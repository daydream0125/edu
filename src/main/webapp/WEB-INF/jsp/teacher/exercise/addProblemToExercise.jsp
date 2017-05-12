<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <%@include file="../../static.jsp" %>
    <style>
        table {
            color: #0f0f0f;
            font-size: 20px
        }

        p {
            color: #0f0f0f;
            font-size: 20px
        }

    </style>
</head>
<body>
<%@include file="../../navigation.jsp" %>
<div id="app" class="container">
    <div style="color: #004772;font-weight: bold">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="teacher/exerciseManage">作业管理</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">添加题目</a>
        <hr>
    </div>
    <h1 style="color: black;text-align: center">添加题目</h1>
    <br>
    <h3>{{exercise.exerciseName}}</h3>
    <br>
    <p>该作业共有
        <Tag type="border" color="blue">{{problemCount}}</Tag>
        道题
    </p>
    <p>选择章节</p>
    章
    <i-select style="width:400px" @on-change="showSections" size="large">
        <i-option v-for="chapter in chapters" :value="chapter.id">第{{chapter.num}}章{{ chapter.title }}
        </i-option>
    </i-select>
    节
    <i-select style="width:400px" size="large" v-model="sectionId">
        <i-option v-for="section in sections" :value="section.id">第{{section.num}}节{{ section.title }}
        </i-option>
    </i-select>
    <i-button size="large" type="primary" @click="viewProblem(sectionId)">查询</i-button>
    <br>
    <br>
    <b style="color: red">注意:仅当按下最后提交按钮上述添加按钮方可生效</b>
    <table class="table">
        <thead>
            <tr>
                <th>题干</th>
                <th>添加</th>
            </tr>
        </thead>
        <tbody>
            <tr v-for="(problem,index) in problems">
                <td v-if="problem.problemPicPath === 'none'">
                    {{problem.title}}
                </td>
                <td v-else>
                    <img :src="problem.problemPicPath">
                </td>
                <td>
                    <i-button  type="success" @click="addProblem(index)" icon="plus"></i-button>
                </td>
            </tr>
        </tbody>
    </table>
    <i-button size="large" type="primary" @click="submit">提交</i-button>
</div>
<%@include file="../../js.jsp" %>
<script>
    new Vue({
        el: '#app',
        data: {
            teacherId:<sec:authentication property="principal.username" />,
            exerciseId:${exerciseId},
            exercise: {},
            problemCount: 0,
            chapters: [],
            sections: [],
            sectionId: 0,
            problems: [],
            submitProblems:[]

        },
        methods: {
            addProblem:function (index) {
                this.submitProblems.push(this.problems[index].problemId);
                this.problems.splice(index,1);
            },
            submit:function () {
                if (this.submitProblems.length === 0) {
                    alert("未添加题目");
                } else {
                    $.post("teacher/submitProblems",{submitProblems:this.submitProblems,exerciseId:this.exerciseId},function (data) {
                        if (data === true) {
                            alert("添加成功");
                            this.getProblemCount(this.exerciseId);
                        } else {
                            alert("添加失败,请重新添加");
                        }
                    }.bind(this))
                }
            },
            viewProblem: function (sectionId) {
                $.get("problems?sectionId=" + sectionId, function (data) {
                    this.problems = data;
                }.bind(this))
            },
            showSections: function (chapterId) {
                this.getSectionsByChapterId(chapterId);
            },
            getChapterByCourseId: function (courseId) {
                $.ajax({
                    type: 'GET',
                    url: 'course/' + courseId + "/chapters",
                    dataType: 'JSON',
                    success: function (data) {
                        this.chapters = data;
                    }.bind(this)
                });

            },
            getSectionsByChapterId: function (chapterId) {
                $.ajax({
                    type: 'GET',
                    url: '${pageContext.request.contextPath}/sections/' + chapterId,
                    dataType: 'JSON',
                    success: function (data) {
                        this.sections = data;
                    }.bind(this),
                    error: function (err) {
                        console.log(err)
                    }
                });

            },
            getExercise: function (exerciseId) {
                $.get("exercise/" + exerciseId, function (data) {
                    this.exercise = data;
                    this.getChapterByCourseId(this.exercise.course.courseId);
                }.bind(this));
            },
            //查询该练习下的题目数
            getProblemCount: function (exerciseId) {
                $.ajax({
                    type: 'get',
                    url: 'exercise/' + exerciseId + '/problemCount',
                    dataType: 'json',
                    success: function (data) {
                        this.problemCount = data;
                    }.bind(this),
                    error: function (err) {
                        console.log(err);
                    }
                });
            },

        },
        mounted: function () {
            this.$nextTick(function () {
                // 代码保证 this.$el 在 document 中
                this.getExercise(this.exerciseId);
                this.getProblemCount(this.exerciseId);
            })
        },
        filters: {}
    })
</script>
<script>
    new Vue({
        el: "#teacher-exercise",
        data: {
            isActive: true
        }
    })

</script>
</body>
</html>