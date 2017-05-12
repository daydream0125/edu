<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>我的课程</title>
    <%@include file="../static.jsp" %>
    <style>
        body {
            margin-top: 70px;
            font-size: medium;
        }

        .list-item {
            border-bottom: 1px dashed rgb(207, 208, 209);
            height: 36px;
            line-height: 36px;
            margin-left: 400px;margin-right: 100px
        }
    </style>
</head>
<body>
<%@include file="../navigation.jsp" %>
<div id="app" class="container">
    <div  style="color: #004772;font-weight: bold;margin-bottom: 20px">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">我的课程</a>
        <hr>
    </div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>课程名称</th>
            <th>班级</th>
            <th>老师</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>考试时间</th>
            <th>考试形式</th>
            <th>状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(c,index) in currentClazzes">
            <td>
                <i-button type="ghost" @click="courseInfo(c.course.courseId)">
                    {{c.course.courseName}}
                </i-button>
            </td>
            <td>{{c.clazz.className}}</td>
            <td>{{c.course.teacher.name}}</td>
            <td>{{c.clazz.startTime}}</td>
            <td>{{c.clazz.endTime}}</td>
            <td>{{c.clazz.examTime}}</td>
            <td>
                {{c.course.examType | formatExamType}}
            </td>
            <td>
                <Tag :color="c.clazz.isFinish?'blue':'green'">{{c.clazz.isFinish | formatIsFinish}}
                </Tag>
            </td>
            <td>
                <i-button type="primary" @click="moreInfo(index)">更多信息</i-button>
            </td>
        </tr>
        </tbody>
    </table>
    <Row>
        <i-col offset="14">
            <Page :total="clazzLen" @on-change="fetchData" show-total :page-size="pageSize" show-elevator>
            </Page>
        </i-col>
    </Row>
    <!--  默认不打开，当用户点击'更多信息'时，显示在表格下方 -->
    <transition name="fade">
    <template id="c1" v-if="showMoreInfo">
        <div id="c1-app" style="font-size: larger">
            <Tabs value="name1">
                <Tab-pane label="班级信息" name="name1">
                    <!--  课程信息 -->
                    <ul>
                        <li class="list-item">课程名称： {{clazzInfo.course.courseName}}</li>
                        <li class="list-item">班级名称： {{clazzInfo.className}}</li>
                        <li class="list-item">班级人数：
                            <Tag color="green">{{classmatesNum}}</Tag>
                            <i-button type="primary" @click="getClassmatesByClassId(clazzInfo.classId)">查看同学</i-button>
                        </li>
                        <li class="list-item">创建时间： {{clazzInfo.createTime}}</li>
                        <li class="list-item">  成绩比例： {{clazzInfo.scorePercent | formatScorePercent}}</li>
                    </ul>
                </Tab-pane>
                <!--  教师信息 -->
                <Tab-pane label="老师信息" name="name2">
                    <ul>
                        <li class="list-item">账号：{{teacher.userId}}</li>
                        <li class="list-item">姓名：{{teacher.name}}</li>
                        <li class="list-item"> 性别：{{teacher.userInfo.sex}}</li>
                        <li class="list-item">学校：{{teacher.userInfo.major.name}}</li>
                        <li class="list-item">邮箱：{{teacher.userInfo.email}}</li>
                        <li class="list-item">手机：{{teacher.userInfo.telephone}}</li>
                        <li class="list-item">地址：{{teacher.userInfo.address}}</li>
                    </ul>
                </Tab-pane>
                <!-- 成绩信息 -->
                <Tab-pane label="成绩信息" name="name3">
                    <ul>
                        <li class="list-item">平时成绩：{{classmate.regularScore | formatScore}}</li>
                        <li class="list-item">考试成绩：{{classmate.paperScore | formatScore}}</li>
                        <li class="list-item">总成绩：{{classmate.ultimateScore | formatScore}}</li>
                    </ul>
                </Tab-pane>
            </Tabs>
        </div>
    </template>
    </transition>
    <!--  查看班级同学，当点击'查看同学'按钮时，弹出 modal 显示信息，默认不显示 -->
    <template id="classmate" v-if="showClassmates">
        <Modal v-model="showModal" :title="classmates[0].clazz.className" scrollable closable @on-ok="ok">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>账号</th>
                    <th>姓名</th>
                    <th>邮箱</th>
                    <th>状态</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="c in classmates">
                    <td>{{c.account.userId}}</td>
                    <td>{{c.account.name}}</td>
                    <td>{{c.account.userInfo.email}}</td>
                    <td v-if="c.status === 1">
                        <Tag color="yellow">等待审核</Tag>
                    </td>
                    <td v-else-if="c.status === 2">
                        <Tag color="green">审核通过</Tag>
                    </td>
                    <td v-else-if="c.status === 3">
                        <Tag color="red">拒绝审核</Tag>
                    </td>
                </tr>
                </tbody>
            </table>
        </Modal>
    </template>
