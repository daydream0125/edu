<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="../../static.jsp" %>
<html>
<head>
    <title>题库管理</title>
</head>
<body>
<%@include file="../../navigation.jsp" %>
<div class="container" id="container">
    <div>
        <h1>添加作业</h1>
        <p>课程 班级</p>
        <i-select style="width:300px" @on-change="showClasses" size="large" v-model="courseId">
            <i-option v-for="course in courses" :value="course.courseId">{{ course.courseName }}</i-option>
        </i-select>
        <i-select style="width:300px" size="large" v-model="classId">
            <i-option v-for="clazz in clazzes" :value="clazz.classId">{{ clazz.className }}</i-option>
        </i-select>
        <p>作业名称</p>
        <i-input placeholder="作业名称" size="large" v-model="exerciseName"></i-input>
        <p>作业描述</p>
        <i-input size="large" type="textarea" placeholder="作业描述" :rows="3" v-model="exerciseDesc"></i-input>
        <p>开始时间--结束时间</p>
        <Row>
            <i-col span="6">
                <Date-picker type="date" v-model="startTime" placeholder="开始时间" style="width: 200px"
                             size="large"></Date-picker>
            </i-col>
            <i-col span="6">
                <Date-picker type="date" v-model="endTime" placeholder="结束时间" style="width: 200px"
                             size="large"></Date-picker>
            </i-col>
        </Row>
        <p>最终考试
            <i-switch size="large" v-model="isFinal">
                <span slot="open">是</span>
                <span slot="close">否</span>
            </i-switch>
        </p>
        <i-button type="primary" size="large" @click="submit">提交</i-button>
    </div>
</div>
<%@include file="../../js.jsp" %>
<script src="static/dateFormat.js"></script>
<script>
    var vm = new Vue({
        el: '#container',
        data: {
            teacherId:${sessionScope.account.userId},
            courses: [],
            clazzes: [],
            courseId: 0,
            classId: 0,
            exerciseName: '',
            exerciseDesc: '',
            isFinal: false,
            startTime: '',
            endTime: '',
        },
        methods: {
            submit: function () {
                if (this.classId === 0 || this.courseId === 0) {
                    alert("未选择课程 班级信息");
                    return;
                }
                this.startTime = new Date(this.startTime).Format("yyyy-MM-dd");
                this.endTime = new Date(this.endTime).Format("yyyy-MM-dd");
                $.post("teacher/exercise", {
                    courseId: this.courseId, classId: this.classId, teacherId: this.teacherId,
                    exerciseName: this.exerciseName, exerciseDesc: this.exerciseDesc,
                    startTime: this.startTime, endTime: this.endTime, isFinal: this.isFinal
                }, function (data) {
                    if (data === true) {
                        alert("新增作业成功");
                        window.location.href = "teacher/exerciseManage";
                    } else {
                        alert("新增作业失败,请重新添加")
                    }
                })
            },
            showClasses: function (courseId) {
                if (courseId === 0) {
                    return;
                }
                $.get("course/" + courseId + "/clazzes", function (data) {
                    this.clazzes = data;
                }.bind(this))
            },
            getCourseByTeacherId: function (teacherId) {
                $.ajax({
                    type: "GET",
                    url: "${pageContext.request.contextPath}/teacher/" + teacherId + "/courses",
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
                    url: '${pageContext.request.contextPath}/chapters/' + courseId,
                    dataType: 'JSON',
                    success: function (data) {
                        this.chapters = data;
                    }.bind(this)
                })
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
                })
            },
            showChapters: function (courseId) {
                this.getChapterByCourseId(courseId);
            },
            showSections: function (chapterId) {
                this.getSectionsByChapterId(chapterId);
            }
        },
        mounted: function () {
            this.getCourseByTeacherId(this.teacherId);
        },
        filters: {
            formatChoose: function (index) {
                return String.fromCharCode(index + 64);
            }
        }
    });
</script>
<script>
    let active = new Vue({
        el: "#teacher-exercise",
        data: {
            isActive: true
        }
    })
</script>
</body>
</html>
