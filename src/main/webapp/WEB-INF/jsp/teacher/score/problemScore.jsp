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
        <a href="javascript:">题目分析</a>
        <hr>
    </div>
    <div style="margin-top: 20px">
        <b>题目 ID</b>
        <i-input v-model="problemId" size="large" style="width: 300px;">
            <i-button slot="append" icon="search" @click="analyse1"></i-button>
        </i-input>
    </div>
    <hr>
    <Tabs type="card" @on-click="tabClick">
        <Tab-pane label="题目详情" name="detail">
            <div class="panel panel-info" v-if="problem !== null">
                <div class="panel-heading">
                    <h3 class="panel-title" v-if="problem.title != null">{{problem.title}}</h3>
                </div>
                <div class="panel-body">
                    <div v-if="problem.problemPicPath != 'none' ">
                        <p>题目:</p>
                        <img :src="problem.problemPicPath">
                    </div>
                    <div v-else>
                        <div v-if="problem.type === 1">
                            <div v-for="(c,index) in problem.description.split('\n')">
                                <i-input readonly :value="c">
                                    <span slot="prepend">{{index | formatChoose}}</span>
                                </i-input>
                            </div>
                        </div>
                    </div>
                    <p>参考答案:</p>
                    <div v-if="problem.solutionPicPath != 'none'  ">
                        <img :src="problem.solutionPicPath">
                    </div>
                    <div v-else>
                        {{problem.solution}}
                    </div>
                    <p style="color:gray;font-size: 15px">最后更新时间:{{problem.updateTime}}</p>
                    <p style="color:gray;font-size: 15px">总提交次数:
                        <Tag type="border" color="blue">{{problem.totalSolutions}}</Tag>
                    </p>
                    <p style="color:gray;font-size: 15px">接受提交次数:
                        <Tag type="border" color="blue">{{problem.acceptedSolutions}}</Tag>
                    </p>
                </div>
            </div>
        </Tab-pane>

        <Tab-pane label="分数统计" style="text-align: center" name="scoreCount">
            <div id="score" style="width: 100%;height: 500px">
            </div>
        </Tab-pane>
        <Tab-pane label="历次作业" name="exercises">
            <div id="exercises" style="width: 100%;height: 1500px">

            </div>
        </Tab-pane>
        <Tab-pane label="时间轴" name="time">
            <div id="time" style="width: 100%;height: 500px">

            </div>
        </Tab-pane>
    </Tabs>
