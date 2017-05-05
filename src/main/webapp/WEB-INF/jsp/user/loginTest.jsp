<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="../static.jsp" %>
    <style>
        .abc{
            margin-top: 70px;
        }
        body{
            background-color: whitesmoke;
            font-size: medium;
            color: #0f0f0f;
        }
    </style>
</head>
<body>
<div id="app" class="container">
    <div class="abc text-center">
        <h1>用户登录</h1>
        <br>
        <i-form ref="formInline" :model="formInline" >
            <Row type="flex" justify="center" class="code-row-bg">
                <i-col span="6">
                    <Form-item prop="user">
                        <i-input type="text" v-model="formInline.user" placeholder="Username" size="large" style="size: 100px"/>
                        <Icon type="ios-person-outline" slot="prepend" size="large"></Icon>
                        </Input>
                    </Form-item>


                    <Form-item prop="password">
                        <i-input type="password" v-model="formInline.password" placeholder="Password" size="large"/>
                        <Icon type="ios-locked-outline" slot="prepend" size="large"></Icon>
                        </Input>
                    </Form-item>

                    <Form-item>
                        <i-button type="primary" @click="submit" size="large">登录</i-button>
                    </Form-item>
                </i-col>
            </Row>
        </i-form>
    </div>

</div>
<%@include file="../js.jsp"%>
<script>
    new Vue({
        el: '#app',
        data: {
            formInline: {
                user: '',
                password: ''
            },
            ruleInline: {
                user: [
                    {required: true, message: '请填写用户名', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请填写密码', trigger: 'blur'},
                    {type: 'string', min: 6, message: '密码长度不能小于6位', trigger: 'blur'}
                ]
            }
        },
        methods: {
            handleSubmit(name) {
                this.$refs[name].validate((valid) => {
                    if (valid) {
                        this.$Message.success('提交成功!');
                    } else {
                        this.$Message.error('表单验证失败!');
                    }
                })
            },
            submit:function () {
                $.post("j_security_check",{username:this.formInline.user,password:this.formInline.password})
            }
        }
    })
</script>
</body>
</html>
