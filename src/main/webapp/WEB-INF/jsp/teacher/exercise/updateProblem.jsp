<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="../../static.jsp" %>
<html>
<head>
    <title>题库管理</title>
</head>
<body>
<%@include file="../../navigation.jsp"%>

<div class="container">
    <form action="${pageContext.request.contextPath}/teacher/exercise/updateProblem" method="post">
        <label>
            <input type="text" name="problemId" value="${problem.problemId}" hidden>
        </label>
        <div class="form-group">
            <label for="section">题目所属小节</label>
            <input id="section" class="form-control"
                   value="弟${problem.courseChapter.num}节、${problem.courseChapter.title}" disabled/>
        </div>
        <div class="form-group">
            <label for="title">题目</label>
            <input name="title" id="title" class="form-control" value="${problem.title}"/>
        </div>

        <c:if test="${problem.type == 1}">
            <div class="form-group">
                <%
                    request.setAttribute("vEnter", "\n");
                %>
                <c:set var="desc" value="${problem.description}"/>
                <c:set var="choice" value="${fn:split(desc,vEnter)}"/>
                <div class="input-group">
                    <span class="input-group-addon">A</span>
                    <input type="text" class="form-control" value="${choice[0]}" name="choice">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">B</span>
                    <input type="text" class="form-control" value="${choice[1]}" name="choice">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">C</span>
                    <input type="text" class="form-control" value="${choice[2]}" name="choice">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">D</span>
                    <input type="text" class="form-control" value="${choice[3]}" name="choice">
                </div>
                    <%--
                    动态打印选项
                    <%
                        request.setAttribute("vEnter", "\n");
                        char a = 'A';
                    %>
                    <c:set var="desc" value="${problem.description}"/>

                    <c:forEach items="${fn:split(desc,vEnter)}" var="d">--%>
                    <%--<div class="input-group">--%>
                    <%--<span class="input-group-addon" id=""><%=a++%></span>--%>
                    <%--<label>--%>
                    <%--<input type="text" class="form-control" value="${d}" name="choice">--%>
                    <%--</label>--%>
                    <%--</div>--%>
                    <%--</c:forEach>--%>
            </div>
        </c:if>
        <c:if test="${problem.type != 1}">
            <label for="description ">
                <label for="description ">题目信息</label>
                <textarea id="description " class="form-control">${problem.description}</textarea>
            </label>
        </c:if>
        <div class="form-group">
            <label for="solution">参考答案</label>
            <textarea id="solution" class="form-control" name="solution">${problem.solution}</textarea>
        </div>

        <label for="judgeType">是否人工批改</label>
        <div id="judgeType">
            <div class="radio">
                <label>
                    <input type="radio" name="isManualJudge" value="0">
                    是
                </label>
            </div>
            <div class="radio">
                <label>
                    <input type="radio" name="isManualJudge" value="1">
                    否
                </label>
            </div>
        </div>

        <button type="submit" class="btn btn-primary">更新</button>
    </form>
</div>
<%@include file="../../js.jsp"%>

<script>
    new Vue({
        el:"#teacher-problem",
        data:{
            isActive:true
        }
    })

</script>
</body>
</html>