</div>
<%@include file="../js.jsp"%>


<script>

    const C1 = Vue.extend({
        template: '#c1'
    });
    const C2 = Vue.extend({
        template: '#c2'
    });
    const MoreInfo = Vue.extend({
        template: '#more-info'
    });
    //    const router = new VueRouter({
    //        routes: [
    //            // 动态路径参数 以冒号开头
    //            {path: '/moreInfo', component: MoreInfo}
    //        ]
    //    });
    new Vue({
        el: '#app',
        data: {
            //考虑到一个学生参加的课程不会很多，故一次全部取出，存在前端，需要时按需取之即可。
            userId:<sec:authentication property="principal.username" />,
            clazzes: [],//该学生报名的班级(实际上获取的是 classmate 对象，Hibernate 自动将关联对象 class 对象取出，整个页面的数据基本都来自这个数组)
            classmate: {},
            clazzLen: 0,
            currentClazzes: [], //当前页展示的班级
            pageSize: 4,
            clazzInfo: {},       //具体的班级信息
            showMoreInfo: false,
            classmatesNum: 0,
            classmates: [],      //班级同学
            showClassmates: false,
            showModal: false,
            teacher: {}
        },
        components: {
            'more-info': MoreInfo,
            'c1': C1,
            'c2': C2
        },
        methods: {
            //获取用户参加的课程
            ok: function () {
                this.showModal = false;
            },
            getClazzByUserId(userId) {
                $.ajax({
                    type: 'get',
                    url: '${pageContext.request.contextPath}/student/clazz/' + this.userId,
                    dataType: 'json',
                    success: function (data) {
                        this.clazzes = data;
                        this.clazzLen = this.clazzes.length;
                        this.currentClazzes = this.clazzes.slice(0, this.pageSize);
                    }.bind(this),
                    error: function (err) {
                        console.log(err);
                    }
                });
            },
            fetchData: function (page) {
                let index = (page - 1) * this.pageSize;
                this.currentClazzes = this.clazzes.slice(index, index + this.pageSize);
            },
            //发送请求，获取课程信息，跳转至课程信息页面
            courseInfo: function (courseId) {
                window.location.href = "course/" + courseId;
            },
            getClassmatesCount: function (classId) {
                $.ajax({
                    type: 'get',
                    url: '${pageContext.request.contextPath}/student/classmatesCount/' + classId,
                    dataType: 'json',
                    success: function (data) {
                        this.classmatesNum = data;
                    }.bind(this),
                    error: function (err) {
                        console.log(err);
                    }
                });
            },
            //根据当期页面中的信息，依据索引找到班级的详细信息
            moreInfo: function (index) {
                this.clazzInfo = this.currentClazzes[index].clazz;
                this.getClassmatesCount(this.clazzInfo.classId);
                this.teacher = this.currentClazzes[index].course.teacher;
                this.classmate = this.currentClazzes[index];
                this.showMoreInfo = true;
            },
            //根据 classId 得出班级学生
            getClassmatesByClassId: function (classId) {
                if (this.classmates.length !== 0) {
                    this.showClassmates = true;
                    this.showModal = true;
                    return;
                }
                $.ajax({
                    type: 'get',
                    url: '${pageContext.request.contextPath}/student/classmates/' + classId,
                    dataType: 'json',
                    success: function (data) {
                        this.classmates = data;
                        this.showClassmates = true;
                        this.showModal = true;
                    }.bind(this),
                    error: function (err) {
                        console.log(err);
                    }
                });
            }


        },
        mounted: function () {
            this.$nextTick(function () {
                // 代码保证 this.$el 在 document 中
                this.getClazzByUserId(this.userId);
            })
        },
        filters: {
            formatExamType: function (type) {
                switch (type) {
                    case 1:
                        return "提交论文";
                    case 2:
                        return "开卷考试";
                    case 3:
                        return "闭卷考试";
                    case 4:
                        return "开卷机试";
                    default:
                        return "闭卷机试";
                }
            },
            formatIsFinish: function (isFinish) {
                return isFinish ? '已结课' : '进行中';
            },
            formatScorePercent: function (p) {
                return '平时/考试:' + p * 10 + '%/' + (10 - p) * 10 + '%';
            },
            formatScore: function (score) {
                return score === null ? "暂无成绩" : score;
            }
        }

    })
</script>
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