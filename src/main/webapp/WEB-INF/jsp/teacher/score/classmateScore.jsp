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
        <a href="javascript:">学生成绩</a>
        <hr>
    </div>
    选择练习
    <i-select @on-change="showClassmates" size="large" v-model="activeExerciseIndex" style="width: 300px">
        <i-option v-for="(e,index) in exercises" :value="index">{{e.exerciseName}}</i-option>
    </i-select>
    选择学生
    <i-select @on-change="showScore" size="large" v-model="activeClassmateIndex" style="width: 300px">
        <i-option v-for="(c,index) in classmates" :value="index">{{c[1]}}</i-option>
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
            exercises: [],
            classmates: [],
            activeExerciseIndex: 0,
            activeClassmateIndex: 0,
            score: []
        },
        methods: {
            showClassmates: function (index) {
                $.get("classmates/" + this.exercises[index].exerciseId, function (data) {
                    this.classmates = data;
                }.bind(this))
            },
            showScore: function (index) {
                $.get("classmate/score", {
                    exerciseId: this.exercises[this.activeExerciseIndex].exerciseId,
                    classmateId: this.classmates[index][0]
                }, function (data) {
                    this.score = data;
                    // 基于准备好的dom，初始化echarts实例
                    let myChart = echarts.init(document.getElementById("score"));
                    let average;
                    $.ajax({
                        url:'classmate/average',
                        data:{
                            exerciseId:this.exercises[this.activeExerciseIndex].exerciseId,
                            problemIds:this.score.map(function (p) {
                                //返回题目编号
                                return p[2]
                            })
                        },
                        async:false,
                        success:function (data) {
                            average = data;
                        }
                    });

                    // 指定图表的配置项和数据
                    let option = {
                        title: {
                            text: this.classmates[this.activeClassmateIndex][1] + "在" + this.exercises[this.activeExerciseIndex].exerciseName + "的成绩"
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
                            data: ['分数', '时间','平均分数','平均时间']
                        },
                        xAxis: {
                            name:'题目编号',
                            data: this.score.map(function (p) {
                                //返回题目编号
                                return p[2]
                            })
                        },
                        yAxis: [
                            {
                                type: 'value',
                                name: '分数',
                                min: 0,
                                max: 10,
                                position: 'left',
                            },

                            {
                                type: 'value',
                                name: '时间',
                                position: 'right',
                                axisLabel: {
                                    formatter: '{value} s'
                                }
                            },
                            {
                                type: 'value',
                                name: '平均分数',
                                min: 0,
                                max: 10,
                                offset: 80,
                                position: 'left',
                            },
                            {
                                type: 'value',
                                name: '平均时间',
                                offset: 80,
                                position: 'right',
                            }
                        ],
                        series: [
                            {
                                name: '分数',
                                type: 'bar',
                                data:  this.score.map(function (p) {
                                    return p[0]
                                }),
                                yAxisIndex: 0
                            },
                            {
                                name: '时间',
                                type: 'bar',
                                data: this.score.map(function (p) {
                                    return p[1]
                                }),
                                yAxisIndex: 1
                            },
                            {
                                name: '平均分数',
                                type: 'line',
                                data: average.map(function (p) {
                                    return p[0][0]
                                }),
                                yAxisIndex: 2
                            },
                            {
                                name: '平均时间',
                                type: 'line',
                                data: average.map(function (p) {
                                    return p[0][1]
                                }),
                                yAxisIndex: 3
                            }
                        ]
                    };

                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }.bind(this))
            },
            getExercises: function (teacherId) {
                $.get("teacher/" + teacherId + "/exercises", function (data) {
                    this.exercises = data;
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