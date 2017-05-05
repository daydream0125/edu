<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" language="java" %>
<%
	//项目绝对路径
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName()
			+ ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
	<title>Login</title>
	<meta charset="utf-8">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<link rel="shortcut icon" href="http://bootstrap.evget.com/assets/ico/favicon.ico">
	<base href="<%=basePath%>">
	<style>
		span{
			color: red;
		}
	</style>
	<!-- Bootstrap core CSS -->
	<!-- link href=里面的../表示当前应用的根目录，此时bootstrap放在webapp目录下面 -->
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

	<!-- Custom styles for this template -->
	<link href="bootstrap/mysignin/signin.css" rel="stylesheet">

	<!-- Just for debugging purposes. Don't actually copy this line! -->
	<!--[if lt IE 9]>
	<script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->

	<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
	<script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	<script type="text/javascript">
        $(function(){  //生成验证码
            $('#kaptchaImage').click(function () {
                $(this).hide().attr('src', '/code/captcha-image?' + Math.floor(Math.random()*100) ).fadeIn(); });
        });

        window.onbeforeunload = function(){
            //关闭窗口时自动退出
            if(event.clientX>360&&event.clientY<0||event.altKey){
                alert(parent.document.location);
            }
        };
        function check(){
            if(document.getElementById("password").value!=
                document.getElementById("password_check").value)
            {
                document.getElementById("warning").innerHTML="   两次密码的输入不一致";
            }else{
                document.getElementById("warning").innerHTML="";
            }
        }

        function reloadImage(imgurl){
            var getimagecode=document.getElementById("codeimg");
            getimagecode.src= imgurl + "?id=" + Math.random();
        }
	</script>
</head>

<body>
<div class="container">
	<div class="col-md-5 col-md-offset-4">
	<form class="form-signin" role="form" action="${pageContext.request.contextPath }/register_info" method="POST">
		<h2 class="form-signin-heading">用户注册</h2>
		<input type="text" name="username" class="form-control" placeholder="用户名/手机号" required autofocus>
		<c:if test="${not empty param.accountIsExist}">
			<span>该账户已注册</span>
		</c:if>
		<input type="password" name="password" id="password" class="form-control" placeholder="密码" required>
		<input type="password" name="password_check" id="password_check" class="form-control" placeholder="确认密码" required onblur="check()">
		<span id="warning"></span>

		<input type="text" name="code" maxlength="4" class="form-control" placeholder="验证码" required>
		<br>
		<img id="codeimg" name="codeimg" border=0 src="${pageContext.request.contextPath}/captcha-image">
		<a href="javascript:reloadImage('captcha-image')">看不清？换一张</a><br/>
		<br>
		<c:if test="${not empty param.codeError}">
			<span>验证码填写错误</span>
		</c:if>
		<button class="btn btn-lg btn-primary btn-block" type="submit">注册</button>

	</form>
	</div>

</div> <!-- /container -->
<%@include file="../js.jsp"%>
</body>
</html>