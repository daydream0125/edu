<%@ page pageEncoding="UTF-8" language="java" %>

<html>
<head>
    <title>
        成绩管理
    </title>
    <%@ include file="../../static.jsp" %>
</head>
<body>
<%@include file="../../navigation.jsp" %>
<div class="container" id="container">
    <div style="color: #004772;font-weight: bold">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">成绩管理</a>
        <hr>
    </div>
    <div style="float: right;margin-right: 100px">
        <i-button type="primary" size="large" @click="problemScore">题目分析</i-button>
        <i-button type="primary" size="large" @click="viewClassmateScore(index)">学生成绩</i-button>
        <i-button type="primary" size="large" @click="viewClassScore">班级成绩</i-button>
    </div>
    <div id="exerciseScore" style="width: 100%;height: 500px;margin-top: 40px" v-if="showExerciseScores">
    </div>
    <div style="margin-top: 50px">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>课程</th>
                <th>练习名称</th>
                <th>班级</th>
                <th>查看成绩</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(e,index) in currentExercises">
                <td>{{e.course.courseName}}</td>
                <td>{{e.exerciseName}}</td>
                <td>{{e.clazz.className}}</td>
                <td>
                    <i-button type="primary" size="large" @click="viewExerciseScore(index)">查看成绩</i-button>
                </td>
            </tr>
            </tbody>
        </table>
        <Row>
            <i-col offset="10">
                <Page :total="exercisesLen" @on-change="fetchData" show-total :page-size="pageSize" show-elevator>
                </Page>
            </i-col>
        </Row>
    </div>
</div>
<%@include file="../../js.jsp" %>
<script src="https://cdn.bootcss.com/echarts/3.5.4/echarts.common.js"></script>
<script>
    new Vue({
        el: '#container',
        data: {
            teacherId:<sec:authentication property="principal.username" />,
            exercises: [],
            exerciseIndex: 0,
            currentExercises: [],
            pageSize: 4,
            exercisesLen: 0,
            exerciseScores: [],
            showExerciseScores: false
        },
        methods: {
            viewClassScore: function () {
                window.location.href = "teacher/viewClassScore";
            },
            problemScore: function () {
                window.location.href = "problemScore";
            },
            viewClassmateScore: function (index) {
                window.location.href = "classmateScore"
            },
            //获取该页的练习
            fetchData: function (page) {
                let index = (page - 1) * this.pageSize;
                this.currentExercises = this.exercises.slice(index, index + this.pageSize);
            },
            getExercises: function (teacherId) {
                $.get("teacher/" + teacherId + "/exercises", function (data) {
                    this.exercises = data;
                    this.currentExercises = this.exercises.slice(0, this.pageSize);
                    this.exercisesLen = this.exercises.length;
                }.bind(this))
            },
            viewExerciseScore: function (index) {
                $.get("exercise/" + this.exercises[index].exerciseId + "/isJudge", function (data) {
                    if (data === true) {
                        this.showExerciseScores = true;
                        $.get("exerciseScores/" + this.exercises[index].exerciseId, function (data) {
                            this.exerciseScores = data;

                            // 基于准备好的dom，初始化echarts实例
                            let myChart = echarts.init(document.getElementById("exerciseScore"));

                            // 指定图表的配置项和数据
                            let data1 = this.exerciseScores.map(function (p1, p2, p3) {
                                return p1[0]
                            });
                            let data2 = this.exerciseScores.map(function (p1, p2, p3) {
                                return p1[1]
                            });
                            let option = {
                                title: {
                                    text: this.exercises[index].exerciseName + "成绩"
                                },
                                tooltip: {},
                                legend: {
                                    data: ['分数']
                                },
                                xAxis: {
                                    data: data1
                                },
                                yAxis: [
                                    {
                                        type: 'value',
                                        name: '分数',
                                        min: 0,
                                        max: 100,
                                        position: 'left'
                                    }
                                ],
                                series: [{
                                    name: '分数',
                                    type: 'bar',
                                    data: data2
                                }]
                            };

                            // 使用刚指定的配置项和数据显示图表。
                            myChart.setOption(option);
                        }.bind(this))
                    } else {
                        alert("该练习还未批改，请先至作业管理完成练习批改");
                    }
                }.bind(this))
            }
        },
        mounted: function () {
            this.getExercises(this.teacherId);
        }
    });
    new Vue({
        el: '#teacher-score',
        data: {
            isActive: true
        }
    })
</script>
</body>
</html>