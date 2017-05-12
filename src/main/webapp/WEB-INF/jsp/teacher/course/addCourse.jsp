<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="../../static.jsp"%>
<html>
    <head>
        <title>添加课程</title>
        <style>
            form{
                width: 500px;
                margin-left: 200px;
            }
        </style>
    </head>
<body>
<%@include file="../../navigation.jsp"%>

<div class="container">
    <div style="color: #004772;font-weight: bold">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="teacher/courseList">课程管理</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">新增</a>
        <hr>
    </div>
    <!-- Main component for a primary marketing message or call to action -->
        <h1>添加课程</h1>
        <form action="${pageContext.request.contextPath}/teacher/saveCourse" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <label for="teacher">开课老师</label>
                <input type="text" class="form-control" id="teacher" value="${<sec:authentication property="principal.username" />.account.name}" readonly>
                <input type="hidden" name="teacherId" value="<sec:authentication property="principal.username" />">
            </div>
            <div class="form-group">
                <label for="courseName">课程名称</label>
                <input type="text" id="courseName" class="form-control" name="courseName" placeholder="课程名称" required>
            </div>
            <div class="form-group">
                <label for="courseDescription">课程描述</label>
                <textarea id="courseDescription" class="form-control" name="courseDescription" placeholder="课程描述" required>
                </textarea>
            </div>
            <div class="form-group">
                <label for="coursePic">课程图片</label>
                <input type="file" class="form-control" name="coursePicture" id="coursePic">
            </div>
            <div class="form-group">
                <label for="startTime">开始时间</label>
                <input type="date" id="startTime" class="form-control" name="startTime" placeholder="开始时间" required>
            </div>
            <div class="form-group">
                <label for="endTime">结束时间</label>
                <input type="date" id="endTime" class="form-control" name="endTime" placeholder="结束时间" required>

            </div>
            <div class="form-group">
                <label for="examTime">考试时间</label>
                <input type="date" id="examTime" class="form-control" name="examTime" placeholder="考试时间" required>

            </div>

            <label>考试形式</label>
            <div class="radio">
                <label>
                    <input type="radio" name="examType" value="1" checked>
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