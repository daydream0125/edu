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
        background-color: #eaeff2;
    }
    p{
        color: #0f0f0f;
        font-size: 16px
    }
    table {
        color: #0f0f0f;
        font-size: 20px
    }
    hr {
        display: block;
        -webkit-margin-before: 0.5em;
        -webkit-margin-after: 0.5em;
        -webkit-margin-start: auto;
        -webkit-margin-end: auto;
        border: 1px inset;
    }
    a {
        color: #333;
        text-decoration: none;
        /*text-decoration-style: initial;*/
        /*text-decoration-color: initial;*/
        font-weight: bold;
    }
    ul {
       list-style-type:square;
    }
    a:hover {color: #ff9322;
        text-decoration: none;
    }


</style>