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
        <a href="teacher/exercise/problemManage">题库管理</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">新增</a>
        <hr>
    </div>
    <div>
        <i-form :label-width="80">
            <h3>选择章节</h3>
            <Form-item label="章节信息">
                课程
                <i-select style="width:300px" @on-change="showChapters" size="large" v-model="courseId">
                    <i-option v-for="course in courses" :value="course.courseId">{{ course.courseName }}</i-option>
                </i-select>
                章
                <i-select style="width:300px" @on-change="showSections" size="large">
                    <i-option v-for="chapter in chapters" :value="chapter.id">第{{chapter.num}}章{{ chapter.title }}
                    </i-option>
                </i-select>
                节
                <i-select style="width:300px" v-model="sectionId" size="large">
                    <i-option v-for="section in sections" :value="section.id">第{{section.num}}节{{ section.title }}
                    </i-option>
                </i-select>
            </Form-item>
            <h3>题目信息</h3>
            <Form-item label="题型">
                <Row>
                    <i-col span="7">
                        <%--选择题型--%>
                        <i-select style="width:300px" v-model="type" size="large">
                            <i-option v-for="type in typeList" :value="type.value">{{type.label}}</i-option>
                        </i-select>
                    </i-col>
                    <i-col span="10">
                        <Tooltip placement="bottom">
                            <Upload action="teacher/uploadPicture" :on-success="handleProblemPic" name="pic">
                                <i-button type="ghost" icon="ios-cloud-upload-outline">上传图片</i-button>
                            </Upload>
                            <div slot="content">
                                <p style="font-size: larger">选择图片作为题目时</p>
                                <p style="font-size: larger">以下<b>非必要信息</b>可不用编辑</p>
                            </div>
                        </Tooltip>
                    </i-col>
                </Row>
            </Form-item>
            <Form-item label="题干">
                <i-input type="textarea" :rows="4" size="large" placeholder="题干" v-model="title"></i-input>
            </Form-item>
            <div v-if="type === 1">
                <Form-item label="选项">
                    <div v-for="n in 4">
                        <i-input v-model="choose[n-1]">
                            <span slot="prepend">{{n | formatChoose}}</span>
                        </i-input>
                    </div>
                </Form-item>
                <Form-item label="参考答案">
                    <Radio-group v-model="solution">
                        <Radio label="A">
                        </Radio>
                        <Radio label="B">
                        </Radio>
                        <Radio label="C">
                        </Radio>
                        <Radio label="D">
                        </Radio>
                    </Radio-group>
                </Form-item>
            </div>
            <Form-item label="参考答案" v-else-if="type === 2">
                <Radio-group v-model="solution">
                    <Radio label="对">
                    </Radio>
                    <Radio label="错">
                    </Radio>
                </Radio-group>
            </Form-item>
            <Form-item label="参考答案" v-else>
                <Tooltip placement="bottom">
                    <Upload action="teacher/uploadPicture" :on-success="handleSolutionPic" name="pic">
                        <i-button type="ghost" icon="ios-cloud-upload-outline">上传图片</i-button>
                    </Upload>
                    <div slot="content">
                        <p style="font-size: larger">选择图片作为题目答案时</p>
                        <p style="font-size: larger">以下<b>非必要信息</b>可不用编辑</p>
                    </div>
                </Tooltip>
                <i-input v-model="solution" type="textarea" :rows="4" placeholder="参考答案" size="large"
                ></i-input>
            </Form-item>
            <Form-item label="机器批改">
                <i-switch size="large" v-model="isManualJudge">
                    <span slot="open">是</span>
                    <span slot="close">否</span>
                </i-switch>
            </Form-item>
            <Form-item>
                <i-button type="primary" size="large" @click="submit">提交</i-button>
            </Form-item>
        </i-form>
    </div>
</div>
<%@include file="../../js.jsp" %>
<script>
    var vm = new Vue({
        el: '#container',
        data: {
            teacherId:${sessionScope.account.userId},
            problemId: 0,
            courses: [],
            chapters: [],
            sections: [],
            sectionId: 0,
            courseId: 0,
            title: '',
            solution: '',
            description: '',
            choose: [],
            problemPicPath: '',
            solutionPicPath: '',
            isManualJudge: false,
            type: 0,
            typeList: [
                {
                    value: 1,
                    label: '选择题'
                },
                {
                    value: 2,
                    label: '判断题'
                },
                {
                    value: 3,
                    label: '填空题'
                },
                {
                    value: 4,
                    label: '简答题'
                },
                {
                    value: 5,
                    label: '编程题'
                }
            ]
        },
        methods: {
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
            },
            submit:function () {
                //如果是选择题,将选项内容封装到 desc 中
                if (this.type === 1) {
                    for (let i = 0; i < this.choose.length; i++) {
                        this.description += this.choose[i] + '\n';
                    }
                }
                $.ajax({
                    type: 'POST',
                    url: '${pageContext.request.contextPath}/teacher/exercise/problem',
                    data: {
                        title:this.title,
                        description:this.description,
                        solution:this.solution,
                        type:this.type,
                        isManualJudge:this.isManualJudge,
                        problemPicPath:this.problemPicPath,
                        solutionPicPath:this.solutionPicPath,
                        sectionId:this.sectionId,
                        createUserId:this.teacherId,
                        courseId:this.courseId
                    },
                    dataType: 'JSON',
                    success: function (data) {
                        if (data === true) {
                            this.$Modal.success({
                                title: '添加成功',
                                content: '题目添加成功!'
                            });
                        } else {
                            this.$Modal.error({
                                title: '添加失败',
                                content: '请重新添加'
                            });
                        }
                    }.bind(this),
                    error:function (err) {
                        this.$Modal.error({
                            title: '添加失败',
                            content: '请重新添加!'
                        });
                        console.log(err);
                    }
                });

            },
            handleProblemPic: function (res) {
                if (res !== "error") {
                    this.problemPicPath = res;
                }
            },
            handleSolutionPic: function (res) {
                if (res !== "error") {
                    this.solutionPicPath = res;
                }
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
        el: "#teacher-problem",
        data: {
            isActive: true
        }
    })
</script>
</body>
</html>
