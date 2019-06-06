

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
password：          资源所有者（即用户）密码类型。
implicit：          隐式授权类型。
client_credentials：客户端凭据（客户端ID以及Key）类型。
refresh_token：     通过以上授权获得的刷新令牌来获取新的令牌。
```


###  令牌模式

> STEP 1 获取CODE 

- ${context}/oauth/authorize

```
# 方式一 登录授权
http://www.codepasser.com/web-oauth/oauth/authorize?response_type=code&client_id=daemon_client&redirect_uri=http://www.codepasser.com/web-client/login&scope=read
# 方式二 登录后转向授权地址
http://www.codepasser.com/web-oauth/login?target-url=http://www.codepasser.com/web-oauth/oauth/authorize?response_type=code&client_id=oauth_client&redirect_uri=http://www.codepasser.com/web-client/login&scope=read
```

> STEP 2 获取令牌 

- ${context}/oauth/token

```
# 获取 TOKEN
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'code=24cs7C&grant_type=authorization_code&redirect_uri=http://www.codepasser.com/web-client/login' "http://daemon_client:1234@www.codepasser.com/web-oauth/oauth/token"

> response :

{"access_token":"0db0833d-0578-4a5c-b723-e3aa3374bcfb","token_type":"bearer","refresh_token":"563e385b-2049-4646-bbcf-c06fcaaf48a8","expires_in":1799,"scope":"read"}

```

> STEP 3 获取用户信息 

- ${context}/oauth/me

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'access_token=0db0833d-0578-4a5c-b723-e3aa3374bcfb' "http://daemon_client:1234@www.codepasser.com/web-oauth/oauth/me"
```

> STEP 4 检查token是否有效

- /oauth/check_token

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'token=5a6118cb-2f46-4175-ba34-787b8c02b15b' "http://daemon_client:1234@www.codepasser.com/web-oauth/oauth/check_token"

> response

{"exp":1559731956,"user_name":"admin","authorities":["ROLE_USER","ROLE_ADMIN","ROLE_MGR"],"client_id":"daemon_client","scope":["read"]}

```


> STEP 5 刷新token TODO

- /oauth/token

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'grant_type=refresh_token&refresh_token=563e385b-2049-4646-bbcf-c06fcaaf48a8' "http://daemon_client:1234@www.codepasser.com/web-oauth/oauth/token"

> response
{"access_token":"5a6118cb-2f46-4175-ba34-787b8c02b15b","token_type":"bearer","refresh_token":"563e385b-2049-4646-bbcf-c06fcaaf48a8","expires_in":1799,"scope":"read"}

```
# 密码模式

```
http://www.codepasser.com/web-oauth/oauth/token?client_id=daemon_client&client_secret=MDAwMA==&grant_type=password&username=admin&password=123qwe
```
