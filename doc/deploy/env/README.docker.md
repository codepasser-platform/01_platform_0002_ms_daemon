

## 环境篇 ##

    [https://www.docker.com](https://www.docker.com)
    
### 安装步骤 ###

### Install Docker Engine on CentOS ###

> Docker requires a 64-bit OS and version 3.10 or higher of the Linux kernel.

    [root@localhost ~]# uname -r
    3.10.0-327.el7.x86_64

### Install Docker CE on CentOS ###

> Prerequisites

> Docker CE is supported on CentOS 7.3 64-bit.

> 1. Set up the repository

> Set up the Docker CE repository on CentOS:

    sudo yum install -y yum-utils

    sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

    # aliyun 
    sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo

    sudo yum makecache fast

> 2. Get Docker CE

> Install the latest version of Docker CE on CentOS:

    sudo yum -y install docker-ce

> Start Docker:

    sudo systemctl start docker

> 3. Test your Docker CE installation

> Test your installation:

    sudo docker run hello-world

### 创建 Docker Group ###

> - Log into your machine as a user with sudo or root privileges.

> - Create the docker group.
    
    $ sudo groupadd docker

> - Add your user.

    $ sudo adduser -g docker docker --home-dir=/home/docker
    $ sudo passwd docker

> - Add your user to docker group.

    $ sudo usermod -aG docker ellenchia

> - Log out and log back in

    This ensures your user is running with the correct permissions.

> - Verify that your user is in the docker group by running docker without sudo.

    $ docker run hello-world

#### 设置随系统启动 ####

Configure the Docker daemon to start automatically when the host starts:

    $ sudo systemctl enable docker

### 卸载 ###

You can uninstall the Docker software with yum.

> - List the installed Docker packages.

    $ yum list installed | grep docker
    docker-engine.x86_64     1.7.1-0.1.el7@/docker-engine-1.7.1-0.1.el7.x86_64

> - Remove the package.

    $ sudo yum -y remove docker-engine.x86_64

This command does not remove images, containers, volumes, or user-created configuration files on your host.

> - To delete all images, containers, and volumes, run the following command:

    $ rm -rf /var/lib/docker

> - Locate and delete any user-created configuration files.


## 基本操作命 ##

> 基础命令

    yum -y install docker-engine        #安装docker

    docker run 镜像 apt-get install -y wget    #在容器中安装新的程序（wget）

    docker images                #查看docker库中是否已经有镜像

    docker search Ubuntu    #查看docker镜像库里有没有Ubuntu镜像

    docker pull Ubuntu：14.04    #下载标准的Ubuntu 14.04 镜像

    docker tag 镜像ID 别名    #给下载的镜像取个别名

    docker images    #可以查看是否下载成功

    docker ps -a        #查看docker环境里有多少容器

    docker start 3a09b2588478    #容器 ID 显示在列表左侧。我们可以重新进入容器，不过需要先启动容器。这里使用容器 3a09b2588478 作为示例，你操作时需要使用自己的容器 ID，启用容器

docker 容器操作：

    docker run -d --name 容器名 镜像        #创建容器

    docker stop 容器                        #先停止

    docker rm 容器                        #再移除

    docker exec -i -t 容器名 bash        #进入自己的容器

    docker exec -it 容器名 bash            #进入自己的容器

    docker inspect 容器                    #查看容器的ip

    docker port 容器名                    #查端口

    redis-cli -h 另一个容器ip                #在容器里登陆另一个容器

    docker export 容器名或ID  >  /home/work/ubuntu.tar    #导出docker容器sudo docker save -o ubuntu_14.04.tar ubuntu:14.04

    cat ubuntu.tar | docker import  - 容器名/ubuntu:v1.0        #导入docker容器sudo docker load --input ubuntu_14.04.tar

    docker rm $(docker ps -a -q)            #删除所有停止的容器

    docker run  -d  -p本地端口：容器端口 -v /本地绝对路径：/容器内绝对路径 --name容器名 镜像名     #运行容器

    docker ps -a -q | xargs docker rm                                   #可以删除所有未运行的容器，docker ps -a -q列出当前运行的容器， -a 会列出所有，包括已停止的， -q 只列出容器 ID。

docker镜像操作：

    docker history 镜像                                                        #查看镜像的历史版本

    docker push 镜像                                                           #使用以下命令将镜像推送到registry

    docker commit 做好容器  新创建的镜像名                            #将容器制作成镜像

    docker run --name 容器名 -d -p 17474:22 -v /data/test本地目录：/home/test容器目录 -h test 镜像    #新建容器全

    useradd test -d /home/test                                           #新建用户登陆到指定目录

    apt-get update && apt-get install  -y openssh-server       #容器里下载安装

    docker rmi 镜像名或镜像ID号                                          #删除镜像，先删除这个镜像启动的容器

    docker save 镜像名 >/home/work/ubuntu_ssh.tar            #将docker镜像导出

    docker  load  < /home/work/ubuntu_ssh.tar                   #将本地docker镜像导入docker环境下  

## 配置篇 ##

### Docker 镜像仓库 ###

Docker HUB : https://hub.docker.com

DaoCloud : https://dashboard.daocloud.io

> - pull 镜像

    # daocloud 镜像仓库服务
    https://dashboard.daocloud.io/packages

    # Eg.
    docker pull daocloud.io/library/nginx

### 删除镜像 ###

> - 进入root权限

    sudo su

> - 停止所有的container，这样才能够删除其中的images：

    docker stop $(docker ps -a -q)

    # 如果想要删除所有container的话再加一个指令：
    docker rm $(docker ps -a -q)

> - 查看当前有些什么images

    docker images

> - 删除images，通过image的id来指定删除谁

    docker rmi <image id>

    # 想要删除untagged images，也就是那些id为<None>的image的话可以用
    docker rmi $(docker images | grep "^<none>" | awk "{print $3}")

    # 要删除全部image的话
    docker rmi $(docker images -q)

    # Ubuntu中使用sysv-rc-conf管理service
    sudo apt-get install sysv-rc-conf
    dpkg -r daomonit # For Ubuntu/Debian
    rpm -e daomonit  # For CentOS/Fedora


### 常用命令 ###

> - 查看运行中的实例

    docker ps
    docker ps -a

> - 查看实例日志

    docker logs <container-name|container-id>

> - 停止服务实例

    docker stop <container-name|container-id>
    
> - 删除服务实例

    docker rm <container-name|container-id>
