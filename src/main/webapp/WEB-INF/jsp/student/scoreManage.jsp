<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="../static.jsp" %>
<html>
<head>
    <title>
        成绩管理
    </title>
</head>
<body>
<%@include file="../navigation.jsp" %>
<div class="container" id="container">
    <div  style="color: #004772;font-weight: bold;margin-bottom: 20px">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">我的成绩</a>
        <hr>
    </div>
    选择练习
    <i-select size="large" v-model="exerciseIndex" style="width: 300px">
        <i-option v-for="(e,index) in exercises" :value="index">{{e.exerciseName}}</i-option>
    </i-select>
    <i-button type="primary" size="large" @click="checkScore">查询</i-button>

    <div style="float: right">
        <i-button type="primary" size="large" @click="detail">查看详细得分</i-button>
        <i-button type="primary" size="large" @click="viewAnswers">查看批改情况</i-button>
    </div>
    <hr>
    <div id="score" style="width: 100%;height: 500px;margin-top: 30px">
    </div>
</div>
<%@include file="../js.jsp" %>
<script src="https://cdn.bootcss.com/echarts/3.5.4/echarts.common.js"></script>
<script>
    new Vue({
        el: '#container',
        data: {
            userId:${sessionScope.account.userId},
            exercises: [],
            exerciseIndex: 0,
            exerciseScores: [],
            score:[]
        },
        methods: {
            viewAnswers:function () {
                window.location.href = "student/exercise/" + this.exercises[this.exerciseIndex].exerciseId + "/answers";
            },
            detail: function (index) {
                let classmateId;
                $.ajax({
                    url:"userToClassmate",
                    data:{
                        exerciseId:this.exercises[this.exerciseIndex].exerciseId,
                        userId:this.userId
                    },
                    async:false,
                    success:function (data) {
                        classmateId = data;
                    }
                });
                $.get("classmate/score", {
                    exerciseId: this.exercises[this.exerciseIndex].exerciseId,
                    classmateId: classmateId
                }, function (data) {
                    this.score = data;
                    // 基于准备好的dom，初始化echarts实例
                    let myChart = echarts.init(document.getElementById("score"));
                    let average;
                    $.ajax({
                        url:'classmate/average',
                        data:{
                            exerciseId:this.exercises[this.exerciseIndex].exerciseId,
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
                            text: this.userId + "在" + this.exercises[this.exerciseIndex].exerciseName + "的成绩"
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
            checkScore: function () {
                let yourScore;
                $.ajax({
                    url: "student/score/exercise",
                    data: {
                        exerciseId: this.exercises[this.exerciseIndex].exerciseId,
                        userId: this.userId
                    },
                    async:false,
                    success: function (data) {
                        if (!data) {
                            alert("暂无你的成绩，请确认是否提交作业");
                        }
                        yourScore = data;
                    }.bind(this)
                });
                $.get("exercise/" + this.exercises[this.exerciseIndex].exerciseId + "/isJudge", function (data) {
                    if (data === true) {
                        $.get("exerciseScores/" + this.exercises[this.exerciseIndex].exerciseId, function (data) {
                            this.exerciseScores = data;

                            // 基于准备好的dom，初始化echarts实例
                            let myChart = echarts.init(document.getElementById("score"));

                            // 指定图表的配置项和数据
                            let data1 = this.exerciseScores.map(function (p1) {
                                return p1[0]
                            });
                            let data2 = this.exerciseScores.map(function (p1) {
                                return p1[1]
                            });
                            let option = {
                                title: {
                                    text: this.exercises[this.exerciseIndex].exerciseName + "成绩"
                                },
                                tooltip: {},
                                legend: {
                                    data: ['分数', '你的分数']
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
                                },
                                    {
                                        name: '你的分数',
                                        type: 'line',
                                        data: data2.map(function () {
                                            return yourScore;
                                        })
                                    }]
                            };

                            // 使用刚指定的配置项和数据显示图表。
                            myChart.setOption(option);
                        }.bind(this))
                    } else {
                        alert("该练习还未批改，请等待老师批改后查询");
                    }
                }.bind(this))
            },
            getExercises: function () {
                $.ajax({
                    type: 'get',
                    url: 'student/exercisesByUserId/' + this.userId,
                    dataType: 'json',
                    success: function (data) {
                        this.exercises = data;
                    }.bind(this),
                    error: function (err) {
                        console.log(err);
                    }
                });
            }
        },


        mounted: function () {
            this.getExercises();
        }
    });
    new Vue({
        el: '#student-score',
        data: {
            isActive: true
        }
    })
</script>
</body>
</html>