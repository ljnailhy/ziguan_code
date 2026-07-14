param(
  [string]$BackendUrl = "http://222.240.31.178:10000/api",
  [int]$Port = 5173,
  [switch]$SkipInstall
)

$ErrorActionPreference = "Stop"

$RepoRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$FrontendPackage = Get-ChildItem -LiteralPath $RepoRoot -Recurse -Filter "package.json" -ErrorAction Stop |
  Where-Object { $_.Directory.Name -eq "asset-fe" } |
  Select-Object -First 1

if (!$FrontendPackage) {
  throw "Could not find asset-fe/package.json under: $RepoRoot"
}

$FrontendDir = $FrontendPackage.Directory.FullName
$VendorPackage = Join-Path $FrontendDir "vendor\vue-platform-ui\package.json"

if (!(Test-Path $VendorPackage)) {
  throw "Vendored private package not found: $VendorPackage"
}

function Test-TcpPort {
  param(
    [string]$ComputerName,
    [int]$Port,
    [int]$TimeoutMs = 3000
  )

  $Client = [System.Net.Sockets.TcpClient]::new()
  try {
    $ConnectTask = $Client.ConnectAsync($ComputerName, $Port)
    return $ConnectTask.Wait($TimeoutMs) -and $Client.Connected
  } catch {
    return $false
  } finally {
    $Client.Dispose()
  }
}

$BackendUri = [Uri]$BackendUrl
$BackendPort = if ($BackendUri.IsDefaultPort) {
  if ($BackendUri.Scheme -eq "https") { 443 } else { 80 }
} else {
  $BackendUri.Port
}

if (!(Test-TcpPort -ComputerName $BackendUri.Host -Port $BackendPort)) {
  throw "Backend is not reachable: $BackendUrl"
}

$LocalUrl = "http://127.0.0.1:$Port/asset/#/login"
$ExistingListener = Get-NetTCPConnection -LocalPort $Port -State Listen -ErrorAction SilentlyContinue
if ($ExistingListener) {
  try {
    $ExistingPage = Invoke-WebRequest -Uri $LocalUrl -UseBasicParsing -TimeoutSec 3
    if ($ExistingPage.StatusCode -eq 200) {
      Write-Host "Asset frontend is already running." -ForegroundColor Green
      Write-Host "Open: $LocalUrl" -ForegroundColor Cyan
      return
    }
  } catch {
    throw "Port $Port is already used by another program."
  }
}

$LocalEnvFile = Join-Path $FrontendDir ".env.development.local"
$BundledNodeDir = Join-Path $env:USERPROFILE ".cache\codex-runtimes\codex-primary-runtime\dependencies\node\bin"
$BundledPnpm = Join-Path $env:USERPROFILE ".cache\codex-runtimes\codex-primary-runtime\dependencies\bin\fallback\pnpm.cmd"

if (Test-Path $BundledNodeDir) {
  $env:Path = "$BundledNodeDir;$env:Path"
}

$Pnpm = if (Test-Path $BundledPnpm) { $BundledPnpm } else { "pnpm" }

@"
VITE_OPEN = false
VITE_PORT = $Port
VITE_APP_PUBLIC_PATH = /asset/
VITE_APP_WORKFLOW_DOMAIN = http://222.240.31.178:10000/workflow
VITE_PROXY = [["/api","$BackendUrl"]]
VITE_DISABLE_ESLINT = true
"@ | Set-Content -Path $LocalEnvFile -Encoding UTF8

Set-Location $FrontendDir

if (!$SkipInstall) {
  & $Pnpm install
  if ($LASTEXITCODE -ne 0) { throw "pnpm install failed with exit code $LASTEXITCODE" }

  # These packages select their Vue 2/3 entrypoint during postinstall.
  & $Pnpm rebuild "@vue-office/docx" "@vue-office/excel" "@vue-office/pdf"
  if ($LASTEXITCODE -ne 0) { throw "pnpm rebuild failed with exit code $LASTEXITCODE" }
}

$LocalRedis = Test-TcpPort -ComputerName "127.0.0.1" -Port 6379 -TimeoutMs 500
if ($LocalRedis) {
  Write-Host "Local Redis detected on 127.0.0.1:6379." -ForegroundColor Green
} else {
  Write-Host "Local Redis was not detected; the remote backend will use its configured Redis." -ForegroundColor DarkYellow
}

Write-Host ""
Write-Host "Asset frontend is starting..." -ForegroundColor Green
Write-Host "Open: $LocalUrl" -ForegroundColor Cyan
Write-Host "Backend: $BackendUrl" -ForegroundColor DarkGray
Write-Host "Press Ctrl+C to stop." -ForegroundColor DarkGray
Write-Host ""

& $Pnpm run dev --host 0.0.0.0
if ($LASTEXITCODE -ne 0) { throw "Frontend exited with code $LASTEXITCODE" }
