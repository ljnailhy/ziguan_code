import CryptoJS, { AES, enc, mode, pad, PBKDF2 } from "crypto-js";
import * as forge from "node-forge";

// 后端RSA公钥（实际应从接口动态获取）
const PUBLIC_KEY_PEM =
  import.meta.env.VITE_CRYPTO_PUBLIC_KEY ||
  `-----BEGIN PUBLIC KEY-----
MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxcb7zuFV8aHSDUIfARHt
QPT1Qn+o8fxM0S3zNA39wBVjnrc3FMWo2LKc6autHM0WEhKAbftO+pihGjINcO1Y
WJVTvRRbkVGy84ZOxQPs8gfyAR7TqdpzeSjuPxlZTJHy4hRxCxq+ZN6bf4V8oS5g
KbogmemanZPlq4NyIQkbNCWQdpzxMEJjPMGhuWjN+LtIrSrsGKcUL1yUEczdA6lY
3YGAgaD3whd9RW5fpVzOzHKJfekGeaqp5sZmyJVn0iFjw/nB6WSpiX5U1Qkla8FT
HjCyYIJ30gBKMfFZCOtLPilninyCuBaDZ3guNb/3ekQDlq2vYyPZYJpKJCzqrrHs
ywIDAQAB
-----END PUBLIC KEY-----
`;

// 盐值常量（应通过环境变量注入，不要硬编码）
const STATIC_SALT =
  import.meta.env.VITE_CRYPTO_SALT || "8710FB89B0ECD98F5A98E45D3BFB549F";

// function pemToArrayBuffer(pem: string): ArrayBuffer {
//   const b64 = pem.replace(/-----(BEGIN|END) PUBLIC KEY-----/g, "").replace(/\s+/g, "");
//   const bin = atob(b64);
//   const bytes = new Uint8Array(bin.length);
//   for (let i = 0; i < bin.length; i++) bytes[i] = bin.charCodeAt(i);
//   return bytes.buffer;
// }
// async function importPublicKey(): Promise<CryptoKey> {
//   const keyData = pemToArrayBuffer(PUBLIC_KEY_PEM);
//   return crypto.subtle.importKey("spki", keyData, { name: "RSA-OAEP", hash: "SHA-256" }, false, ["encrypt"]);
// }
// export async function rsaEncryptToBase64(plain: string): Promise<string> {
//   try {
//     const maxLength = 117; // RSA1024 的最大加密长度
//     if (plain.length > maxLength) {
//       console.error("RSA加密数据过长，请确保数据长度不超过117字节");
//       return plain; // 返回原始数据以避免数据丢失
//     }

//     const key = await importPublicKey();
//     const encoded = new TextEncoder().encode(plain);
//     const cipher = await crypto.subtle.encrypt({ name: "RSA-OAEP" }, key, encoded);
//     // ArrayBuffer -> Base64
//     const bytes = new Uint8Array(cipher);
//     let bin = "";
//     for (let i = 0; i < bytes.length; i++) bin += String.fromCharCode(bytes[i]);
//     const encrypted = btoa(bin);

//     if (!encrypted) {
//       throw new Error("RSA加密失败");
//     }

//     return encrypted;
//   } catch (error) {
//     console.error("RSA加密错误:", error);
//     return plain;
//   }
// }

// RSA加密（用于加密AES密钥）
export function rsaEncryptToBase64(plain: string): string {
  try {
    // 使用 node-forge 在浏览器/HTTP 下做 RSA-OAEP(SHA-256) 加密
    const publicKeyPem = PUBLIC_KEY_PEM;
    const pub = forge.pki.publicKeyFromPem(publicKeyPem);
    // 明文为字符串，直接加密（node-forge 返回二进制字符串），使用 SHA-256 做 OAEP 和 MGF1
    const encryptedBytes = pub.encrypt(plain, "RSA-OAEP", {
      md: forge.md.sha256.create(),
      mgf1: {
        md: forge.md.sha256.create(),
      },
    });
    // 转 base64
    return forge.util.encode64(encryptedBytes);
  } catch (error) {
    console.error("RSA加密错误:", error);
    return plain;
  }
}

// AES加密（用于业务数据）
export const aesEncrypt = (data: string, key: string, iv: string): string => {
  try {
    // 模拟后端的PBKDF2密钥派生
    // const salt = enc.Utf8.parse(STATIC_SALT); // 需要与后端保持一致
    // const iterations = 65536; // 需要与后端保持一致
    // const keySize = 256 / 32; // 256位 → 8 (32位 x 8 = 256位)

    // const derivedKey = PBKDF2(key, salt, {
    //   keySize: keySize,
    //   iterations: iterations,
    //   hasher: CryptoJS.algo.SHA256
    // });
    const derivedKey =
      localStorage.getItem("derivedKey") &&
      JSON.parse(localStorage.getItem("derivedKey")!);

    const ivBytes = enc.Utf8.parse(iv);
    const result = AES.encrypt(data, derivedKey, {
      iv: ivBytes,
      mode: mode.CBC,
      padding: pad.Pkcs7,
    });

    return result.toString();
  } catch (error) {
    console.warn("AES加密失败:", error);
    return data; // 返回原始数据以避免数据丢失
  }
};

// AES解密（用于业务数据）
export const aesDecrypt = (
  encryptedData: string,
  key: string,
  iv: string
): string => {
  try {
    // 模拟后端的PBKDF2密钥派生
    // const salt = enc.Utf8.parse(STATIC_SALT); // 需要与后端保持一致
    // const iterations = 65536; // 需要与后端保持一致
    // const keySize = 256 / 32; // 256位 → 8 (32位 x 8 = 256位)

    // const derivedKey = PBKDF2(key, salt, {
    //   keySize: keySize,
    //   iterations: iterations,
    //   hasher: CryptoJS.algo.SHA256,
    // });
    const derivedKey =
      localStorage.getItem("derivedKey") &&
      JSON.parse(localStorage.getItem("derivedKey")!);

    const ivBytes = enc.Utf8.parse(iv);
    const decrypted = AES.decrypt(encryptedData, derivedKey, {
      iv: ivBytes,
      mode: mode.CBC,
      padding: pad.Pkcs7,
    });

    const result = decrypted.toString(enc.Utf8);
    return result ? result : encryptedData;
  } catch (error) {
    console.warn("AES解密失败:", error);
    return encryptedData; // 返回原始数据以避免数据丢失
  }
};

// 生成16字节(128位)随机IV
export const generateIV = (): string => {
  const randomBytes = new Uint8Array(8);
  window.crypto.getRandomValues(randomBytes);
  return Array.from(randomBytes)
    .map((byte) => byte.toString(16).padStart(2, "0"))
    .join("");
};

// 生成32字节(256位)随机AES密钥
export const generateAesKey = (): string => {
  const randomBytes = new Uint8Array(16);
  window.crypto.getRandomValues(randomBytes);
  return Array.from(randomBytes)
    .map((byte) => byte.toString(16).padStart(2, "0"))
    .join("");
};
