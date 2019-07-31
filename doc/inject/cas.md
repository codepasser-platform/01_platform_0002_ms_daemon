# CAS authorization server

## CAS 资源 【应用版本 4.2.7】

> https://mvnrepository.com/artifact/org.jasig.cas/cas-server-webapp

## CAS server配置取消http

- 修改 cas.properties

> 目录 cas/WEB-INF

```
tgc.secure=false
warn.cookie.secure=false
```

- 修改 HTTPSandIMAPS-10000001.json*

> 目录 cas/WEB-INF/classes/services

```
"serviceId" : "^(https|imaps|http)://.*",
```