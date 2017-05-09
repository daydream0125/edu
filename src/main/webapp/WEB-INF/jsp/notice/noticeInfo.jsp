<%@ page pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <%@include file="../static.jsp" %>
    <title>通知公告</title>
    <style>
        .notice-meta {
            padding: 15px;
            text-align: center;
            color: #8c8c8c;
            font-size: 15px;
        }
    </style>
</head>
<body>
<%@include file="../navigation.jsp" %>
<div id="container" class="container">
    <div  style="color: #004772;font-weight: bold">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="notice">通知公告</a>
        <hr>
    </div>
    <div>
        <h1 style="text-align: center;margin-top: 20px">{{notice[0][0]}}</h1>
        <p class="notice-meta">
            <span>日期：{{notice[0][1]}}</span>
            <span>作者：{{notice[0][3]}}</span>
        </p>

        <div v-html="notice[0][2]">

        </div>
    </div>
</div>

<%@include file="../js.jsp" %>
<script>
    new Vue({
        el: '#container',
        data: {
            noticeId:${noticeId},
            notice: {}
        },
        methods: {

            getNoticeInfo: function () {
                $.get("notice/" + this.noticeId, function (data) {
                    this.notice = data;
                }.bind(this))
            }
        },
        mounted: function () {
            this.getNoticeInfo();
        }

    });
    new Vue({
        el: '#notice',
        data: {
            isActive: true
        }
    })
</script>
</body>
</html>