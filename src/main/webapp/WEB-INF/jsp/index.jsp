<%@ page pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <%@include file="static.jsp" %>
    <style>
        body {
            margin-top: 50px;
            font-size: medium;
        }

        .index-course {
            height: 400px;
            width: 100%;
            overflow: hidden;
        }

        .carousel-caption h1, h2, h3 {
            color: #0f0f0f;
        }

        .course-block {
            width: 350px;
            height: 450px;
            float: left;
            overflow: hidden;
        }
    </style>
</head>
<body>
<%@include file="navigation.jsp" %>
<div id="container">
    <div id="carousel" class="carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <li data-target="#carousel" data-slide-to="0" class="active"></li>
            <template v-for="n in 4">
                <li data-target="#carousel" :data-slide-to="n"></li>
            </template>
        </ol>
        <!-- Wrapper for slides -->
        <div class="carousel-inner" role="listbox">
            <div class="item active index-course">
                <div class="carousel-caption">
                    <h1>注册以关注更多课程内容</h1>
                    <i-button type="success" size="large" @click="register">立即注册</i-button>
                </div>
            </div>
            <div class="item index-course" v-for="c in topCourses">
                <div class="carousel-caption">
                    <h1 style="color: #0f0f0f">{{c.courseName}}</h1>
                    <h3>
                        {{c.courseDescription}}
                    </h3>
                    <h2>开课老师：{{c.teacher.name}}</h2>
                    <h3>时间：{{c.startTime}}--{{c.endTime}}</h3>
                    <i-button size="large" type="success" @click="courseInfo(c.courseId)">查看详情</i-button>
                </div>
            </div>


        </div>
        <!-- Controls -->
        <a class="left carousel-control" href="#carousel" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#carousel" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
    <div class="container">
        <h1>全部课程</h1>
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

<%@include file="js.jsp" %>
<script>
    new Vue({
        el: '#container',
        data: {
            courses: [],
            topCourses: [],
        },
        methods: {
            register:function () {
              window.location.href = "register";
            },
            courseInfo: function (courseId) {
                window.location.href = "course/" + courseId;
            },
            getCourses: function () {
                $.get("courses", function (data) {
                    this.courses = data;
                    this.topCourses = this.courses.slice(0, 4);
                }.bind(this))
            }
        },
        mounted: function () {
            this.getCourses();
        }

    })
</script>
</body>
</html>