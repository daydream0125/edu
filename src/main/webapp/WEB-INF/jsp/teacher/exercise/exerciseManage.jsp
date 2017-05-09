<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>作业管理</title>
    <%@include file="../../static.jsp" %>
    <style>
    </style>
</head>
<body>
<%@include file="../../navigation.jsp" %>
<div id="app" class="container">
    <div style="color: #004772;font-weight: bold">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">作业管理</a>
        <hr>
    </div>
    <div style="float: right;margin-right: 100px;height: 40px">
        <i-button type="primary" size="large" @click="goToAddExercise">
            新增作业
        </i-button>
    </div>

    <div>

    <table class="table">
        <thead>
        <tr>
            <th>课程</th>
            <th>班级</th>
            <th>名称</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>性质</th>
            <th>状态</th>
            <th>发布</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="(e,index) in currentExercises">
            <td>{{e.course.courseName}}</td>
            <td>{{e.clazz.className}}</td>
            <td>{{e.exerciseName}}</td>
            <td>{{e.startTime}}</td>
            <td>{{e.endTime}}</td>
            <td v-if="e.isFinal">
                <Tag type="border" color="red">最终考试</Tag>
            </td>
            <td v-else>
                <Tag type="border" color="green">平时练习</Tag>
            </td>
            <td v-if="getIsFinish(e.endTime)">
                <Tag color="green">进行中</Tag>
            </td>
            <td v-else>
                <Tag color="yellow">已结束</Tag>
            </td>
            <td v-if="e.isRelease">
                <Tag type="border" color="yellow">已发布</Tag>
            </td>
            <td v-else>
                <i-button type="primary" @click="releaseExercise(index)">点击发布</i-button>
            </td>
            <td>
                <i-button type="primary" @click="moreInfo(index)">更多信息</i-button>
            </td>
        </tr>
        </tbody>
    </table>
    </div>
    <br>
    <div style="float: right">
        <Page :total="exercisesLen" @on-change="fetchData" show-total :page-size="pageSize" show-elevator>
        </Page>
    </div>
    <br>
    <div id="moreInfo" v-if="showMoreInfo">
        <i-menu mode="horizontal" theme="light" active-name="1" @on-select="changeMenu">
            <Row type="flex" justify="center">
                <i-col span="5">
                    <Menu-item name="1">
                        <p class="p">修改信息</p>
                    </Menu-item>
                </i-col>
                <i-col span="5">
                    <Menu-item name="2">
                        <p class="p">添加题目</p>
                    </Menu-item>
                </i-col>
                <i-col span="5">
                    <Menu-item name="3">
                        <p class="p">批改作业</p>
                    </Menu-item>
                </i-col>
            </Row>
        </i-menu>
        <div v-if="menu == 1">
            暂不提供
        </div>
        <div v-if="menu == 2">
            <br>
            <i-button type="primary" size="large" @click="addProblemToExercise">添加题目</i-button>
        </div>
        <div v-if="menu == 3" style="text-align: center">
            <br>
            <p> 班级人数：
                <Tag type="border" color="blue">{{classmateCount}}</Tag>
            </p>
            <p> 提交人数：
                <Tag type="border" color="blue">{{submitCount}}</Tag>
            </p>
            <p> 批改人数：
                <Tag type="border" color="blue">{{judgeCount}}</Tag>
            </p>
            <i-button type="primary" size="large" @click="mark">批改作业</i-button>
        </div>
    </div>

</div>
<%@include file="../../js.jsp" %>
<script>

    new Vue({
        el: '#app',
        data: {
            teacherId:${sessionScope.account.userId},
            currentExercises: [], //当前页展示的练习
            activeExercise: {},
            pageSize: 4,
            showStartExercise: false,
            startExerciseId: 0,
            problemCount: 0,
            exercises: [],
            exercisesLen: 0,
            showMoreInfo: false,
            menu: 1,
            classmateCount: 0,
            submitCount: 0,
            judgeCount: 0
        },
        methods: {
            mark: function () {
              if (this.submitCount === this.judgeCount) {
                  alert("当前练习没有可批改的作业");
                  return;
              }
              window.location.href = "teacher/exercise/markExercise/" + this.activeExercise.exerciseId;
            },
            getClassmatesCount: function () {
                $.get("teacher/classmateCount/" + this.activeExercise.clazz.classId, function (data) {
                    this.classmateCount = data;
                }.bind(this))
            },
            getSubmitCount: function () {
                $.get("teacher/exercise/submitCount/" + this.activeExercise.exerciseId, function (data) {
                    this.submitCount = data;
                }.bind(this))
            },
            getJudgeCount: function () {
                $.get("teacher/exercise/judgeCount/" + this.activeExercise.exerciseId, function (data) {
                    this.judgeCount = data;
                }.bind(this))
            },
            goToAddExercise: function () {
                window.location.href = "teacher/addExercise";
            },
            addProblemToExercise: function () {
                window.location.href = "teacher/addProblemToExercise/" + this.activeExercise.exerciseId;
            },
            changeMenu: function (name) {
                this.menu = name;
            },
            moreInfo: function (index) {
                this.activeExercise = this.currentExercises[index];
                this.getProblemCount(index);
                this.showMoreInfo = true;

                this.getClassmatesCount();
                this.getSubmitCount();
                this.getJudgeCount();
            },
            getExercisesByTeacherId:function(teacherId) {
                $.ajax({
                    type: 'get',
                    url: 'teacher/' + this.teacherId + '/exercises',
                    dataType: 'json',
                    success: function (data) {
                        this.exercises = data;
                        this.exercisesLen = this.exercises.length;
                        this.currentExercises = this.exercises.slice(0, this.pageSize);
                    }.bind(this),
                    error: function (err) {
                        console.log(err);
                    }
                });
            },
            //查询该练习下的题目数
            getProblemCount: function (index) {
                $.ajax({
                    type: 'get',
                    url: 'exercise/' + this.currentExercises[index].exerciseId + '/problemCount',
                    dataType: 'json',
                    success: function (data) {
                        this.problemCount = data;
                    }.bind(this),
                    error: function (err) {
                        console.log(err);
                    }
                });
            },
            //获取该页的练习
            fetchData: function (page) {
                let index = (page - 1) * this.pageSize;
                this.currentExercises = this.exercises.slice(index, index + this.pageSize);
            },
            releaseExercise: function (index) {
                $.post("teacher/releaseExercise", {'exerciseId': this.currentExercises[index].exerciseId}, function (data) {
                    if (data === true) {
                        this.currentExercises[index].isRelease = true;
                        alert("发布成功");
                    } else {
                        alert("发布失败");
                    }
                }.bind(this))
            },
            getIsFinish: function (endTime) {
                let now = new Date();
                endTime = endTime.replace('-', '/');
                let d = new Date(Date.parse(endTime));
                return d > now;
            }
        },
        //页面加载完成后获取 userId 下的 全部exercise
        created: function () {
            this.getExercisesByTeacherId(this.teacherId);
        },
        filters: {
            formatIsFinal: function (isFinal) {
                return isFinal ? '<Tag type="border" color="red">最终考试</Tag>' : '<Tag type="border" color="green">平时练习</Tag>';
            }

        }
    })
</script>
<script>
    new Vue({
        el: "#teacher-exercise",
        data: {
            isActive: true
        }
    })

</script>
</body>
</html>