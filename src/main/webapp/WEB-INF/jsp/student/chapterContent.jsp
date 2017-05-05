<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="../static.jsp" %>
<html>
<head>
    <title>课程内容</title>
</head>
<body>
<%@include file="../navigation.jsp"%>
<div class="container">
    <h1>小节内容</h1>
    <hr>
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">小节内容</h3>
        </div>
        <div class="panel-body">
            <p>${chapter.chapterContent.content}</p>
        </div>
    </div>
</div> <!-- /container -->
<%@include file="../js.jsp"%>

<script>
    new Vue({
        el:"#student-course",
        data:{
            isActive:true
        }
    })

</script>
</body>
</html>