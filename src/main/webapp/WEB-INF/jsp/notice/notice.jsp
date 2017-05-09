<%@ page pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <%@include file="../static.jsp" %>
    <title>通知公告</title>
    <style>
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
<div id="container" class="container">
<div  style="color: #004772;font-weight: bold">
    <span>当前位置：</span>
    <a href="">首页</a>
    <span class=>&nbsp;| &nbsp;</span>
    <a href="javascript:">通知公告</a>
    <hr>
</div>
    <div>
        <div style="float: left;width: 300px;height: 500px;margin-top: 30px">
            <h2 style="color: #1b6d85;font-weight: bold;text-align: center">通知公告</h2>
            <hr>
            <sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_TEACHER)')">
                <i-button size="large" type="success" long @click="addNotice">新增通知</i-button>
                <br><br>
                <i-button size="large" type="error" long @click="deleteModal = true">删除通知</i-button>
            </sec:authorize>
        </div>
        <div style="margin-top: 30px">
        <ul>
            <li class="list-item" v-for="n in notices">
                <a :href="formatUrl(n[0])" >{{n[1]}}</a>
                <div style="float: right;">
                    {{n[2]}}
                </div>
            </li>
        </ul>
        </div>

    </div>
    <Modal v-model="deleteModal" title="删除通知">
        <i-select v-model="delIndex">
            <i-option v-for="(n,index) in notices" :value="index">
                {{n[1]}}{{n[2]}}
            </i-option>
        </i-select>
        <br>
        <hr>
        <br>
        <i-button type="error" @click="deleteNotice" long >删除</i-button>
    </Modal>
</div>

<%@include file="../js.jsp" %>
<script>
    new Vue({
        el: '#container',
        data: {
            notices: [],
            delIndex:0,
            deleteModal:false
        },
        methods: {
            addNotice:function () {
              window.location.href = "addNotice";
            },
            deleteNotice:function () {
                $.post("notice/delete",{id:this.notices[this.delIndex][0]},function (data) {
                    if (data === true) {
                        alert("删除成功");
                        this.notices.splice(this.delIndex,1);
                    } else {
                        alert("删除失败,请重试")
                    }
                }.bind(this))
            },
            formatUrl:function (id) {
                return "noticeInfo/" + id;
            },
            getNotices: function () {
                $.get("notices", function (data) {
                    this.notices = data;
                }.bind(this))
            }
        },
        mounted: function () {
            this.getNotices();
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