<%@ page pageEncoding="UTF-8" language="java" %>
<%@include file="../static.jsp" %>
<html>
<head>
    <title>管理员</title>
    <style>
        table {
            font-size: 18px;
        }
    </style>
</head>
<body>
<%@include file="../navigation.jsp" %>
<div class="container" id="container">
    <div style="color: #004772;font-weight: bold;margin-top: 20px">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">管理员</a>
        <hr>
    </div>
    <table class="table table-hover">
        <thead>
        <tr>
            <th>操作人</th>
            <th>时间</th>
            <th>操作</th>
            <th>ip</th>
            <th>方法</th>
        </tr>
        </thead>
        <tbody>
            <tr v-for="l in logs">
                <td>{{l.userId}}</td>
                <td>{{l.operTime}}</td>
                <td>{{l.operation}}</td>
                <td>{{l.ip}}</td>
                <td>{{l.method}}</td>
            </tr>
        </tbody>
    </table>
    <div style="float: right">
        <Page :total="logsCount" @on-change="fetchData" show-total :page-size="pageSize" show-elevator>
        </Page>
    </div>
</div> <!-- /container -->
<%@include file="../js.jsp" %>
<script>
    new Vue({
        el: '#container',
        data: {
            userId:<sec:authentication property="principal.username" />,
            logs: [],
            logsCount: 0,
            pageNow: 1,
            pageSize:10
        },
        methods: {
            getLog: function () {
                $.get("admin/logsCount", function (data) {
                    this.logsCount = data;
                    $.get("admin/logs",{pageNow:this.pageNow,count:this.logsCount},function (data) {
                        this.logs = data;
                    }.bind(this))
                }.bind(this));
            },
            fetchData:function (page) {
                $.get("admin/logs",{pageNow:page,count:this.logsCount},function (data) {
                    this.logs = data;
                }.bind(this))
            }
        },
        mounted: function () {
            this.getLog();
        }
    });
    new Vue({
       el:'#admin',
        data:{
           isActive:true
        }
    })
</script>
</body>
</html>
