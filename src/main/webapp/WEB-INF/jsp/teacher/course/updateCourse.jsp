<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="../../static.jsp"%>
<html>
<head>
    <title>修改课程</title>
</head>
<body>
<%@include file="../../navigation.jsp"%>

<div class="container">

    <h1>修改课程</h1>
    <form action="${pageContext.request.contextPath}/teacher/course/updateCourse" method="post">
        <div class="form-group">
            <label for="teacher">开课老师</label>
            <input type="text" class="form-control" id="teacher" value="${sessionScope.account.name}" readonly>
            <input type="hidden" name="teacherId" value="${sessionScope.account.userId}">
            <input type="hidden" name="courseId" value="${course.courseId}">
        </div>
        <div class="form-group">
            <label for="courseName">课程名称</label>
            <input type="text" id="courseName" class="form-control" name="courseName" value="${course.courseName}" required>
        </div>
        <div class="form-group">
            <label for="courseDescription">课程描述</label>
            <textarea id="courseDescription" class="form-control" name="courseDescription"  required>
                ${course.courseDescription}
                </textarea>
        </div>
        <div class="form-group">
            <label for="startTime">开始时间</label>
            <input type="date" id="startTime" class="form-control" name="startTime" value="${course.startTime}" required>
        </div>
        <div class="form-group">
            <label for="endTime">结束时间</label>
            <input type="date" id="endTime" class="form-control" name="endTime" value="${course.endTime}"  required>

        </div>
        <div class="form-group">
            <label for="examTime">考试时间</label>
            <input type="date" id="examTime" class="form-control" name="examTime" value="${course.examTime}" required>

        </div>

        <label>考试形式</label>
        <div class="radio">
            <label>
                <input type="radio" name="examType" value="1">
                提交论文
            </label>
        </div>
        <div class="radio">
            <label>
                <input type="radio" name="examType" value="2">
                开卷考试
            </label>
        </div>
        <div class="radio">
            <label>
                <input type="radio" name="examType" value="3">
                闭卷考试
            </label>
        </div>
        <div class="radio">
            <label>
                <input type="radio" name="examType" value="4">
                开卷机试
            </label>
        </div>
        <div class="radio">

            <label>
                <input type="radio" name="examType" value="5">
                闭卷机试
            </label>
        </div>
        <button type="submit" class="btn btn-default">提交</button>
    </form>
</div> <!-- /container -->
<%@include file="../../js.jsp"%>

<script>
    new Vue({
        el:"#teacher-course",
        data:{
            isActive:true
        }
    })

</script>
</body>
</html>