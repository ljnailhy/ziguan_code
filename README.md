# 资产管理系统本地运行

这是一个精简后的本地代码版本：保留前端源码、后端源码、启动脚本和必须的私有包。

## 一键启动

在项目根目录执行：

```powershell
.\start-local.cmd
```

启动后访问：

```text
http://127.0.0.1:5173/asset/#/login
```

当前一键启动只启动前端，接口通过代理连接测试后端：

```text
http://222.240.31.178:10000/api
```

## 账号

```text
用户名：admin
密码：使用项目交接时提供的测试账号密码
```

## 私有包

项目依赖的 `@platform/vue-platform-ui` 已放入：

```text
源代码/前端/asset-fe/vendor/vue-platform-ui
```

`package.json` 中使用本地依赖：

```json
"@platform/vue-platform-ui": "file:vendor/vue-platform-ui"
```

因此不需要访问私有 npm 仓库。

## 说明

- 本地暂不启动 Nacos。
- 本地暂不启动后端 JAR。
- 后端源码会上传；后端 `target`、`运行包` 这类可重新生成/下载的产物不上传。
- `部署程序`、服务器连接密码文档、运行日志、`node_modules` 不放入仓库。
- 如果页面主题颜色异常，可以在浏览器控制台执行：

```js
localStorage.removeItem("vz-global");
location.reload();
```
