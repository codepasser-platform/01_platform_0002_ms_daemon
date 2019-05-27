# 环境部署文档

## CentOS系统配置

> 切换 yum aliyun 源

    # 备份
    cp /etc/yum.repos.d/CentOS-Base.repo /etc/yum.repos.d/CentOS-Base.repo.bak
    # 切换
    curl -o /etc/yum.repos.d/CentOS-Base.repo http://mirrors.aliyun.com/repo/Centos-7.repo
    # 清理缓存
    yum clean all
    # 缓存索引
    yum makecache

> 安装 CentOS 必要开发包 

    sudo yum -y install make gcc gcc-c++ ncurses-devel perl pcre-devel zlib zlib-devel git
    sudo yum -y install openssl openssl-devel 
    sudo yum -y install libgd2-devel libpcre-devel libcurl-devel gd-devel libaio
    sudo yum -y install net-tools telnet tree lsof wget unzip zip portmap vim lvm2 rsync ntp httpd nfs-utils psmisc
    sudo yum install -y perl-Module-Install.noarch

> 创建应用账户

    groupadd daemon
    adduser -g daemon daemon --home-dir=/home/daemon
    passwd daemon

## 安装 nginx(源码安装方式)

> 源码安装 nginx-1.14.2 stable
  
    # without ssl
    ./configure --user=root --group=root --prefix=/usr/local/lib/nginx/nginx-proxy --with-pcre --with-http_stub_status_module --with-http_gzip_static_module --without-http_fastcgi_module
    
    # with ssl
    ./configure --user=root --group=root --prefix=/usr/local/lib/nginx/nginx-proxy --with-pcre --with-http_stub_status_module --with-http_gzip_static_module --with-http_ssl_module

> 配置

    参考 guide/nginx

## 安装 GIT

>  v1.8.3.1 or latter

    # 官网
    https://git-scm.com/
    # 当前最新版本号为1.8.3.1
    yum install git

> 环境配置

    # GIT安装完成后自动会导入PATH， 注意 git的顺序在JAVA、MAVEN、NODE之后，这样 WINDOWNS即可适用 git bash
    # 设置ssh协议秘钥生成 .ssh目录后撤销，将 git_ssh_key.zip解压放入目录 ~/.ssh
    ssh-keygen
    
    # 设置个人信息
    git config --global user.name  "Joker.Cheng"
    git config --global user.email "codepasser@aliyun.com"
    
    # mac/linux 开发者
    git config --global core.autocrlf input

    # 设置命令别名
    git config --global alias.st status
    git config --global alias.co checkout
    git config --global alias.ci commit
    git config --global alias.br branch

## 安装 JAVA

>  v1.8.x or latter

    # 官网
    https://www.java.com/en/
    # 当前jdk 1.8 最新版本号为 1.8.181

> 安装目录

    /usr/local/lib/java/jdk1.8.0_211

## 安装 MAVEN

>  v3.3.3 or latter

    # 官网
    http://maven.apache.org/
    # 当前最新版本号为3.6.1

> 安装目录

    /usr/local/lib/java/apache-maven-3.6.1
    
> maven 仓库配置

    1 settings.xml保存本地，放入到 ${USER_HOME}/.m2/下

    # Maven 安装后没有.m2 目录的情况，打开cmd运行 mvn clean即可生成 ${USER_HOME}/.m2/

    # 配置中默认仓库路径
    ${USER_HOME}/.m2/repostory

    # Maven源Aliyun
    <mirror>
      <id>alimaven</id>
      <name>aliyun maven</name>
      <url>http://maven.aliyun.com/nexus/content/groups/public/</url>
      <mirrorOf>central</mirrorOf>
    </mirror>

## 安装NodeJS

> v8.11.2 or latter

    # 官网
    https://nodejs.org/en/
    
> 安装目录

    /usr/local/lib/node/node-v10.15.3-linux-x64

> 安装vue依赖 2.9.6 or latter

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

## 环境变量配置

```
export JAVA_HOME=/usr/local/lib/java/jdk1.8.0_211
export JAVA_BIN=$JAVA_HOME/bin
export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar

export MAVEN_HOME=/usr/local/lib/java/apache-maven-3.6.1
export MAVEN_BIN=$MAVEN_HOME/bin

export NODE_HOME=/usr/local/lib/node/node-v10.15.3-linux-x64
export NODE_BIN=$NODE_HOME/bin

export JAVA_HOME JAVA_BIN CLASSPATH MAVEN_HOME MAVEN_BIN NODE_HOME NODE_BIN
export PATH=$PATH:$JAVA_BIN:$MAVEN_BIN:$NODE_BIN:
# 集群环境服务序列码
export MACHINE_ID=00
```

