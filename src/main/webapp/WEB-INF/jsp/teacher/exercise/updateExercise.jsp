<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="../../static.jsp" %>
<html>
<head>
    <title>更新作业</title>
</head>
<body>
<%@include file="../../navigation.jsp"%>

<div class="container">
    <div class="btn-group btn-group-lg">
        <a href="${pageContext.request.contextPath}/teacher/exercise/addExercise?teacherId=${sessionScope.account.userId}" class="btn btn-default">新增作业</a>
        <a href="${pageContext.request.contextPath}/teacher/exercise/releaseList?teacherId=${sessionScope.account.userId}" class="btn btn-default">发布作业</a>
        <a href="${pageContext.request.contextPath}/teacher/exercise/viewExercise?teacherId=${sessionScope.account.userId}" class="btn btn-default">查看作业</a>
        <a href="" class="btn btn-default">批改作业</a>
    </div>
    <hr>
    <h3>更新作业</h3>
    <form action="${pageContext.request.contextPath}/teacher/exercise/updateExercise" method="post"
          class="form-horizontal">
        <div class="form-group">
            <label for="course" class="col-sm-2 control-label">课程</label>
            <div class="col-sm-5">
                <input type="text" id="course" value="${exercise.course.courseName}" disabled class="form-control">
                <input type="text" hidden name="exerciseId" value="${exercise.exerciseId}">
            </div>
        </div>
        <div class="form-group">
            <label for="clazz" class="col-sm-2 control-label">班级</label>
            <div class="col-sm-5">
                <input type="text" id="clazz" value="${exercise.clazz.className}" disabled class="form-control">
            </div>
        </div>
        <div class="form-group">
            <label for="exerciseName" class="col-sm-2 control-label">作业名称</label>
            <div class="col-sm-5">
                <input type="text" name="exerciseName" id="exerciseName" class="form-control" value="${exercise.exerciseName}">
            </div>
        </div>
        <div class="form-group">
            <label for="exerciseDesc" class="col-sm-2 control-label">作业描述</label>
            <div class="col-sm-5">
                <textarea name="exerciseDesc" rows="3" class="form-control" id="exerciseDesc">${exercise.exerciseDesc}</textarea>
            </div>
        </div>
        <div class="form-group">
            <label for="startTime" class="col-sm-2 control-label">开始时间</label>
            <div class="col-sm-5">
                <input type="date" name="startTime" class="form-control" id="startTime" value="${exercise.startTime}" required>
            </div>
        </div>
        <div class="form-group">
            <label for="endTime" class="col-sm-2 control-label">结束时间</label>
            <div class="col-sm-5">
                <input type="date" name="endTime" class="form-control" id="endTime" value="${exercise.endTime}" required>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">最终考试</label>
            <div class="col-sm-5">
                <label for="yes" class="radio-inline">是</label>
                <input type="radio" name="isFinal" id="yes" value="1">
                <label for="no" class="radio-inline">否</label>
                <input type="radio" name="isFinal" id="no" value="0">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button type="submit" class="btn btn-default">提交</button>
            </div>
        </div>
    </form>

</div>
<%@include file="../../js.jsp"%>

<script>
    new Vue({
        el:"#teacher-exercise",
        data:{
            isActive:true
        }
    })

</script>
</body>
</html>