</div>
<%@include file="../../js.jsp" %>
<script src="https://cdn.bootcss.com/echarts/3.5.4/echarts.common.js"></script>
<script>
    new Vue({
        el: '#container',
        data: {
            problemId: 0,
            score: [],
            problem: {}
        },
        methods: {
            getProblem: function () {
                $.get("problem/" + this.problemId, function (data) {
                    if (!data) {
                        alert("题目不存在");
                    }
                    this.problem = data;
                }.bind(this))
            },
            tabClick: function (name) {
                if (name === 'detail') {
                    this.getProblem();
                } else if (name === 'scoreCount') {
                    $.get("problem/score", {problemId: this.problemId}, function (data) {
                        if (!data) {
                            alert("题目不存在!");
                            return;
                        }
                        this.score = data;
                        let myChart = echarts.init(document.getElementById("score"));
                        let option = {
                            title: {
                                text: '题目分析',
                                x: 'center'
                            },
                            tooltip: {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },
                            legend: {
                                x: 'center',
                                y: 'bottom',
                                data: ['0分', '1分', '2分', '3分', '4分', '5分', '6分', '7分', '8分', '9分', '10分']
                            },
                            toolbox: {
                                show: true,
                                feature: {
                                    mark: {show: true},
                                    dataView: {show: true, readOnly: false},
                                    magicType: {
                                        show: true,
                                        type: ['pie', 'funnel']
                                    },
                                    restore: {show: true},
                                    saveAsImage: {show: true}
                                }
                            },
                            calculable: true,
                            series: [
                                {
                                    name: '半径模式',
                                    type: 'pie',
                                    radius: [20, 110],
                                    center: ['25%', '50%'],
                                    roseType: 'radius',
                                    label: {
                                        normal: {
                                            show: false
                                        },
                                        emphasis: {
                                            show: true
                                        }
                                    },
                                    lableLine: {
                                        normal: {
                                            show: false
                                        },
                                        emphasis: {
                                            show: true
                                        }
                                    },
                                    data: [
                                        {value: this.score[0], name: '0分'},
                                        {value: this.score[1], name: '1分'},
                                        {value: this.score[2], name: '2分'},
                                        {value: this.score[3], name: '3分'},
                                        {value: this.score[4], name: '4分'},
                                        {value: this.score[5], name: '5分'},
                                        {value: this.score[6], name: '6分'},
                                        {value: this.score[7], name: '7分'},
                                        {value: this.score[8], name: '8分'},
                                        {value: this.score[9], name: '9分'},
                                        {value: this.score[10], name: '10分'}
                                    ],
                                },
                                {
                                    name: '面积模式',
                                    type: 'pie',
                                    radius: [30, 110],
                                    center: ['75%', '50%'],
                                    roseType: 'area',
                                    data: [
                                        {value: this.score[0], name: '0分'},
                                        {value: this.score[1], name: '1分'},
                                        {value: this.score[2], name: '2分'},
                                        {value: this.score[3], name: '3分'},
                                        {value: this.score[4], name: '4分'},
                                        {value: this.score[5], name: '5分'},
                                        {value: this.score[6], name: '6分'},
                                        {value: this.score[7], name: '7分'},
                                        {value: this.score[8], name: '8分'},
                                        {value: this.score[9], name: '9分'},
                                        {value: this.score[10], name: '10分'}
                                    ]
                                }
                            ]
                        };
                        myChart.setOption(option);
                    }.bind(this))
                } else if (name === 'exercises') {
                    $.get("problem/averageInExercise/" + this.problemId, function (data) {
                        let average;
                        $.ajax({
                            url: 'problemAverage/' + this.problemId,
                            async: false,
                            success: function (data) {
                                average = data;
                            }
                        });
                        let myChart = echarts.init(document.getElementById("exercises"));

                        let option = {
                            tooltip: {
                                trigger: 'axis',
                                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                                }
                            },
                            legend: {
                                data: ['平均分数', '平均时间', '总平均分数', '总平均时间']
                            },
                            grid: {
                                left: '3%',
                                right: '4%',
                                bottom: '3%',
                                containLabel: true
                            },
                            xAxis: [
                                {
                                    type: 'value',
                                    name: '平均分数',
                                    min: 0,
                                    max: 10,
                                    position: 'top',
                                },

                                {
                                    type: 'value',
                                    name: '平均时间',
                                    position: 'bottom',
                                    axisLabel: {
                                        formatter: '{value} s'
                                    }
                                },

                                {
                                    type: 'value',
                                    name: '总平均时间',
                                    position: 'bottom',
                                    axisLabel: {
                                        formatter: '{value} s'
                                    },
                                    offset: 80
                                }
                            ],
                            yAxis: {
                                type: 'category',
                                data: data.map(function (p) {
                                    return p[2]
                                })
                            },
                            series: [
                                {
                                    name: '平均分数',
                                    type: 'bar',
                                    label: {
                                        normal: {
                                            show: true,
                                            position: 'insideRight'
                                        },
                                    },
                                    xAxisIndex: 0,
                                    data: data.map(function (p) {
                                        return p[0]
                                    })
                                },
                                {
                                    name: '平均时间',
                                    type: 'bar',
                                    label: {
                                        normal: {
                                            show: true,
                                            position: 'insideRight'
                                        },
                                    },
                                    xAxisIndex: 1,
                                    data: data.map(function (p) {
                                        return p[1]
                                    })
                                },
                                {
                                    name: '总平均分数',
                                    type: 'line',
                                    data: data.map(function (p) {
                                        return average[0][0]
                                    })
                                },
                                {
                                    name: '总平均时间',
                                    type: 'line',
                                    data: data.map(function (p) {
                                        return average[0][1]
                                    }),
                                    xAxisIndex: 2
                                }
                            ]
                        };
                        myChart.setOption(option);
                    }.bind(this))

                } else if (name === 'time') {
                    let myChart = echarts.init(document.getElementById("time"));
                    $.get("allProblemScore/" + this.problemId, function (res) {


                        // 指定图表的配置项和数据
                        var option = {
                            title: {
                                text: '时间轴'
                            },
                            tooltip: {},
                            legend: {
                                data: [' 分数']
                            },
                            xAxis: {
                                data: res
                            },
                            yAxis: {},
                            series: [{
                                name: '分数',
                                type: 'scatter',
                                data: res,
                                smooth: true
                            }]
                        };

                        // 使用刚指定的配置项和数据显示图表。
                        myChart.setOption(option);
                    });

                }
            },
            analyse1: function () {
                this.getProblem();
            }
        },
        mounted: function () {

        },
        filters: {
            formatChoose: function (index) {
                return String.fromCharCode(index + 65);
            }
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