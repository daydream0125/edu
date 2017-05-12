<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <%--<%@include file="../static.jsp" %>--%>
    <title>Title</title>
    <style>
    </style>
    <link href="static/editor/css/wangEditor.min.css" type="text/css" rel="stylesheet">
</head>
<body>
<%--<%@include file="../navigation.jsp" %>--%>
<div id="container" class="container">
    <form action="${pageContext.request.contextPath}/videoTest" enctype="multipart/form-data" method="post">
        <input type="file" name="video">
        <input type="submit">
    </form>
</div>
<%@include file="../js.jsp" %>
<script src="https://cdn.bootcss.com/echarts/3.5.4/echarts.common.js"></script>

<script type="text/javascript">


</script>
</body>
</html>
