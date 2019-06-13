# 相关文档

- https://github.com/MaksymBilenko/docker-oracle-12c/tree/r2

- https://www.cnblogs.com/Dev0ps/p/10676930.html

- https://hub.docker.com/u/codepasser/content/sub-ae4e1fed-1ee7-4d7d-ad31-7891d65f91aa

> 配置aliyun docker镜像加速器：

```
sudo mkdir -p /etc/docker
sudo tee /etc/docker/daemon.json <<-'EOF'
{
  "registry-mirrors": ["https://xmmpdt8k.mirror.aliyuncs.com"]
}
EOF
sudo systemctl daemon-reload
sudo systemctl restart docker
```

> 下载docker oracle 镜像

```
docker pull sath89/oracle-12c
```

> Data挂在目录 777权限

```
chmod 777 $HOME/oracle/volume/data
```

> Connect database with following setting:

```
hostname: localhost
port: 1521
sid: xe
service name: xe
username: system
password: oracle
```

> To connect using sqlplus:

```
sqlplus system/oracle@//localhost:1521/xe
```

> Password for SYS & SYSTEM:

```
oracle
```


> Connect to Oracle Application Express web management console with following settings:

```
http://172.16.20.121:8080/apex
workspace: INTERNAL
user: ADMIN
password: 0Racle$
```

> Change password ADMIN

```
workspace: INTERNAL
user: ADMIN
password: Sa*963.-+
```

# DBA 常用命令

> 给用户解锁

```
alter user scott account unlock;
```

> 注销、断开、切换当前用户连接

```
quit
conn scott/tiger
```

> 用户权限查询

```
# 查看所有用户：
select * from dba_users;
select * from all_users;
select * from user_users;

# 查看用户或角色系统权限(直接赋值给用户或角色的系统权限)：
select * from dba_sys_privs;
select * from user_sys_privs;

# 查看角色(只能查看登陆用户拥有的角色)所包含的权限
select * from role_sys_privs;

# 查看用户对象权限：
select * from dba_tab_privs;
select * from all_tab_privs;
select * from user_tab_privs;

# 查看所有角色：
select * from dba_roles;

# 查看用户或角色所拥有的角色：
select * from dba_role_privs;
select * from user_role_privs;

# 查看哪些用户有sysdba或sysoper系统权限(查询时需要相应权限)
select * from V$PWFILE_USERS;
```

> 用户管理

```
# 创建用户
create user username identified by password;
create user username identified by password default tablespace users quota 10M on users;

# 修改密码,同样登陆后输入password也可以修改密码
alter user username identified by pass;

# 删除用户
drop user username;
drop user username cascade;
```

> 三种标准的角色（role）：CONNECT、RESOURCE和DBA。

```
I、Connect连接、登陆权限

II、Resource可以创建表、序列、过程（procedure）、触发器（trigger）、索引（index）和簇（cluster）。

III、Dba管理员
```

>  用户权限

```
# 给用户授权
grant connect, resource to username;
grant create session, create table, create view to username;

# 撤销权限
revoke connect from username;
```


>  角色管理

```
# 创建角色
create role LOGIN;

# 删除角色
drop role LOGIN;
```

# 为应用配置用户、角色

> 创建用户
```
create user daemon identified by daemon_pw;
grant connect, resource to daemon;
```

> 测试用户

```
docker exec -it oracle-master bash -c "source /home/oracle/.bashrc; sqlplus /nolog"
$ conn daemon
password: daemon_pw
```

```
# 确认数据库时间
select to_char(sysdate,'yyyy-MM-dd HH24:mi:ss') from dual;
# 查看表
select table_name from user_tables;
```

