<%@ page pageEncoding="UTF-8" language="java" %>
<%@include file="../static.jsp" %>
<html>
<head>
    <title>课程信息</title>
    <style>
        body {
            margin-top: 50px;
        }

        .background-img {
            display: block;
            background-image: url('bg.jpg');
            background-repeat: no-repeat;
            background-size: cover;
            background-attachment: fixed;
            overflow: hidden;
            width: 100%;
            height: 300px;
        }

        .frosted-glass {
            width: 100%;
            height: 300px;
            background: inherit;
            -webkit-filter: blur(5px);
            -moz-filter: blur(5px);
            -ms-filter: blur(5px);
            -o-filter: blur(5px);
            filter: blur(5px);
            filter: progid:DXImageTransform.Microsoft.Blur(PixelRadius=4, MakeShadow=false);
        }

        .content {
            margin-top: -250px;
            margin-left: 100px;
            position: relative;
            line-height: 31px;
            font-weight: 700;
            font-size: 32px;
            color: #fff;
        }

        p {
            color: #0f0f0f;
            font-size: 20px
        }

        table {
            color: #0f0f0f;
            font-size: 20px
        }

        #menu p {
            font-size: 16px;
            font-weight: 700;
        }
    </style>
</head>
<body>
<%@include file="../navigation.jsp" %>
<div id="container">
    <div class="background-img">
        <div class="frosted-glass">
        </div>
        <div class="content">
            <h1>{{course.courseName}}</h1>
            <br>
            讲师&nbsp;{{course.teacher.name}}
        </div>
    </div>
    <br>
    <div class="container">
        <p>简介:{{course.courseDescription}}
        </p>

        <br>
        <sec:authorize access="hasAnyRole('ROLE_STUDENT','ROLE_TEACHER')">
            <i-button type="primary" size="large" @click="goToChapters">查看课程内容</i-button>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_TEACHER')">
            <i-button type="primary" size="large" @click="goToCourseManage">课程管理</i-button>
        </sec:authorize>
        <br><br>
        <div id="menu">
            <i-menu mode="horizontal" theme="light" active-name="1" @on-select="changeMenu">
                <Row>
                    <i-col span="5">
                        <Menu-item name="1">
                            <p>课程信息</p>
                        </Menu-item>
                    </i-col>
                    <i-col span="5">
                        <Menu-item name="2">
                            <p>讲师信息</p>
                        </Menu-item>
                    </i-col>
                    <i-col span="5">
                        <Menu-item name="3">
                            <p>班级信息</p>
                        </Menu-item>
                    </i-col>
                </Row>
            </i-menu>
            <br>
        </div>
        <div id="courseInfo" v-if="menu==1">
            <table class="table">
                <thead>
                    <tr>
                        <th>开始时间</th>
                        <th>考试时间</th>
                        <th>考试类型</th>
                        <th>是否结课</th>
                        <th>当前学习人数</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                       <td>{{course.startTime}}</td>
                        <td>{{course.examTime}}</td>
                        <td><Tag type="border" color="blue">{{course.examType | formatExamType}}</Tag></td>
                        <td><Tag type="border" color="blue">{{course.isFinish | formatIsFinish}}</Tag></td>
                        <td><Tag type="border" color="blue">{{studentNum}}</Tag></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div id="teacherInfo" v-if="menu==2">
            <table class="table">
                <thead>
                    <tr>
                        <th>老师</th>
                        <th>性别</th>
                        <th>邮箱</th>
                        <th>手机</th>
                        <th>学校</th>
                        <th>地址</th>
                        <th>上次登录时间</th>
                    </tr>
                </thead>
                <tbody>
                <tr>
                    <td>{{course.teacher.name}}</td>
                    <td>{{course.teacher.userInfo.sex}}</td>
                    <td>{{course.teacher.userInfo.email}}</td>
                    <td>{{course.teacher.userInfo.telephone}}</td>
                    <td>{{course.teacher.userInfo.major.name}}</td>
                    <td>{{course.teacher.userInfo.address}}</td>
                    <td>{{course.teacher.lastLoginTime}}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div id="classInfo" v-if="menu==3">
            <table class="table hover">
                <thead>
                <tr>
                    <th>班级</th>
                    <th>创建时间</th>
                    <th>状态</th>
                    <th>注册</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="clazz in clazzes">
                    <td>{{clazz.className}}</td>
                    <td>{{clazz.createTime}}</td>
                    <td v-if="clazz.isFinish">
                        <Tag type="border" color="yellow">已结束</Tag>
                    </td>
                    <td v-else>
                        <Tag type="border" color="green">正在进行</Tag>

                    </td>
                    <sec:authorize access="hasRole('ROLE_STUDENT')">
                        <td v-if="clazz.isPublicRegister">
                            <div v-if="checkIsRegister(${sessionScope.account.userId},clazz.classId)">
                                <Tag type="border" color="green">已注册
                                </Tag>
                            </div>
                            <div v-else>
                                <i-button type="ghost" @click="register(${sessionScope.account.userId},clazz.classId)">注册</i-button>
                            </div>
                        </td>
                        <td v-else>
                            <Tag type="border" color="yellow">未开放</Tag>
                        </td>
                    </sec:authorize>
                    <sec:authorize access="not hasRole('ROLE_STUDENT')">
                        <td>
                            <Tag type="border" color="red">没有权限</Tag>
                        </td>
                    </sec:authorize>
                </tr>
                </tbody>
            </table>
        </div>

    </div> <!-- /container -->
