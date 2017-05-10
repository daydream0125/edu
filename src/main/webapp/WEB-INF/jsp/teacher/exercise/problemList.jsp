<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="../../static.jsp" %>
<html>
<head>
    <title>题库管理</title>
</head>
<body>
<%@include file="../../navigation.jsp" %>

<div class="container" id="container">
    <div style="color: #004772;font-weight: bold">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="teacher/problemManage">题库管理</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">查看题目</a>
        <hr>
    </div>
    <p>筛选条件</p>
    课程
    <i-select style="width:300px" @on-change="showChapters" size="large">
        <i-option v-for="course in courses" :value="course.courseId">{{ course.courseName }}</i-option>
    </i-select>
    章
    <i-select style="width:300px" @on-change="showSections" size="large">
        <i-option v-for="chapter in chapters" :value="chapter.id">第{{chapter.num}}章{{ chapter.title }}
        </i-option>
    </i-select>
    节
    <i-select style="width:300px" @on-change="showProblems" size="large">
        <i-option v-for="section in sections" :value="section.id">第{{section.num}}节{{ section.title }}
        </i-option>
    </i-select>
    <br>
    <br>
    <hr>
    <p v-if="problemsLen !== 0">共<Tag type="border" color="blue">{{problemsLen}}</Tag>道题
    </p>
    <br>
    <div class="panel panel-info" v-for="p in currentProblems">

        <div class="panel-heading">
            <h3 class="panel-title" v-if="p.title != null">{{p.title}}</h3>
        </div>
        <div class="panel-body">
            <div v-if="p.problemPicPath != 'none'">
                <p>题目:</p>
                <img :src="p.problemPicPath">
            </div>
            <div v-else>
                <div v-if="p.type === 1">
                    <div v-for="(c,index) in p.description.split('\n')">
                        <i-input readonly :value="c">
                            <span slot="prepend">{{index | formatChoose}}</span>
                        </i-input>
                    </div>
                </div>
            </div>
            <p>参考答案:</p>
            <div v-if="p.solutionPicPath != 'none'  ">
                <img :src="p.solutionPicPath">
            </div>
            <div v-else>
                {{p.solution}}
            </div>
            <p style="color:gray;font-size: 15px">最后更新时间:{{p.updateTime}}</p>
            <p style="color:gray;font-size: 15px">总提交次数:
                <Tag type="border" color="blue">{{p.totalSolutions}}</Tag>
            </p>
            <p style="color:gray;font-size: 15px">接受提交次数:
                <Tag type="border" color="blue">{{p.acceptedSolutions}}</Tag>
            </p>
        </div>
    </div>
    <div style="float: right;margin-bottom: 40px" v-if="problemsLen !== 0">
        <Page :total="problemsLen" @on-change="fetchData" show-total :page-size="pageSize" show-elevator>
        </Page>
    </div>
</div>
<%@include file="../../js.jsp" %>
<script>
    new Vue({
        el: '#container',
        data: {
            teacherId:${sessionScope.account.userId},
            courses: [],
            chapters: [],
            sections: [],
            problems: [],
            problemsLen: 0,
            currentProblems: [],
            pageSize: 4,
            choose: []
        },
        methods: {
            fetchData: function (page) {
                let index = (page - 1) * this.pageSize;
                this.currentProblems = this.problems.slice(index, index + this.pageSize);
            },
            getCourseByTeacherId: function (teacherId) {
                $.ajax({
                    type: "GET",
                    url: "teacher/" + teacherId + "/courses",
                    dataType: 'JSON',
                    success: function (data) {
                        this.courses = data;
                    }.bind(this),
                    error: function (err) {
                        console.log(err);
                    }
                })
            },
            getChapterByCourseId: function (courseId) {
                $.ajax({
                    type: 'GET',
                    url: 'chapters/' + courseId,
                    dataType: 'JSON',
                    success: function (data) {
                        this.chapters = data;
                    }.bind(this)
                })
            },
            getSectionsByChapterId: function (chapterId) {
                $.ajax({
                    type: 'GET',
                    url: 'sections/' + chapterId,
                    dataType: 'JSON',
                    success: function (data) {
                        this.sections = data;
                    }.bind(this),
                    error: function (err) {
                        console.log(err)
                    }
                })
            },
            filterProblems: function (filterTypes, keywords) {
                $.get("teacher/filterProblems", {type: filterTypes, keywords: keywords}, function (data) {
                    this.problems = data;
                    this.currentProblems = this.problems.slice(0, this.pageSize);
                    this.problemsLen = this.problems.length;
                }.bind(this))
            },
            showChapters: function (courseId) {
                this.getChapterByCourseId(courseId);
                this.filterProblems(1, courseId);
            },
            showSections: function (chapterId) {
                this.getSectionsByChapterId(chapterId);
                this.filterProblems(2, chapterId);
            },
            showProblems: function (sectionId) {
                this.filterProblems(3, sectionId);
            },
            formatChoose: function (desc, index) {
                return desc.split('\n')[index];
            }
        },
        mounted: function () {
            this.getCourseByTeacherId(this.teacherId);
            let id = ${sectionId};
            if (id !== 0) {
                this.filterProblems(3, id);
            }
        }, filters: {
            formatChoose: function (index) {
                return String.fromCharCode(index + 65);
            }
        }
    });
    new Vue({
        el: "#teacher-problem",
        data: {
            isActive: true
        }
    })
</script>
</body>
</html>

