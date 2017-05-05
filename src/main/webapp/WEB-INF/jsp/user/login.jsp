<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <%@include file="../static.jsp"%>
    <title>Login</title>
    <link href="bootstrap/css/signin.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <form class="form-signin" role="form" action="j_security_check" method="POST">
        <h2 class="form-signin-heading">用户登录</h2>
        <input type="text" name="username" class="form-control" placeholder="username" required autofocus>
        <input type="password" name="password" class="form-control" placeholder="password" required>
         <label class="checkbox">
          <input type="checkbox" value="remember-me"> Remember me
        </label>

        <c:if test="${not empty param.error}">
            <font color="red">用户名或者密码错误</font>
        </c:if>
        <c:if test="${not empty param.message}">
            <font color="green">${param.message}</font>
        </c:if>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>

</div> <!-- /container -->
<%@include file="../js.jsp"%>
</body>
</html>