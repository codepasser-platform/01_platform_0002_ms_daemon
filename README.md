
## 开发环境

### 前端开发环境

- 安装NodeJS

>  v8.11.2 or latter [current 10.15.3]

    # 官网
    https://nodejs.org/en/

- 全局安装VUE

> 2.9.6 or latter

    # 安装命令
    npm install -g typescript
    npm install -g vue
    npm install -g vue-cli

    # 可指定国内镜像安装
    npm install -g typescript --registry=http://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist
    npm install -g vue --registry=http://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist
    npm install -g vue-cli --registry=http://registry.npm.taobao.org --disturl=https://npm.taobao.org/dist

> npm 镜像地址切换

    # 切换npm taobao镜像地址
    npm config set registry http://registry.npm.taobao.org/
    # 切换npm官方地址
    npm config set registry https://registry.npmjs.org/

- 卸载

> 卸载安装

    npm uninstall -g typescript
    npm uninstall -g vue
    npm uninstall -g vue-cli
    npm cache clean

### 后端开发环境
