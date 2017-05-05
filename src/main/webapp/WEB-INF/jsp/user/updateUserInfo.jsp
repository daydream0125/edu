<%@ page pageEncoding="UTF-8" language="java" %>
<%@ page session="true" %>
<%@include file="../static.jsp" %>
<html>
<head>
    <title>个人信息</title>
    <style>
        hr {
            display: block;
            -webkit-margin-before: 0.5em;
            -webkit-margin-after: 0.5em;
            -webkit-margin-start: auto;
            -webkit-margin-end: auto;
            border-style: inset;
            border-width: 1px;
        }
    </style>
</head>
<body>
<%@include file="../navigation.jsp" %>
<div class="container" id="container">
    <i-button type="primary" size="large" @click="updateInfo">更新个人信息</i-button>
        姓名
        <i-input v-model="user.name"></i-input>
        别名
        <i-input v-model="user.userInfo.nickName"></i-input>
        学号/教工号
        <i-input v-model="user.cardNumber"></i-input>
        邮箱
        <i-input v-model="user.userInfo.email"></i-input>
        座机
        <i-input v-model="user.userInfo.phone"></i-input>
        手机
        <i-input v-model="user.userInfo.telephone"></i-input>
        生日
        <Date-picker type="date" v-model="user.userInfo.birthday" style="width: 200px"></Date-picker>
        个人经历
        <i-input type="textarea" size="large" v-model="user.userInfo.desc"></i-input>
        <i-button type="primary" size="large" @click="updateInfo">更新</i-button>
    </div> <!-- /container -->
    <%@include file="../js.jsp" %>
    <script src="static/dateFormat.js"></script>
    <script>
        new Vue({
            el: "#container",
            data: {
                userId:${sessionScope.account.userId},
                user: {},
            },
            methods: {
                updateInfo: function () {
                    $.post("updateUserInfo", {
                        name: this.user.name,
                        nickName: this.user.userInfo.nickName,
                        userId: this.user.userId,
                        cardNumber: this.user.cardNumber,
                        email: this.user.userInfo.email,
                        phone: this.user.userInfo.phone,
                        telephone: this.user.userInfo.telephone,
                        desc: this.user.userInfo.desc
                    },function (data) {
                        if (data === true) {
                            alert("更新成功");
                            window.location.href = "userInfo";
                        } else {
                            alert("更新失败")
                        }
                    }.bind(this))
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