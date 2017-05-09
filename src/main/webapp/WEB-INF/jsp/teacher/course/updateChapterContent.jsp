<%@ page pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <title>增加课程章节信息</title>
    <%@ include file="../../static.jsp" %>
    <link href="static/editor/css/wangEditor.min.css" type="text/css" rel="stylesheet">

    <style>
        .p {
            font-size: 20px;
            font-weight: 800;
            color: #0f0f0f;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<%@include file="../../navigation.jsp" %>
<div class="container" id="container">
    <div  style="color: #004772;font-weight: bold">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:" @click="click">章节信息</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">更新</a>
        <hr>
    </div>
    <p class="p">第{{num}}节.{{title}}</p>
    <div id="content" style="height: 400px" v-html="chapterContent.content">
    </div>
    <br>
    <i-button type="success" size="large" @click="submit">提交</i-button>

</div> <!-- /container -->

<%@include file="../../js.jsp" %>
<script src="static/editor/js/wangEditor.min.js"></script>
<script>
    const vm = new Vue({
        el: '#container',
        data: {
            sectionId:${sectionId},
            chapterContent: {},
            editorContent: '',
            num: 0,
            title: ''
        },
        methods: {
            getChapterContent: function (sectionId) {
                $.get("course/chapterContent/" + sectionId, function (data) {
                    this.chapterContent = data;
                    this.num = data.courseChapter.num;
                    this.title = data.courseChapter.title;
                }.bind(this));
            },
            submit: function () {
                $.post("teacher/course/updateChapterContent", {
                    sectionId: this.chapterContent.id,
                    content: this.editorContent
                }, function (data) {
                    if (data === true) {
                        alert("更新成功!");
                        //跳转至展示页面
                        window.location.href="course/chapterContentPage/" + this.sectionId;
                    } else {
                        alert("更新失败!");
                    }
                }.bind(this))
            },
            click:function () {
                window.location.href="course/chapterContentPage/" + this.sectionId;
            }
        },
        mounted: function () {
            this.getChapterContent(this.sectionId);
            let self = this;
            // 创建编辑器
            let editor = new wangEditor('content');
            editor.onchange = function () {
                // onchange 事件中更新数据
                self.editorContent = editor.$txt.html();
            };
            // 上传图片（举例）
            editor.config.uploadImgUrl = 'teacher/uploadContentPic';
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
        el:'#teacher-course',
        data:{
            isActive:true
        }
    })
</script>
</body>
</html>