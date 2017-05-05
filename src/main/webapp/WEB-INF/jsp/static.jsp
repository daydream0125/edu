<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    //项目绝对路径
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">
<meta charset="utf-8">
<link href="http://cdn.bootcss.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="http://unpkg.com/iview/dist/styles/iview.css">
<link rel="shortcut icon" href="http://bootstrap.evget.com/assets/ico/favicon.ico">
<style>
    body{
        margin-top: 70px;
        font-size: medium;
        color: black;
    }
    p{
        color: #0f0f0f;
        font-size: 20px
    }
    table {
        color: #0f0f0f;
        font-size: 20px
    }
</style>