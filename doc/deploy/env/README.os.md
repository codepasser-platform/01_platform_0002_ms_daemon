> 修改hostname

```shell script
hostnamectl set-hostname platform.codepasser.io

vim /etc/hosts
127.0.0.1       platform.codepasser.io

140.82.113.4    github.com
#140.82.113.3   github.com
```

> 关闭防火墙

```shell script
systemctl stop firewalld.service
systemctl disable firewalld.service
systemctl list-unit-files |grep firewalld.service
systemctl restart docker
```
