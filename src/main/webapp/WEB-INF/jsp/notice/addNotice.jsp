<%@ page pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <%@include file="../static.jsp" %>
    <link href="static/editor/css/wangEditor.min.css" type="text/css" rel="stylesheet">
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
    <div style="color: #004772;font-weight: bold;margin-top: 20px">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="notice">通知公告</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">新增</a>

        <hr>
    </div>

    <div style="margin-top: 30px">
        <h3>新增通知</h3>
        <br>
    <p>标题</p>
    <i-input v-model="title" placeholder="请输入通知标题" style="width: 300px" size="large">

    </i-input>
        <br>
    <p>内容</p>
    <div id="content" style="height: 400px">
    </div>
        <div style="margin-top: 20px;margin-bottom: 20px">
            <i-button type="success" size="large" @click="submit" style="width: 100px">提交</i-button>
        </div>
    </div>
</div>

<%@include file="../js.jsp" %>
<script src="static/editor/js/wangEditor.min.js"></script>
<script>
    new Vue({
        el: '#container',
        data: {
            userId:<sec:authentication property="principal.username" />,
            title: '',
            content: ''
        },
        methods: {
            submit: function () {
                $.post("notice/add", {title: this.title, content: this.content, userId: this.userId}, function (data) {
                    if (data === true) {
                        alert("添加成功");
                        window.location.href = "notice"
                    } else {
                        alert("添加失败，请重试")
                    }
                })
            }
        },
        mounted: function () {
            let self = this;
            // 创建编辑器
            let editor = new wangEditor('content');
            editor.onchange = function () {
                // onchange 事件中更新数据
                self.content = editor.$txt.html();
            };
            // 上传图片（举例）
            editor.config.uploadImgUrl = 'notice/uploadPicture';
            editor.config.uploadImgFileName = 'pic';
            // 设置 headers（举例）
            editor.config.uploadHeaders = {
                'Accept': 'text/x-json'
            };

            // 隐藏掉插入网络图片功能。该配置，只有在你正确配置了图片上传功能之后才可用。
            editor.config.hideLinkImg = true;
            editor.create();
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