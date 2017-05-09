<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Title</title>
    <%@include file="../static.jsp" %>
    <link href="bootstrap/css/signin.css" rel="stylesheet">
    <style>
        .app {
            display: block;
            background-image: url('bg2.png');
            background-repeat: no-repeat;
            background-size: cover;
            background-attachment: fixed;
            overflow: hidden;
            width: 100%;
            height: 100%;
        }

        .frosted-glass {
            width: 100%;
            height: 100%;
            background: inherit;
            -webkit-filter: blur(5px);
            -moz-filter: blur(5px);
            -ms-filter: blur(5px);
            -o-filter: blur(5px);
            filter: blur(5px);
            filter: progid:DXImageTransform.Microsoft.Blur(PixelRadius=4, MakeShadow=false);
        }

        .content {
            margin-top: -700px;
            margin-left: 500px;
            position: relative;
            line-height: 31px;
            font-weight: 700;
            font-size: 25px;
            color: #969696;

        }

        body {
            margin-top: 0;
            margin-bottom: 0;
            padding-top: 0;
            padding-bottom: 0;
        }

        .main {
            width: 400px;
            height: 450px;
            margin: 60px auto 0;
            padding: 50px 50px 30px;
            background-color: white;
            border-radius: 4px;
            box-shadow: 0 0 8px rgba(0, 0, 0, .1);
            vertical-align: middle;
            display: inline-block;
            text-align: center;
        }

        a:active {
            color: #8a6d3b;
            text-decoration: none;
        }
    </style>
</head>
<body>
<div class="app" id="app">
    <div class="frosted-glass">
    </div>
    <div class="content">
        <div class="main">
            <a href="javascript:" @click="signIn">登录</a>
            <span class=>&nbsp;| &nbsp;</span>
            <a href="javascript:" @click="signUp">注册</a>


            <div v-if="sign_in" style="margin-top: 20px">
                <form class="form-signin" role="form" action="j_security_check" method="POST">
                    <input type="text" name="username" class="form-control" placeholder="username" required autofocus>
                    <input type="password" name="password" class="form-control" placeholder="password" required>
                    <c:if test="${not empty param.error}">
                        <p style="color: red">用户名或者密码错误</p>
                    </c:if>
                    <c:if test="${not empty param.message}">
                        <p style="color: green">注销成功</p>
                    </c:if>
                    <i-button type="primary" html-type="submit" long size="large" style="height: 46px">登录</i-button>
                </form>
            </div>

            <div v-if="sign_up">
                <form class="form-signin" role="form" action="/register" method="POST">
                    <input type="text" v-model="username" class="form-control" placeholder="用户名/手机号" required autofocus>
                    <input type="password" v-model="password" class="form-control" placeholder="密码" required>
                    <input type="password" v-model="password_check" class="form-control"
                           placeholder="确认密码" required @onblur="check">
                    <input type="text" v-model="code" maxlength="4" class="form-control" placeholder="验证码" required>
                    <img id="codeImg" border=0 :src="codePath" style="width: 100px;margin-top: 5px">
                    <a href="javascript:" @click="reloadImage" style="font-size: 15px;">看不清？换一张</a><br/>
                    <i-button type="primary" @click="register" long size="large" style="height: 46px;margin-top: 10px">
                        注册
                    </i-button>
                </form>
            </div>
        </div>
    </div>
</div>
<%@include file="../js.jsp" %>
<script>
    new Vue({
        el: '#app',
        data: {
            sign_in: true,
            sign_up: false,
            username: '',
            password: '',
            password_check: '',
            codePath: 'captcha-image',
            code: ''
        },
        methods: {
            check: function () {
                if (this.password !== this.password_check) {
                    alert("两次输入的密码不一致")
                }
            },
            signIn: function () {
                this.sign_in = true;
                this.sign_up = false;
            },
            signUp: function () {
                this.sign_up = true;
                this.sign_in = false;
            },
            reloadImage: function () {
                this.codePath += "/?id=" + Math.random();
            },
            register: function () {
                if (this.password !== this.password_check) {
                    alert("两次输入的密码不一致");
                    return;
                }
                $.post("register", {
                    username: this.username,
                    password: this.password,
                    code: this.code
                }, function (data) {
                    if (data === 'codeError') {
                        alert("验证码有误")
                    }
                    else if (data === 'accountExistError') {
                        alert("账号已存在")
                    } else {
                        alert("注册成功")
                    }
                }.bind(this));

            }
        }
    })
</script>
</body>
</html>
