<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="../static.jsp" %>
<html>
<head>
    <title>题库管理</title>
</head>
<body>
<%@include file="../navigation.jsp" %>

<div class="container" id="container">
    <div  style="color: #004772;font-weight: bold;margin-bottom: 20px">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="student/score">我的成绩</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">详细得分</a>
        <hr>
    </div>
    <h1 style="text-align: center">{{answers[0].exercise.exerciseName}}</h1>
    <br>
    <i-col offset="14">
        <h4>答题人：${sessionScope.account.name}</h4>
    </i-col>
    <p>本次练习共<Tag type="border" color="blue">{{answersLen}}</Tag>道题
    </p>
    <br>
    <div class="panel panel-info" v-for="a in answers">

        <div class="panel-heading">
            <h3 class="panel-title" v-if="a.problem.title != null">{{a.problem.title}}</h3>
        </div>
        <div class="panel-body">
            <div v-if="a.problem.problemPicPath != 'none'">
                <p>题目:</p>
                <img :src="a.problem.problemPicPath">
            </div>
            <div v-else>
                <div v-if="a.problem.type === 1">
                    <div v-for="(c,index) in a.problem.description.split('\n')">
                        <i-input readonly :value="c">
                            <span slot="prepend">{{index | formatChoose}}</span>
                        </i-input>
                    </div>
                </div>
            </div>
            <p>你的答案</p>
            <div v-if="a.answerPic !== 'none'">
                <img :src="a.answerPic" alt="">
            </div>
            <div v-else>
                <p>{{a.answer}}</p>
            </div>
            <p>参考答案:</p>
            <div v-if="a.problem.solutionPicPath != 'none'  ">
                <img :src="a.problem.solutionPicPath">
            </div>
            <div v-else>
                {{a.problem.solution}}
            </div>
            <div>得分：{{a.result}}</div>
            <div>
                <p>评语：{{a.comment}}</p>
            </div>
            <div>
                时间：{{a.startTime}} --> {{a.endTime}}
            </div>

        </div>
    </div>
</div>
<%@include file="../js.jsp" %>
<script>
    new Vue({
        el: '#container',
        data: {
            userId:${sessionScope.account.userId},
            exerciseId:${exerciseId},
            answers: [],
            answersLen: 0,
        },
        methods: {
            checkIsExist: function () {
                $.ajax({
                    url: 'student/exercise/existAnswers',
                    data: {
                        exerciseId: this.exerciseId,
                        userId: this.userId
                    },
                    async: false,
                    success: function (data) {
                        if (data === false) {
                            alert("您还未提交答案或老师未发布成绩，请耐心等待");
                            window.location.href = "index"
                        }
                    }
                })
            },
            getSubmitAnswers: function () {
                $.get("student/exercise/answers", {exerciseId: this.exerciseId, userId: this.userId}, function (data) {
                    this.answers = data;
                    this.answersLen = data.length;
                }.bind(this))
            },
            formatChoose: function (desc, index) {
                return desc.split('\n')[index];
            }
        },
        mounted: function () {
            this.checkIsExist();
            this.getSubmitAnswers();
        }, filters: {
            formatChoose: function (index) {
                return String.fromCharCode(index + 65);
            }
        }
    });
    new Vue({
        el: "#student-exercise",
        data: {
            isActive: true
        }
    })
</script>
</body>
</html>

