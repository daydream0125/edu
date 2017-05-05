<%@ page pageEncoding="UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <%@include file="../static.jsp" %>
    <style>
        table {
            font-size: 15px;
        }
    </style>
</head>
<body>
<%@include file="../navigation.jsp" %>
<div id="app" class="container">
    <br>
    <i-table :columns="exerciseColumn" :data="currentExercises" size="large">

    </i-table>
    <br>
    <Row>
        <i-col offset="14">
            <Page :total="exercisesLen" @on-change="fetchData" show-total :page-size="pageSize" show-elevator>
            </Page>
        </i-col>
    </Row>

    <Modal title="确认开始做题" @on-ok="confirmStartExercise(startExerciseId)" v-model="showStartExercise">
        <span style="font-size: larger">本次练习共包含<Tag color="blue">{{problemCount}}</Tag>道题,确认开始做题?</span>
    </Modal>
</div>
<%@include file="../js.jsp" %>


<script>

    new Vue({
        el: '#app',
        data: {
            userId:${sessionScope.account.userId},
            currentExercises: [], //当前页展示的练习
            pageSize: 4,
            showStartExercise: false,
            startExerciseId: 0,
            problemCount: 0,
            exercises: [],
            exercisesLen: 0,
            exerciseColumn: [
                {
                    title: '课程',
                    key: 'course.courseName',
                    width: 180,
                    align: 'center',
                    render (row, column, index) {
                        return `${'${'}row.course.courseName}`;
                    }
                },
                {
                    title: '班级',
                    width: 160,
                    align: 'center',
                    render (row, column, index) {
                        return `${'${'}row.clazz.className}`;
                    }
                },
                {
                    title: '练习名称',
                    key: 'exerciseName',
                    width: 200,
                    sortable: true,
                    align: 'center',
                },
                {
                    title: '开始时间',
                    key: 'startTime',
                    width: 140,
                    align: 'center',
                    sortable: true
                },
                {
                    title: '结束时间',
                    key: 'endTime',
                    sortable: true,
                    width: 140,
                    align: 'center',
                },
                {
                    title: '性质',
                    key: 'isFinal',
                    width: 120,
                    align: 'center',
                    sortable: true,
                    render (row, column, index) {
                        return `${'${'}row.isFinal === true?'最终考试':'平时练习'}`;
                    }
                },
                {
                    title: '状态',
                    key: 'endTime',
                    width: 100,
                    align: 'center',
                    sortable: true,
                    render (row, column, index) {
                        let endTime = `${'${'}row.endTime}`;
                        let now = new Date();
                        endTime = endTime.replace('-', '/');
                        let d = new Date(Date.parse(endTime));
                        return d > now ? '<Tag color="green">进行中</Tag>' : '<Tag color="yellow">已结束</Tag>';
                    }
                },
                {
                    title: '操作',
                    key: 'isFinish',
                    render (row, column, index) {
                        let endTime = `${'${'}row.endTime}`;
                        let now = new Date();
                        endTime = endTime.replace('-', '/');
                        let d = new Date(Date.parse(endTime));
                        let id = `${'${'}index}`;
                        if (d > now) {
                            return `<i-button type="primary" @click="startExercise(` + id + `)">开始答题</i-button>`;
                        } else {
                            return `<Tooltip content="答题时间已结束"><i-button type="warning" disbaled>开始答题</i-button></Tooltip>`;
                        }

                    }
                }
            ]
        },
        methods: {

            getExercisesByUserId(userId) {
                $.ajax({
                    type: 'get',
                    url: 'student/exercisesByUserId/' + this.userId,
                    dataType: 'json',
                    success: function (data) {
                        this.exercises = data;
                        this.exercisesLen = this.exercises.length;
                        this.currentExercises = this.exercises.slice(0, this.pageSize);
                    }.bind(this),
                    error: function (err) {
                        console.log(err);
                    }
                });
            },
            //查询该练习下的题目数
            startExercise: function (index) {
                let flag = false;
                $.ajax({
                    url:'student/checkSubmit',
                    data:{
                        userId: this.userId,
                        exerciseId: this.currentExercises[index].exerciseId
                    },
                    async:false,
                    success:function (data) {
                        flag = data;
                    }.bind(this)
                });
                if (flag === true) {
                    alert("已提交过答案，无法重复提交");
                    return;
                }
                $.ajax({
                    type: 'get',
                    url: '${pageContext.request.contextPath}/student/exercise/problemCount',
                    data: {
                        'exerciseId': this.currentExercises[index].exerciseId,
                    },
                    dataType: 'json',
                    success: function (data) {
                        this.problemCount = data;
                        this.showStartExercise = true;
                        this.startExerciseId = this.currentExercises[index].exerciseId
                    }.bind(this),
                    error: function (err) {
                        console.log(err);
                    }
                });
            },
            //当点击确认做题时,跳转至做题页面
            confirmStartExercise: function () {
                window.location.href = "student/doExercise/" + this.startExerciseId;
            },
            //获取该页的练习
            fetchData: function (page) {
                let index = (page - 1) * this.pageSize;
                this.currentExercises = this.exercises.slice(index, index + this.pageSize);
            }
        },
        //页面加载完成后获取 userId 下的 全部exercise
        created: function () {
            this.getExercisesByUserId(this.userId);
        }
    })
</script>
<script>
    new Vue({
        el: "#student-exercise",
        data: {
            isActive: true
        }
    })

</script>
</body>
</html>