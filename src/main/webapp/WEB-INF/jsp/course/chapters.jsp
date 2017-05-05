<%@ page pageEncoding="UTF-8" language="java" %>
<html>
<head>
    <%@ include file="../static.jsp" %>
    <title>课程内容</title>
</head>
<body>
<%@include file="../navigation.jsp" %>
<div class="container" id="container">
    <h1>课程内容</h1>
    <br>
    <sec:authorize access="hasRole('ROLE_TEACHER')">
        <i-button type="primary" size="large" @click="addChapter">添加章节信息</i-button>
    </sec:authorize>
    <br>
    <br>
    <Collapse accordion>
        <Panel v-for="chapter in chapters">
            第{{chapter.num}}章.{{chapter.title}}
            <p slot="content" v-for="section in chapter.childChapters">
                <a :href="section.id | formatUrl">第{{section.num}}节.{{section.title}}</a>
            </p>
        </Panel>
    </Collapse>
</div> <!-- /container -->
<%@include file="../js.jsp"%>

<script>
    let app = new Vue({
        el: '#container',
        data: {
            courseId:${courseId},
            chapters: [],
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
            }
        },
        filters:{
          formatUrl:function (sectionId) {
              return "course/chapterContentPage/" + sectionId;
          }
        },
        mounted: function () {
            this.getChapters(this.courseId);
        }
    });
</script>
</body>
</html>