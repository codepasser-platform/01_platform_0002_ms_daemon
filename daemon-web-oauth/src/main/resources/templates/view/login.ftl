<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="description" content="">
  <meta name="author" content="">

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
        <a class="navbar-brand" href="#">Codepasser IO Platform [WEB-OAUTH]</a>
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
            <button type="submit" class="btn btn-primary w-100">登录</button>
          </div>
          <div class="col-sm-4">
            <button type="submit" class="btn btn-primary w-100">注册</button>
          </div>
        </div>
      </form>
    </div>
    <div class="layout-footer">
      <div class="form-group row">
        <button id="corsBtn" class="btn btn-primary w-100">testing cors request</button>
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
        url: 'http://www.codepasser.com${rc.contextPath}/sample/log/info',
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