</div>

<%@include file="../js.jsp" %>
<script>
    new Vue({
        el: '#container',
        data: {
            courseId:${courseId},
            course: {},
            clazzes: [],
            studentNum: 0,
            menu: '1',
            isRegister: false
        },
        methods: {
            getCourseInfo: function (courseId) {
                $.get("course/info/" + courseId, function (data) {
                    this.course = data;
                }.bind(this))
            },
            getStudentNum: function (courseId) {
                $.get("course/" + courseId + "/studentNum", function (data) {
                    this.studentNum = data;
                }.bind(this))
            },
            getClazzesByCourseId: function (courseId) {
                $.get("course/" + courseId + "/clazzes", function (data) {
                    this.clazzes = data;
                }.bind(this))
            },
            checkIsRegister: function (userId, classId) {
                let flag = false;
                $.ajax({
                    url:"course/isRegister",
                    type:"GET",
                    async : false,
                    data:{
                        userId:userId,
                        classId:classId
                    },
                    success:function (data) {
                        flag = data;
                    }.bind(this)
                });
                return flag;
            },
            register:function (userId, classId) {
              $.post("course/register",{userId:userId,classId:classId,courseId:this.courseId},function (data) {
                  if (data === true) {
                      alert("注册成功!");
                  } else {
                      alert("注册失败!请重新注册或联系老师");
                  }
              })
            },
            goToChapters:function () {
                //跳转至课程章节信息
              window.location.href="course/" + this.courseId + "/courseChapterInfo";
            },
            goToCourseManage:function () {
              window.location.href="teacher/courseManage/" + this.courseId;
            },
            changeMenu: function (name) {
                this.menu = name;
            }
        },
        mounted: function () {
            this.getCourseInfo(this.courseId);
            this.getStudentNum(this.courseId);
            this.getClazzesByCourseId(this.courseId);
        },
        filters: {
            formatExamType: function (type) {
                switch (type) {
                    case 1:
                        return '提交论文';
                    case 2:
                        return '开卷考试';
                    case 3:
                        return '闭卷考试';
                    case 4:
                        return '开卷机试';
                    case 5:
                        return '闭卷机试';
                }
            },
            formatIsFinish: function (flag) {
                return flag === true ? '已结课' : '正在进行';
            }
        }
    })
</script>
</body>
</html>