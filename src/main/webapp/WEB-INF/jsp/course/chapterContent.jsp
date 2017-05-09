
<%@ page pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../static.jsp" %>
    <title>课程内容</title>
    <style>
        .title{
            font-size: 30px;
            color: #0f0f0f;
            text-align: center;
        }
    </style>
</head>
<body>
<%@include file="../navigation.jsp" %>
<div class="container" id="container">
    <div  style="color: #004772;font-weight: bold">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:" @click="goToChapters">课程内容</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">章节信息</a>
        <hr>
    </div>
    <div style="float: right;margin-right: 100px">
    <sec:authorize access="hasRole('ROLE_TEACHER')">
        <i-button type="primary" size="large" @click="update">更新章节信息</i-button>
        <i-button type="primary" size="large" @click="upload">上传视屏</i-button>
    </sec:authorize>
    <sec:authorize access="hasAnyRole('ROLE_TEACHER','ROLE_STUDENT')">
        <i-button type="primary" size="large" @click="showVideo = true" v-if="content.videoPath !== null">观看视屏</i-button>
        <i-button type="primary" size="large" @click="goToChapters">返回章节信息</i-button>
    </sec:authorize>
    </div>
    <div style="margin-top: 60px">
    <h1 class="title">第{{content.courseChapter.num}}节&nbsp;{{content.courseChapter.title}}</h1>
    <Row>
        <i-col offset="20">
            <p style="color: #3c3c3c">{{content.courseChapter.course.teacher.name}}</p>
        </i-col>
    </Row>
    <div v-html="content.content">
    </div>
    </div>
    <br>
    <br>
    <Modal v-model="showVideo" width="820" height="500" :closable="false" :mask-closable="false" @on-ok="showVideo=false" @on-cancle="showVideo=false">
        <video :src="content.videoPath" controls="controls" width="790" height="auto" v-if="showVideo">
            您的浏览器不支持 video 标签。
        </video>
    </Modal>
    <Modal v-model="uploadVideo" :closable="false" :mask-closable="false">
        <Upload type="drag" action="teacher/uploadChapterVideo" name="video" :data="{'contentId':content.id}" :on-success="handleSuccess">
            <div style="padding: 20px 0">
                <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                <p>点击或将视屏拖拽到这里上传</p>
            </div>
        </Upload>
    </Modal>
    <Modal v-model="confirmUpload" :closable="false" :mask-closable="false" @on-ok="uploadVideo=true">
        <b style="font-size: larger">视屏已存在,确定要覆盖吗</b>
    </Modal>

</div> <!-- /container -->
<%@include file="../js.jsp"%>
<script>
    let app = new Vue({
        el: '#container',
        data: {
            sectionId:${sectionId},
            content:{},
            showVideo:false,
            uploadVideo:false,
            confirmUpload:false
        },
        methods: {
            //获取小节详细信息
            getChapterContent: function (sectionId) {
                $.ajax({
                    type: 'GET',
                    url: 'course/chapterContent/' + sectionId,
                    dataType: 'JSON',
                    success: function (data) {
                        this.content = data;
                    }.bind(this),
                    error: function (err) {
                        console.log(err);
                    }
                })
            },
            upload:function () {
                if (this.content.videoPath !== null)  {
                    this.confirmUpload = true;
                    return;
                }
                this.uploadVideo = true;
            },
            handleSuccess(res) {
                if (res === "error") {
                    this.$Modal.error({
                        title: '上传失败',
                        content: '上传失败,请重新上传'
                    });

                } else{
                    this.content.videoPath = res;
                    this.$Modal.success({
                        title: '上传成功',
                        content: '上传成功,点击 观看视屏 观看'
                    });
                }

            },
            update:function () {
                window.location.href="teacher/updateChapterContent/" + this.sectionId;
            },
            goToChapters:function () {
                $.get("course/courseId/" + this.sectionId,function (data) {
                    window.location.href="course/" + data +"/courseChapterInfo";
                });
            }
        },
        mounted: function () {
            this.getChapterContent(this.sectionId);
        }
    });
</script>
</body>
</html>