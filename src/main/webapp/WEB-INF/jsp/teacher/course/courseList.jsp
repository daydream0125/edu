<%@ page pageEncoding="UTF-8" language="java" %>
<%@include file="../../static.jsp" %>
<html>
<head>
    <title>课程管理</title>
</head>
<body>
<%@include file="../../navigation.jsp" %>

<div class="container" id="container">
    <div style="color: #004772;font-weight: bold">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">课程管理</a>
        <hr>
    </div>
    <div style="float: right;margin-right: 100px;height: 40px">
        <span><i-button type="primary" size="large" @click="addCourse">开设新课</i-button></span>
    </div>

    <div style="margin-top: 50px">
        <Card style="width:350px;height: 400px;float: left;margin-left: 10px;margin-top: 10px" v-for="c in courses">
            <p slot="title" style="font-size: 18px">
                {{c.courseName}}
            </p>
            <div style="width: 100%;height: 200px;overflow: hidden">
                <img :src="c.coursePic" alt="" style="width: 100%;height: 200px;overflow: hidden">
            </div>
            <div style="height: 90px;overflow: hidden">
                <p style="font-size: 14px">{{c.courseDescription}}</p>
            </div>
            <div style="margin-bottom: 10px">
                <i-button @click="courseInfo(c.courseId)" type="primary">查看详情</i-button>
            </div>
        </Card>
    </div>

</div>
<%@include file="../../js.jsp" %>

<!-- /container -->
<script>
    new Vue({
        el: '#container',
        data: {
            teacherId:<sec:authentication property="principal.username" />,
            courses: []
        },
        methods: {
            addCourse: function () {
                window.location.href = "teacher/addCourse"
            },
            courseInfo: function (courseId) {
                window.location.href = "course/" + courseId;
            },
            getCoursesByTeacherId: function (teacherId) {
                $.get("teacher/" + teacherId + "/courses", function (data) {
                    this.courses = data;
                }.bind(this));
            }
        },
        mounted: function () {
            this.getCoursesByTeacherId(this.teacherId);
        }
    });
    new Vue({
        el: "#teacher-course",
        data: {
            isActive: true
        }
    })

</script>
</body>
</html>