## CentOS 软件管理配置

> NGINX 版本配置

    # 查看
    update-alternatives --list
    # 切换nginx版本
    update-alternatives --install /usr/bin/nginx nginx /usr/local/lib/nginx/nginx-proxy/sbin/nginx 60
    update-alternatives --config nginx

> JAVA 版本配置

    # 查看
    update-alternatives --list
    # 切换java版本
    update-alternatives --install /usr/bin/java java /usr/local/lib/java/jdk1.8.0_211/bin/java 60
    update-alternatives --config java

> MAVEN 版本配置

    # 查看
    update-alternatives --list
    # 切换java版本
    update-alternatives --install /usr/bin/mvn maven /usr/local/lib/java/apache-maven-3.6.1/bin/mvn 60
    update-alternatives --config maven

> Node 版本管理

    # 查看
    update-alternatives --list
    
    # 切换node版本
    update-alternatives --install /usr/bin/node node /usr/local/lib/node/node-v10.15.3-linux-x64/bin/node 60
    update-alternatives --config node
    
    # 切换npm版本
    update-alternatives --install /usr/bin/npm npm /usr/local/lib/node/node-v10.15.3-linux-x64/bin/npm 60
    update-alternatives --config npm
    
    # 切换npx版本
    update-alternatives --install /usr/bin/npx npx /usr/local/lib/node/node-v10.15.3-linux-x64/bin/npx 60
    update-alternatives --config npx
    
    # 切换vue版本
    update-alternatives --install /usr/bin/vue vue-cli /usr/local/lib/node/node-v10.15.3-linux-x64/bin/vue 60
    update-alternatives --config vue-cli
    
    # 切换vue-init版本
    update-alternatives --install /usr/bin/vue-init vue-init /usr/local/lib/node/node-v10.15.3-linux-x64/bin/vue-init 60
    update-alternatives --config vue-init
    
    # 切换vue-list版本
    update-alternatives --install /usr/bin/vue-list vue-list /usr/local/lib/node/node-v10.15.3-linux-x64/bin/vue-list 60
    update-alternatives --config vue-list

## 安装 docker

> Docker requires a 64-bit OS and version 3.10 or higher of the Linux kernel.
> Docker CE is supported on CentOS 7.3 64-bit.

> 1. Set up the Docker CE repository on CentOS

    sudo yum install -y yum-utils
    sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

    # 可指定aliyun镜像
    sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo
    sudo yum makecache fast

> 2. Install the latest version of Docker CE on CentOS

    sudo yum -y install docker-ce

> 3. Start Docker:

    sudo systemctl start docker

> 4. Test your Docker CE installation

    sudo docker run hello-world

> 5. Add your user.

    sudo adduser -g docker docker --home-dir=/home/docker
    sudo passwd docker
    
    usermod -G docker daemon

> 6. Configure the Docker daemon to start automatically when the host starts:

    sudo systemctl enable docker
    
> 7. 配置镜像加速器

```
# 您可以通过修改daemon配置文件/etc/docker/daemon.json来使用加速器
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://xmmpdt8k.mirror.aliyuncs.com"]
}
EOF
# 重载服务
sudo systemctl daemon-reload
sudo systemctl restart docker
```

> 8. Docker镜像服务安装(参考 README.docker.md)

    docker pull mongo:4.0.5
    docker pull mysql:8.0.13
    docker pull redis:4.0.12
    docker pull tobias74/elasticsearch-head:6
    docker pull docker.elastic.co/elasticsearch/elasticsearch:6.4.3
    

## 安装 GlusterFS - 集群环境

>  GlusterFS文件服务安装(参考README.glusterfs.md)

## 导入邮件SSL证书

> 1. 导入邮件SSL证书 valueonline cer

    # 创建目录，放置证书文件
    mdkir $JAVA_HOME/bin/cacerts
    # 导入
    keytool -import -alias valueonline -file $JAVA_HOME/bin/cacerts/valueonline.cer -keystore $JAVA_HOME/jre/lib/security/cacerts  -storepass changeit
    
> 2. 查看证书列表

    keytool -list -keystore $JAVA_HOME/jre/lib/security/cacerts  -storepass changeit |grep valueonline

> 3. 删除导入

    keytool -delete -alias valueonline -keystore $JAVA_HOME/jre/lib/security/cacerts  -storepass changeit


## 应用部署

> git repository (branch: release)

    git clone ssh://git@123.57.54.13:10022/daemon/daemon-web.git

> 前端

    # 更新npm 包
    npm install
    
    # 启动
    sh run.sh

> 后台

    # 更新maven 包
    mvn clean install
    mvn clean install -Dmaven.test.skip=true
    
    # 启动
    sh run.sh
