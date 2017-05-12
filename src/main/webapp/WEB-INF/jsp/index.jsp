<%@ page pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <meta charset="utf-8">
    <title>精准教育</title>
    <%@include file="static.jsp" %>
    <style>
        body {
            margin-top: 50px;
            font-size: medium;
        }

        .index-course {
            height: 250px;
            width: 100%;
            overflow: hidden;
        }

        .carousel-caption h1, h2, h3 {
            color: #0f0f0f;
        }
    </style>
</head>
<body>
<%@include file="navigation.jsp" %>
<div id="container">
    <div id="carousel" class="carousel slide" data-ride="carousel" style="background: indexbg.png">
        <!-- Wrapper for slides -->
        <div class="carousel-inner" role="listbox">
            <div class="item active index-course">
                <div class="carousel-caption">
                    <h1>注册以关注更多课程内容</h1>
                    <div>
                        <i-button type="success" size="large" @click="register">立即注册</i-button>
                    </div>
                </div>
            </div>
            <div class="item index-course" v-for="c in topCourses">
                <div class="carousel-caption">
                    <h1 style="color: #0f0f0f">{{c[1]}}</h1>
                    <h3>
                        {{c[2]}}
                    </h3>
                    <div style="margin-top: 10px">
                        <i-button size="large" type="success" @click="courseInfo(c[0])">查看详情</i-button>
                    </div>
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
        <div  style="color: #004772;font-weight: bold;margin-top: 20px">
            <span>当前位置：</span>
            <a href="javascript:">首页</a>
            <hr>
        </div>
        <h1>全部课程</h1>
        <div>
        <Card style="width:350px;height: 400px;float: left;margin-left: 10px;margin-top: 10px" v-for="c in courses">
            <p slot="title" style="font-size: 18px">
                {{c[1]}}
            </p>
            <div style="width: 100%;height: 200px;overflow: hidden">
                <img :src="c[3]" alt="" style="width: 100%;height: 200px;overflow: hidden">
            </div>
            <div style="height: 90px;overflow: hidden">
                <p style="font-size: 14px">{{c[2]}}</p>
            </div>
            <div style="margin-bottom: 10px">
                <i-button @click="courseInfo(c[0])" type="primary">查看详情</i-button>
            </div>
        </Card>
        </div>
    </div>
    <div style="margin-left: 200px;margin-top: 40px;margin-bottom: 100px">
        <Page :total="count" @on-change="fetchData" show-total :page-size="pageSize" show-elevator>
        </Page>
    </div>
    </div>
</div>

<%@include file="js.jsp" %>
<script>
    new Vue({
        el: '#container',
        data: {
            courses: [],
            topCourses: [],
            pageSize:9,
            pageNow:1,
            count:0,
        },
        methods: {
            register: function () {
                window.location.href = "sign";
            },
            courseInfo: function (courseId) {
                window.location.href = "course/" + courseId;
            },
            getCourses: function () {
                $.get("courses/count",function (data) {
                    this.count = data;
                    $.get("courses",
                        {
                            pageSize:this.pageSize,
                            pageNow:this.pageNow,
                            count:this.count
                        }, function (data) {
                            this.courses = data;
                            this.topCourses = this.courses.slice(0, 4);
                        }.bind(this))
                }.bind(this));
            },
            fetchData:function (page) {
                this.pageNow = page;
                $.get("courses",{pageNow:page,pageSize:this.pageSize,count:this.count},function (data) {
                    this.courses = data;
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