### 介绍

Vue3.4、TypeScript、Vite5、Pinia、Element-Plus。

### 安装使用步骤 📔

- **Clone：**

```text
# GitHub
git clone http://106.55.177.90:8099/jk/platform/asset/asset-fe.git
```

- **Install：**

```text
pnpm install
```

- **Run：**

```text
pnpm dev
pnpm serve
```

- **Build：**

```text
# 开发环境
pnpm build:dev

# 测试环境
pnpm build:test

# 生产环境
pnpm build:pro
```

- **Lint：**

```text
# eslint 检测代码
pnpm lint:eslint

# prettier 格式化代码
pnpm lint:prettier

# stylelint 格式化样式
pnpm lint:stylelint
```

- **commit：**

```text
# 提交代码（提交前会自动执行 lint:lint-staged 命令）
pnpm commit
```

### 使用VUE PLATFORM UI 平台前端组件库 🎉

> 设置作用域的注册表

```bash
# npm set @platform:registry=http://106.55.177.90:8081/repository/npm-hosted
```

> 若以上方法无效，则手动配置 .npmrc（通常位于用户主目录，例如 ~/.npmrc）

```npmrc
@platform:registry=http://106.55.177.90:8081/repository/npm-hosted
```

> 验证配置，会输出已配置的私服地址。

```bash
npm config get '@platform:registry'
```

> 需要更新版本时

```bash
npm i @platform/vue-platform-ui --save
# 删除 node_modules/.vite/deps/ 目录，并重新run
```

### 新增修改页注册 📌

> 注册路径（key的规则是“文件夹\_createUpdate”）：`src\components\common\Components.ts`

### 文件资源目录 📚

```text
├─ .husky                  # husky 配置文件
├─ .vscode                 # VSCode 推荐配置
├─ build                   # Vite 配置项
├─ public                  # 静态资源文件（该文件夹不会被打包）
├─ src
│  ├─ api                  # API 接口管理
│  ├─ assets               # 静态资源文件
│  ├─ components           # 全局组件
│  ├─ config               # 全局配置项
│  ├─ directives           # 全局指令文件
│  ├─ enums                # 项目常用枚举
│  ├─ hooks                # 常用 Hooks 封装
│  ├─ languages            # 语言国际化 i18n
│  ├─ layouts              # 框架布局模块
│  ├─ routers              # 路由管理
│  ├─ stores               # pinia store
│  ├─ styles               # 全局样式文件
│  ├─ typings              # 全局 ts 声明
│  ├─ utils                # 常用工具库
│  ├─ views                # 项目所有页面
│  ├─ App.vue              # 项目主组件
│  ├─ main.ts              # 项目入口文件
│  └─ vite-env.d.ts        # 指定 ts 识别 vue
├─ .editorconfig           # 统一不同编辑器的编码风格
├─ .env                    # vite 常用配置
├─ .env.development        # 开发环境配置
├─ .env.production         # 生产环境配置
├─ .env.test               # 测试环境配置
├─ .eslintignore           # 忽略 Eslint 校验
├─ .eslintrc.cjs           # Eslint 校验配置文件
├─ .gitignore              # 忽略 git 提交
├─ .prettierignore         # 忽略 Prettier 格式化
├─ .prettierrc.cjs         # Prettier 格式化配置
├─ .stylelintignore        # 忽略 stylelint 格式化
├─ .stylelintrc.cjs        # stylelint 样式格式化配置
├─ commitlint.config.cjs   # git 提交规范配置
├─ index.html              # 入口 html
├─ lint-staged.config.cjs  # lint-staged 配置文件
├─ package-lock.json       # 依赖包包版本锁
├─ package.json            # 依赖包管理
├─ postcss.config.cjs      # postcss 配置
├─ README.md               # README 介绍
├─ tsconfig.json           # typescript 全局配置
└─ vite.config.ts          # vite 全局配置文件
```
