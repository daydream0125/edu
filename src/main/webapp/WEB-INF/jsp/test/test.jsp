<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--<%@include file="../static.jsp" %>--%>
    <title>Title</title>
    <style>
    </style>
    <link href="static/editor/css/wangEditor.min.css" type="text/css" rel="stylesheet">
</head>
<body>
<%--<%@include file="../navigation.jsp" %>--%>
<div id="container" class="container">
    <div id="time" style="width: 100%;height: 500px">

    </div>

</div>
<%@include file="../js.jsp" %>
<script src="https://cdn.bootcss.com/echarts/3.5.4/echarts.common.js"></script>

<script type="text/javascript">
    let myChart = echarts.init(document.getElementById("time"));
    $.get('data/asset/data/aqi-beijing.json', function (data) {
        myChart.setOption(option = {
            title: {
                text: 'Beijing AQI'
            },
            tooltip: {
                trigger: 'axis'
            },
            xAxis: {
                data: data.map(function (item) {
                    return item[0];
                })
            },
            yAxis: {
                splitLine: {
                    show: false
                }
            },
            toolbox: {
                left: 'center',
                feature: {
                    dataZoom: {
                        yAxisIndex: 'none'
                    },
                    restore: {},
                    saveAsImage: {}
                }
            },
            dataZoom: [{
                startValue: '2014-06-01'
            }, {
                type: 'inside'
            }],
            visualMap: {
                top: 10,
                right: 10,
                pieces: [{
                    gt: 0,
                    lte: 50,
                    color: '#096'
                }, {
                    gt: 50,
                    lte: 100,
                    color: '#ffde33'
                }, {
                    gt: 100,
                    lte: 150,
                    color: '#ff9933'
                }, {
                    gt: 150,
                    lte: 200,
                    color: '#cc0033'
                }, {
                    gt: 200,
                    lte: 300,
                    color: '#660099'
                }, {
                    gt: 300,
                    color: '#7e0023'
                }],
                outOfRange: {
                    color: '#999'
                }
            },
            series: {
                name: 'Beijing AQI',
                type: 'line',
                data: data.map(function (item) {
                    return item[1];
                }),
                markLine: {
                    silent: true,
                    data: [{
                        yAxis: 50
                    }, {
                        yAxis: 100
                    }, {
                        yAxis: 150
                    }, {
                        yAxis: 200
                    }, {
                        yAxis: 300
                    }]
                }
            }
        });
    });
    myChart.setOption(option);

</script>
</body>
</html>
