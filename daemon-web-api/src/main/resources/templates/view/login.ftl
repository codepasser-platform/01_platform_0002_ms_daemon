<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">
  <link rel="shortcut icon" type="image/x-icon" href="${rc.contextPath}/favicon.ico">

  <title>Codepasser IO Platform - Login </title>

  <!-- Bootstrap core CSS -->
  <link href="${rc.contextPath}/static/library/bootstrap/4.0.0/css/bootstrap.min.css" rel="stylesheet">

  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
  <![endif]-->
  <style>
  </style>
</head>

<body class="bg-light">

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="${rc.contextPath}/static/library/jquery/1.12.4/jquery.min.js"></script>
<script src="${rc.contextPath}/static/library/bootstrap/4.0.0/js/bootstrap.min.js"></script>

<div class="app-container">
  <div class="layout-container layout-screen">
    <div class="layout-header">
      <nav class="navbar bg-light">
        <a class="navbar-brand" href="#">Codepasser IO Platform [WEB-API]</a>
      </nav>
    </div>
    <div class="layout-main container mt-4">
      <form class="thumbnail w-75" role="form" action="login" method="post">
        <div class="form-group row">
          <label class="col-sm-4 col-form-label text-right" for="username">登录</label>
          <div class="col-sm-8">
            <input type="text" class="form-control" name="username" id="username" placeholder="Username/Phone/Email">
          </div>
        </div>
        <div class="form-group row">
          <label class="col-sm-4 col-form-label text-right" for="password">密码</label>
          <div class="col-sm-8">
            <input type="password" class="form-control" name="password" id="password" placeholder="password">
          </div>
        </div>
        <div class="form-group row">
          <div class="col-sm-4"></div>
          <div class="col-sm-4">
            <input type="hidden" class="form-control" name="target-url" value="/index">
            <input type="hidden" class="form-control" name="remember-me" value="true">
            <button type="submit" class="btn btn-primary w-100">登录</button>
          </div>
          <div class="col-sm-4">
            <button type="submit" class="btn btn-primary w-100">注册</button>
          </div>
        </div>
      </form>
    </div>
    <div class="layout-footer d-flex p-2 justify-content-around">

      <div class="card" style="width: 18rem;">
        <div class="card-body">
          <h5 class="card-title">CORS request</h5>
          <p class="card-text">CORS是一个W3C标准，全称是"跨域资源共享"（Cross-origin resource sharing）。它允许浏览器向跨源(协议
            + 域名 + 端口)服务器，发出XMLHttpRequest请求，从而克服了AJAX只能同源使用的限制。</p>
          <button id="corsBtn" class="btn btn-primary w-100">TEST</button>
        </div>
      </div>

      <div class="card" style="width: 18rem;">
        <div class="card-body">
          <h5 class="card-title">SSO</h5>
          <p class="card-text">SSO英文全称Single Sign
            On，单点登录。SSO是在多个应用系统中，用户只需要登录一次就可以访问所有相互信任的应用系统。它包括可以将这次主要的登录映射到其他应用中用于同一个用户的登录的机制.</p>
          <a class="btn btn-primary w-100" href="/web-oauth">WEB-API</a>
        </div>
      </div>

      <div class="card" style="width: 18rem;">
        <div class="card-body">
          <h5 class="card-title">OAuth2.0</h5>
          <p class="card-text">OAuth2.0是OAuth协议的延续版本，但不向后兼容OAuth 2.0即完全废止了OAuth1.0。 OAuth
            2.0关注客户端开发者的简易性。要么通过组织在资源拥有者和HTTP服务商之间的被批准的交互动作代表用户，要么允许第三方应用代表用户获得访问的权限。同时为Web应用，桌面应用和手机，和起居室设备提供专门的认证流程。2012年10月，OAuth
            2.0协议正式发布为RFC.</p>
          <a class="btn btn-primary w-100" href="/web-oauth/login/github">Github</a>
        </div>
      </div>

    </div>
  </div>
</div>
</body>
<script type="application/javascript">

  /*Mock cors*/
  $(function () {
    $("#corsBtn").click(function () {
      $.ajax({
        type: 'GET',
        // CORS : www.codepasser.com
        url: 'http://127.0.0.1:8001${rc.contextPath}/sample/log/info',
        cache: false,
        // data: JSON.stringify(data),
        contentType: "application/json",
        dataType: "json",
        success: function (result) {
          console.log(">success", result);
        },
        error: function (result) {
          console.log(">error", result);
        }
      });
    });
  })

</script>
</html>
