

# OAuth2 client



# OAuth2 authorization server

## 配置

- EnableAuthorizationServer

- security-oauth2-service.sql


## CODE 模式
 
- 步骤 TODO

> 1 login.ftl 中处理target-url

> 2 client 工程 未登录跳转

> 3 授权

```
# 方式一 授权地址
http://www.codepasser.com/web-oauth/oauth/authorize?response_type=code&client_id=oauth_client&redirect_uri=http://www.codepasser.com/web-client/login&scope=read
# 方式二 登录后转向授权地址
http://www.codepasser.com/web-oauth/login?target-url=http://www.codepasser.com/web-oauth/oauth/authorize?response_type=code&client_id=oauth_client&redirect_uri=http://www.codepasser.com/web-client/login&scope=read
```

> 4  令牌
```
# 令牌地址 
http://www.codepasser.com/web-oauth/oauth/token?client_id=CLIENT_ID&client_secret=CLIENT_SECRET&grant_type=authorization_code&code=AUTHORIZATION_CODE&redirect_uri=CALLBACK_URL 
```

> 5 TODO