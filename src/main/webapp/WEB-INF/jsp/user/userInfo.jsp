<%@ page pageEncoding="UTF-8" language="java" %>
<%@ page session="true" %>
<%@include file="../static.jsp" %>
<html>
<head>
    <title>个人信息</title>
    <style>

    </style>
</head>
<body>
<%@include file="../navigation.jsp" %>
<div class="container" id="container">
    <div  style="color: #004772;font-weight: bold">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">个人信息</a>
        <hr>
    </div>

    <div style="height: 350px">

        <Row>
            <div style="float: right;margin-right: 100px">
                <i-button type="primary" size="large" @click="updateInfo">更新个人信息</i-button>
            </div>
            <i-col span="2">
                <img :src="user.userInfo.photo" style="width: 150px;height: 180px">
            </i-col>
            <i-col offset="4">
                <p style="font-size: 30px;margin-top: 20px">{{user.name}}</p>
                <p style="font-size: 20px;margin-top: 10px">{{user.userInfo.nickName}}</p>
                <p style="color: #2e6da4">{{user.roles | formatRoles}}</p>
                <p v-if="user.userInfo.major !== null" style="margin-top: 10px">
                    {{user.userInfo.major.parent.parent.name}}&nbsp;{{user.userInfo.major.parent.name}}&nbsp;{{user.userInfo.major.name}}</p>
            </i-col>
        </Row>

        <hr>
        <Row>
            <Row>
                <i-col span="1">
                    <b>学号</b>
                </i-col>
                <i-col span="5">
                    {{user.cardNumber}}
                </i-col>
            </Row>
            <i-col span="1">
                <b>邮箱</b>
            </i-col>
            </td>
            <i-col span="5">
                {{user.userInfo.email}}
            </i-col>

        </Row>
        <Row>
            <i-col span="1"><b>座机</b></i-col>
            <i-col span="5">{{user.userInfo.phone}}</i-col>
            <i-col span="1" offset="4"><b>手机</b></i-col>
            <i-col span="5">{{user.userInfo.phone}}</i-col>
        </Row>
        <Row>
            <i-col span="1"><b>地址</b></i-col>
            <i-col span="10">{{user.userInfo.address}}</i-col>
        </Row>
        <Row>
            <i-col span="2"><b>创建时间</b></i-col>
            <i-col span="5">{{user.createTime}}</i-col>
            <i-col span="3" offset="3"><b>上次登录时间</b></i-col>
            <i-col span="4">{{user.lastLoginTime}}</i-col>
        </Row>
        <Row>
            <i-col span="2"><b>登录IP</b></i-col>
            <i-col>{{user.loginIp}}</i-col>
        </Row>
    </div>
    <hr>
    <Row>
        <i-col><b>个人经历</b></i-col>
    </Row>
    <Row>
        {{user.userInfo.desc}}
    </Row>
    <div>

    </div>
</div> <!-- /container -->
<%@include file="../js.jsp" %>
<script>
    new Vue({
        el: "#container",
        data: {
            userId:'${sessionScope.account.userId}',
            user: {},
        },
        methods: {
            updateInfo: function () {
                window.location.href = "updateInfo";
            },
            getUserInfo: function (userId) {
                $.get("userInfo/" + userId, function (data) {
                    this.user = data;
                }.bind(this))
            }
        },
        mounted: function () {
            this.getUserInfo(this.userId);
        },
        filters: {
            formatRoles: function (roles) {
                let roleName = "普通用户";
                for (let r in roles) {
                    roleName += " ";
                    if (roles[r].roleName === "ROLE_ADMIN") {
                        roleName += "管理员";
                    } else if (roles[r].roleName === "ROLE_STUDENT") {
                        roleName += "学生";
                    } else if (roles[r].roleName === "ROLE_TEACHER") {
                        roleName += "教师";
                    }
                }
                return roleName;
            }
        }

    });
    new Vue({
        el:'#userInfo',
        data:{
            isActive:true
        }
    })

</script>
</body>
</html>