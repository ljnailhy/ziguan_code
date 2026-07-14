# VUE PLATFORM UI 平台前端组件库

## 1. 环境

### 设置作用域的注册表

```bash
# npm set @platform:registry=http://192.168.1.100:8081/repository/npm-hosted
```

> 若以上方法无效，则手动配置 .npmrc（通常位于用户主目录，例如 ~/.npmrc）

```npmrc
@platform:registry=http://192.168.1.100:8081/repository/npm-hosted
```

### 验证配置

> 会输出已配置的私服地址。

```bash
npm config get '@platform:registry'
```

## 2. 打包

### 登录私有 npm registry

> 手动配置 .npmrc（通常位于用户主目录，例如 ~/.npmrc）

```npmrc
//192.168.1.100:8081/repository/:username=develop
//192.168.1.100:8081/repository/:_password=PASSWORD
//192.168.1.100:8081/repository/:email=YOUR_EMAIL
```

### 验证配置

> 会返回已配置的用户名。

```bash
npm whoami --registry=http://192.168.1.100:8081/repository/npm-hosted
```

### 打包

```bash
npm run build
```

### 发布

> 要先增加`package.json`中的版本号`version`

```bash
npm publish
# 有多个私服时，可以加上以下命令指定发布到哪个私服：--registry=http://192.168.1.100:8081/repository/npm-hosted
```

## 3. 使用

### 安装

> 更新版本时，先执行以下命令后，再删除`node_modules/.vite/deps/`目录，并重新`run`

```bash
npm i --save @platform/vue-platform-ui
```

### 全局注册

> /src/main.ts

```typescript
import VuePlatformUi from "@platform/vue-platform-ui";

const app = createApp(App);
app.use(VuePlatformUi);
```

### 使用示例

#### 组件

```vue
<template>
  <vz-user />
</template>
```

#### 函数

```javascript
<script setup>import {getWeek} from '@platform/vue-platform-ui';</script>
```

### 更新版本

```bash
npm i @platform/vue-platform-ui --save
# 删除 node_modules/.vite/deps/ 目录，并重新run
```

## 4. 其他

### 导出组件&函数

> /components/index.ts

### 外部一定会 install 的依赖不需要打包

> vite.config.ts

`rollupOptions.external`和`rollupOptions.output.globals`都要配置
