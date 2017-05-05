<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="../static.jsp" %>
<html>
<head>
    <title>课程内容</title>
</head>
<body>
<%@include file="../navigation.jsp"%>
<div class="container">
    <h1>课程内容</h1>
    <hr>
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">课程内容</h3>
        </div>
        <div class="panel-body">
            <hr>
            <c:if test="${not empty chapters}">
                <c:forEach items="${chapters}" var="chapter">
                    <div class="h4">

                        <a class="h4" data-toggle="collapse"
                           href="#collapse${chapter.num}">
                            <span class="glyphicon glyphicon-align-left" aria-hidden="true"></span>
                            第${chapter.num}章、${chapter.title}</a>
                    </div>
                    <div class="list-group" id="collapse${chapter.num}">
                        <c:forEach items="${chapter.childChapters}" var="c">
                            <a href="student/chapterContent?chapterId=${c.id}" class="list-group-item">${chapter.num}-${c.num}&nbsp;&nbsp;${c.title}</a>
                        </c:forEach>
                    </div>
                    <hr>
                </c:forEach>
            </c:if>
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