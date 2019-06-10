# OAuth2 authorization server

## 配置

- EnableAuthorizationServer

- security-oauth2-service.sql
 
- 步骤 TODO

```
> 1 login.ftl 中处理target-url
> 2 client 工程 未登录跳转
```

## 认证模式

> authorization_code：授权码类型。

> password：          资源所有者（即用户）密码类型。

> implicit：          隐式授权类型。

> client_credentials：客户端凭据（客户端ID以及Key）类型。

> refresh_token：     通过以上授权获得的刷新令牌来获取新的令牌。

###  认证模式- ：令牌模式 authorization_code

- 授权码（authorization code）方式，指的是第三方应用先申请一个授权码，然后再用该码获取令牌。
这种方式是最常用的流程，安全性也最高，它适用于那些有后端的 Web 应用。授权码通过前端传送，令牌则是储存在后端，而且所有与资源服务器的通信都在后端完成。这样的前后端分离，可以避免令牌泄漏。


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
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'code=g5Q2kc&grant_type=authorization_code&redirect_uri=http://www.codepasser.com/web-client/login' "http://daemon_client:1234@www.codepasser.com/web-oauth/oauth/token"

> response :

{"access_token":"36af4b60-67d4-445a-9f0a-e1c036acae1a","token_type":"bearer","refresh_token":"15c02260-bb9d-4f74-9d59-f3b3f6278a3d","expires_in":1799,"scope":"read"}

```

> STEP 3 获取用户信息 

- ${context}/api/me

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'access_token=36af4b60-67d4-445a-9f0a-e1c036acae1a' "http://daemon_client:1234@www.codepasser.com/web-oauth/api/me"

> response 

{"id":"1","username":"admin","phone":"18516171342","email":"admin@codepasser.com","type":"GENERATED","user_statuses":["MANAGED"],"locked":false,"authorities":["USER","ADMIN","MGR"],"org_id":"0","org":{"id":"0","name":"codepasser.com","type":"ROOT"}}
```

> STEP 4 检查token是否有效

- /oauth/check_token

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'token=84089940-8247-4869-96d6-4d132acbb61e' "http://daemon_client:1234@www.codepasser.com/web-oauth/oauth/check_token"

> response

{"exp":1559731956,"user_name":"admin","authorities":["ROLE_USER","ROLE_ADMIN","ROLE_MGR"],"client_id":"daemon_client","scope":["read"]}

```

> STEP 5 刷新token TODO

- /oauth/token

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'grant_type=refresh_token&refresh_token=15c02260-bb9d-4f74-9d59-f3b3f6278a3d' "http://daemon_client:1234@www.codepasser.com/web-oauth/oauth/token"

> response

{"access_token":"84089940-8247-4869-96d6-4d132acbb61e","token_type":"bearer","refresh_token":"15c02260-bb9d-4f74-9d59-f3b3f6278a3d","expires_in":1799,"scope":"read"}

```

###  认证模式二 ：密码模式 password

- 有些 Web 应用是纯前端应用，没有后端。这时就不能用上面的方式了，必须将令牌储存在前端。RFC 6749 就规定了第二种方式，允许直接向前端颁发令牌。这种方式没有授权码这个中间步骤，所以称为（授权码）"隐藏式"（implicit）。

> STEP 1 密码认证 

- /oauth/token

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'grant_type=password&username=38494070&password=123456' "http://daemon_client:1234@www.codepasser.com/web-oauth/oauth/token"

> response

{"access_token":"1d22b7bd-1ccf-4321-ad21-4ee22131c847","token_type":"bearer","refresh_token":"d76fcb4c-8635-4644-99ae-989a34daadd2","expires_in":1799,"scope":"read write"}
```

> STEP 2 获取用户信息 

- ${context}/api/me

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'access_token=1d22b7bd-1ccf-4321-ad21-4ee22131c847' "http://daemon_client:1234@www.codepasser.com/web-oauth/api/me"

> response 

{"id":"45059919","username":"38494070","type":"EXTERNAL","user_statuses":["MANAGED"],"locked":false,"authorities":["USER"],"org_id":"0","org":{"id":"0","name":"codepasser.com","type":"ROOT"}}

```

> STEP 3 检查token是否有效

- /oauth/check_token

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'token=1d22b7bd-1ccf-4321-ad21-4ee22131c847' "http://daemon_client:1234@www.codepasser.com/web-oauth/oauth/check_token"

> response

{"aud":["daemon-service"],"exp":1559799720,"user_name":"38494070","authorities":["ROLE_USER"],"client_id":"daemon_client","scope":["read","write"]}

```

> STEP 4 刷新token TODO

- /oauth/token

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'grant_type=refresh_token&refresh_token=27ff1868-f89d-4506-abf3-35b0844218a4' "http://daemon_client:1234@www.codepasser.com/web-oauth/oauth/token"

> response

{"access_token":"1d22b7bd-1ccf-4321-ad21-4ee22131c847","token_type":"bearer","refresh_token":"27ff1868-f89d-4506-abf3-35b0844218a4","expires_in":1799,"scope":"read write"}

```

###  认证模式三 ：凭证式 client_credentials

- 凭证式（client credentials），适用于没有前端的命令行应用，即在命令行下请求令牌。

> STEP 1

- /oauth/token

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'grant_type=client_credentials' "http://daemon_client:1234@www.codepasser.com/web-oauth/oauth/token"

> response

```

> STEP 2

- ${context}/api/me

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'access_token=1d22b7bd-1ccf-4321-ad21-4ee22131c847' "http://daemon_client:1234@www.codepasser.com/web-oauth/api/me"

> response

```

###  认证模式四 ：凭证式 implicit

- 

> STEP 1

```
```

> STEP 2

```
```

## 权限测试

> STEP 1 权限测试 

```
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'access_token=1d22b7bd-1ccf-4321-ad21-4ee22131c847' "http://daemon_client:1234@www.codepasser.com/web-oauth/api/me"
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'access_token=1d22b7bd-1ccf-4321-ad21-4ee22131c847' "http://daemon_client:1234@www.codepasser.com/web-oauth/api/user1"
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'access_token=1d22b7bd-1ccf-4321-ad21-4ee22131c847' "http://daemon_client:1234@www.codepasser.com/web-oauth/api/user2"
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'access_token=1d22b7bd-1ccf-4321-ad21-4ee22131c847' "http://daemon_client:1234@www.codepasser.com/web-oauth/api/admin"
curl -X POST -H "Content-Type: application/x-www-form-urlencoded" -d 'access_token=1d22b7bd-1ccf-4321-ad21-4ee22131c847' "http://daemon_client:1234@www.codepasser.com/web-oauth/api/user"
```

# OAuth2 client

- TODO