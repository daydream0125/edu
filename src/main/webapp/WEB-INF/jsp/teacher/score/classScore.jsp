<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="../../static.jsp" %>
<html>
<head>
    <title>
        成绩管理
    </title>
</head>
<body>
<%@include file="../../navigation.jsp" %>
<div class="container" id="container">
    <div style="color: #004772;font-weight: bold">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="teacher/score/scoreManage">成绩管理</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">班级成绩</a>
        <hr>
    </div>
    选择练习
    <i-select @on-change="showClasses" size="large" style="width: 300px">
        <i-option v-for="c in courses" :value="c[0]">{{c[1]}}</i-option>
    </i-select>
    选择学生
    <i-select @on-change="showScore" size="large" style="width: 300px">
        <i-option v-for="(c,index) in classes" :value="index">{{c[1]}}</i-option>
    </i-select>
    <br>
    <hr>
    <h3 style="text-align: center">成绩统计</h3>
    <br>
    <div id="score" style="width: 100%;height: 500px">

    </div>
</div>
<%@include file="../../js.jsp" %>
<script src="https://cdn.bootcss.com/echarts/3.5.4/echarts.common.js"></script>
<script>
    new Vue({
        el: '#container',
        data: {
            teacherId:<sec:authentication property="principal.username" />,
            courses: [],
            classes: [],
            score: [],
            index: 0
        },
        methods: {
            showClasses: function (courseId) {
                $.get("teacher/classes/sharp/" + courseId, function (data) {
                    this.classes = data;
                }.bind(this))
            },
            showScore: function (index) {
                $.get("class/score/" + this.classes[index][0], function (data) {
                    this.score = data;
                    // 基于准备好的dom，初始化echarts实例
                    let myChart = echarts.init(document.getElementById("score"));

                    // 指定图表的配置项和数据
                    let option = {
                        title: {
                            text: this.classes[index][1] + "的成绩"
                        },
                        tooltip: {
                            trigger: 'axis',
                            axisPointer: {
                                type: 'cross'
                            }
                        },
                        grid: {
                            right: '20%'
                        },
                        toolbox: {
                            feature: {
                                dataView: {show: true, readOnly: false},
                                restore: {show: true},
                                saveAsImage: {show: true}
                            }
                        },
                        legend: {
                            data: ['平时成绩', '考试成绩', '最终成绩']
                        },
                        xAxis: {
                            name: '学生',
                            data: this.score.map(function (p) {
                                return p[0]
                            })
                        },
                        yAxis: {},
                        series: [
                            {
                                name: '平时成绩',
                                type: 'bar',
                                data: this.score.map(function (p) {
                                    return p[1]
                                })
                            },
                            {
                                name: '考试成绩',
                                type: 'bar',
                                data: this.score.map(function (p) {
                                    return p[2]
                                })
                            },
                            {
                                name: '最终成绩',
                                type: 'bar',
                                data: this.score.map(function (p) {
                                    return p[3]
                                })
                            }
                        ]
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }.bind(this))
            },
            getCourses: function (teacherId) {
                $.get("teacher/courses/sharp/" + teacherId, function (data) {
                    this.courses = data;
                }.bind(this))
            }
        },
        mounted: function () {
            this.getCourses(this.teacherId);
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