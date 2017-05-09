<%@ page pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../static.jsp" %>
    <title>课程内容</title>
    <style>

        a {
            font-size: 15px;
        }
        .list-item {
            border-bottom: 1px dashed rgb(207, 208, 209);
            height: 36px;
            line-height: 36px;
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
        <a href="javascript:">课程内容</a>
        <hr>
    </div>
    <h1 style="text-align: center">{{course[0][0]}}</h1>
    <div style="float: right;height: 500px;width: 300px">
        <sec:authorize access="hasRole('ROLE_TEACHER')">
            <i-button type="success" size="large" @click="addChapter" long>添加章节信息</i-button>
            <hr>
        </sec:authorize>

        <h3 style="text-align: center">{{course[0][0]}}</h3>
        <br>
        <p style="color:#2e6da4;font-size: 15px">{{course[0][1]}}</p>
    </div>
    <div style="margin-top: 20px;width: 800px">
    <Collapse accordion>
        <Panel v-for="chapter in chapters">
            第{{chapter.num}}章.{{chapter.title}}
            <ul slot="content" style="margin-left: 40px">
                <li  v-for="section in chapter.childChapters" class="list-item">
                    <a :href="section.id | formatUrl">第{{section.num}}节.{{section.title}}</a>
                </li>
            </ul>
        </Panel>
    </Collapse>
    </div>


</div> <!-- /container -->
<%@include file="../js.jsp"%>

<script>
    let app = new Vue({
        el: '#container',
        data: {
            courseId:${courseId},
            chapters: [],
            course:{}
        },
        methods: {
            //获取课程的章节信息
            getChapters: function (courseId) {
                $.ajax({
                    type: 'GET',
                    url: 'course/' + courseId + '/chapters',
                    dataType: 'JSON',
                    success: function (data) {
                        this.chapters = data;
                    }.bind(this),
                    error: function (err) {
                        console.log(err);
                    }
                })
            },
            addChapter:function () {
                window.location.href="teacher/addChapter?courseId=" + this.courseId;
            },
            getSharpCourse:function () {
                $.get("course/sharp/" + this.courseId,function (data) {
                    this.course = data;
                }.bind(this))
            }
        },
        filters:{
          formatUrl:function (sectionId) {
              return "course/chapterContentPage/" + sectionId;
          }
        },
        mounted: function () {
            this.getSharpCourse();
            this.getChapters(this.courseId);

        }
    });
</script>
</body>
</html>