<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="../../static.jsp" %>
<html>
<head>
    <title>题库管理</title>
</head>
<body>
<%@include file="../../navigation.jsp" %>

<div class="container" id="container">
    <i-button type="primary" size="large" @click="markObjectiveProblem" v-if="!isMark">批改客观题</i-button>
    <i-button type="success" size="large" @click="countScore" v-if="!isMark">计算总分</i-button>
    <br>
    <br>
    <h1>主观题批改</h1>
    <div style="text-align: center" v-if="!isMark">
        <h2>待批改学生</h2>
        <br>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>姓名</th>
                <th>学号</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-if="classmates.length === 0" style="text-align: center">
                <td style="color: red">暂无可批改数据</td>
            </tr>
            <tr v-for="(c,index) in classmates">
                <td>{{c.account.name}}</td>
                <td>{{c.account.cardNumber}}</td>
                <td>
                    <i-button type="primary" size="large" @click="mark(index)">批改作业</i-button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div v-if="isMark">
        <p>题目</p>
        <div v-if="activeAnswer.problem.problemPicPath !== 'none'">
            <img :src="activeAnswer.problem.problemPicPath">
        </div>
        <div v-else>
            <pre>{{activeAnswer.problem.title}}</pre>
        </div>
        <p>答案</p>
        <div v-if="activeAnswer.answerPic !== 'none'">
            <img :src="activeAnswer.answerPic" alt="">
        </div>
        <div v-else>
            <pre>{{activeAnswer.answer}}</pre>
        </div>
        <p>分数
            <Input-number :max="10" :min="1" v-model="scores[answerIndex]"></Input-number>
        </p>

        <br>
        <br>
        <Row>
            <i-col span="2">
                <i-button v-if="answerIndex !== 0" @click="previous" type="primary">上一题</i-button>
            </i-col>
            <i-col span="2">
                <i-button v-if="answerIndex+1 < answersLen" type="primary" @click="next">下一题</i-button>
            </i-col>

        </Row>
        <br>
        <i-button v-if="answerIndex+1 === answersLen" @click="submit" type="success">提交</i-button>
    </div>
    <Modal v-model="marking" :closable="false" :mask-closable="false">
        <p>{{markMessage}}</p>
        <Spin size="large"></Spin>
    </Modal>
</div>
<%@include file="../../js.jsp" %>
<script>
    new Vue({
        el: '#container',
        data: {
            exerciseId:${exerciseId},
            answers: [],
            activeAnswer: {},
            answersLen: 0,
            classmates: [],
            isMark: false,
            scores: [],
            answerIndex: 0,
            classmateIndex: 0,
            marking: false,
            markMessage: '正在批改，请稍后，待提示批改成功后点击确认按钮完成批改',
            answersId: []
        },
        methods: {
            countScore: function () {
                $.post("teacher/exercise/countScore", {exerciseId: this.exerciseId}, function (data) {
                    if (data === true) {
                        alert("计算成功");
                    }
                    else {
                        alert("计算失败！请重新计算或与管理员联系")
                    }
                });
            },
            next: function () {
                this.activeAnswer = this.answers[++this.answerIndex];
            },
            previous: function () {
                this.activeAnswer = this.answers[--this.answerIndex];
            },
            submit: function () {
                this.answersId = new Array(this.answersLen);
                for (let i = 0; i < this.answers.length; i++) {
                    this.answersId[i] = this.answers[i].answerId
                }
                $.post("teacher/exercise/markSubjectiveProblem", {
                    scores: this.scores,
                    answersId: this.answersId,
                }, function (data) {
                    if (data === true) {
                        alert("批改成功");
                        this.classmates.splice(this.classmateIndex, 1);
                    } else {
                        alert("批改失败")
                    }

                    this.isMark = false;
                }.bind(this))
            },
            mark: function (index) {
                this.getAnswers(this.classmates[index].classmateId);
                if (this.answersLen === 0) {
                    alert("暂无主观题可供批改");
                    return;
                }
                this.classmateIndex = index;
                this.scores = new Array(this.answersLen);
                this.isMark = true;
            },
            getAnswers: function (classmateId) {
                $.ajax({
                    url: "teacher/subjectiveAnswers",
                    data: {exerciseId: this.exerciseId, classmateId: classmateId},
                    async: false,
                    success: function (data) {
                        this.answers = data;
                        this.answersLen = this.answers.length;
                        this.activeAnswer = this.answers[this.answerIndex];
                    }.bind(this),
                    error: function (err) {
                        console.log(err)
                    }
                })
            },
            getClassmates: function (exerciseId) {
                $.get("teacher/exercise/classmates/" + exerciseId, function (data) {
                    this.classmates = data;
                }.bind(this))
            },
            markObjectiveProblem: function () {
                this.marking = true;
                $.get("teacher/exercise/markObjectiveProblem/" + this.exerciseId, function (data) {
                    if (data === true) {
                        this.markMessage = "客观题批改成功！"
                    } else {
                        this.markMessage = "客观题批改失败!请联系管理员"
                    }
                }.bind(this))
            }
        },
        mounted: function () {
            this.getClassmates(this.exerciseId);
        }
    });
    new Vue({
        el: "#teacher-exercise",
        data: {
            isActive: true
        }
    })
</script>
</body>
</html>

