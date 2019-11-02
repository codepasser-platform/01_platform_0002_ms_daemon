-- # Docker 宿主主机时间同步
# ntpdate -u ntp1.aliyun.com
# ntpdate -u ntp2.aliyun.com
# ntpdate -u ntp3.aliyun.com
# ntpdate -u ntp4.aliyun.com
# ntpdate -u ntp5.aliyun.com
# ntpdate -u ntp6.aliyun.com
# ntpdate -u ntp7.aliyun.com

-- # 查看已提供什么存储引擎:
SHOW ENGINES;

-- # 当前默认的存储引擎:
SHOW VARIABLES LIKE '%storage_engine%';

-- # 查看字符集
SHOW VARIABLES LIKE 'character_set_%';

-- # 列出MYSQL支持的所有字符集
SHOW CHARACTER SET;

-- # 查看mysql 中用户信息
SELECT DISTINCT CONCAT('User: ''', user, '''@''', host, ''';') AS query FROM mysql.user;

-- # 查看用户授权
SHOW GRANTS FOR 'daemon'@'%';

-- # 查看最大连接数
SHOW VARIABLES LIKE '%max_connections%';

-- # 重新设置
SET GLOBAL max_connections = 1000;
SHOW status like 'Threads%';

-- # TimeZone CST
SELECT curtime();
SELECT now();
SHOW VARIABLES LIKE '%time_zone%';

-- # > BASE
-- # 创建数据库
CREATE DATABASE daemon;

-- # 创建用户
CREATE USER 'daemon'@'%' IDENTIFIED BY 'cde3VFR$' PASSWORD EXPIRE NEVER;
ALTER USER 'daemon'@'%' IDENTIFIED WITH mysql_native_password BY 'cde3VFR$';

-- # 已创建用户更改认证加密方式
ALTER USER 'daemon'@'%' IDENTIFIED BY 'cde3VFR$' PASSWORD EXPIRE NEVER;
ALTER USER 'daemon'@'%' IDENTIFIED WITH mysql_native_password BY 'cde3VFR$';
FLUSH PRIVILEGES;

-- # 授权
GRANT ALL PRIVILEGES ON daemon.* TO daemon@'%';
