

# OAuth2 client



# OAuth2 authorization server

## 配置

- EnableAuthorizationServer

- security-oauth2-service.sql


## CODE 模式
 
- 步骤 TODO

> 1 login.ftl 中处理target-url

> 2 client 工程 未登录跳转

```
authorization_code：授权码类型。
implicit：隐式授权类型。
password：资源所有者（即用户）密码类型。
client_credentials：客户端凭据（客户端ID以及Key）类型。
refresh_token：通过以上授权获得的刷新令牌来获取新的令牌。
```
> 3 授权

```
# 方式一 授权地址
http://www.codepasser.com/web-oauth/oauth/authorize?response_type=code&client_id=daemon_client&redirect_uri=http://www.codepasser.com/web-client/login&scope=read
# 方式二 登录后转向授权地址
http://www.codepasser.com/web-oauth/login?target-url=http://www.codepasser.com/web-oauth/oauth/authorize?response_type=code&client_id=oauth_client&redirect_uri=http://www.codepasser.com/web-client/login&scope=read
```

> 4  令牌模式
```
# 获取 TOKEN
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'code=HR2uVJ&grant_type=authorization_code&redirect_uri=http://www.codepasser.com/web-client/login' "http://daemon_client:1234@www.codepasser.com/web-oauth/oauth/token"

# 获取用户信息

```

> 5 密码模式
```
# 密码模式
http://www.codepasser.com/web-oauth/oauth/token?client_id=daemon_client&client_secret=MDAwMA==&grant_type=password&username=admin&password=123qwe
```
