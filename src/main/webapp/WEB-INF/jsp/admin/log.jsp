<%@ page pageEncoding="UTF-8" language="java" %>
<%@include file="../static.jsp" %>
<html>
<head>
    <title>日志管理</title>
</head>
<body>
<%@include file="../navigation.jsp"%>
<div class="container">

    <!-- Main component for a primary marketing message or call to action -->
    <h1>日志管理</h1>
    <table class="table table-hover">
        <tr>
            <th>编号</th>
            <th>操作人</th>
            <th>操作</th>
            <th>操作时间</th>
            <th>操作IP</th>
        </tr>
        <c:if test="${ not empty logs}">
            <c:forEach items="${logs}" var="log">
                <tr>
                    <td>${log.id}</td>
                    <td>${log.userId}</td>
                    <td>${log.operation}</td>
                    <td>${log.operTime}</td>
                    <td>${log.ip}</td>
                </tr>
            </c:forEach>
        </c:if>
    </table>
    <%@include file="../js.jsp"%>
</div> <!-- /container -->
</body>
</html>
