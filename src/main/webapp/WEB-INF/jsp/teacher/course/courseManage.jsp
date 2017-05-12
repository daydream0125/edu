<%@ page pageEncoding="UTF-8" language="java" %>
<%@include file="../../static.jsp" %>
<html>
<head>
    <title>课程信息</title>
    <style>

    </style>
</head>
<body>
<%@include file="../../navigation.jsp" %>
<div id="container" class="container">
    <div style="color: #004772;font-weight: bold">
        <span>当前位置：</span>
        <a href="">首页</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="teacher/courseList">课程管理</a>
        <span class=>&nbsp;| &nbsp;</span>
        <a href="javascript:">班级管理</a>
        <hr>
    </div>
    <div style="float: right;margin-right: 100px">
        <sec:authorize access="hasRole('ROLE_TEACHER')">
            <i-button type="primary" size="large" @click="goToChapters">查看课程内容</i-button>
        </sec:authorize>
    </div>
    <div style="margin-top: 20px">
        <h1>{{course.courseName}}</h1>
        <br>
        <p>简介:{{course.courseDescription}}
        </p>
    </div>


    <br>
    <div id="menu">
        <i-menu mode="horizontal" theme="light" active-name="1" @on-select="changeMenu">
            <Row>
                <i-col span="5">
                    <Menu-item name="1">
                        <p>课程信息</p>
                    </Menu-item>
                </i-col>
                <i-col span="5">
                    <Menu-item name="2">
                        <p>班级信息</p>
                    </Menu-item>
                </i-col>
            </Row>
        </i-menu>
        <br>
    </div>
    <div id="courseInfo" v-if="menu==1">
        <table class="table">
            <thead>
            <tr>
                <th>开始时间</th>
                <th>考试时间</th>
                <th>考试类型</th>
                <th>是否结课</th>
                <th>当前学习人数</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>{{course.startTime}}</td>
                <td>{{course.examTime}}</td>
                <td>
                    <Tag type="border" color="blue">{{course.examType | formatExamType}}</Tag>
                </td>
                <td>
                    <Tag type="border" color="blue">{{course.isFinish | formatIsFinish}}</Tag>
                </td>
                <td>
                    <Tag type="border" color="blue">{{studentNum}}</Tag>
                </td>
            </tr>
            </tbody>
        </table>
    </div>
    <div id="classInfo" v-if="menu==2">
        <i-button type="primary" size="large" @click="addClass = true">增设班级</i-button>
        <i-button type="primary" size="large" @click="reviewRegister = true">审核注册学生</i-button>
        <i-button type="primary" size="large" @click="uploadExcel = true">导入学生名单</i-button>
        <table class="table hover">
            <thead>
            <tr>
                <th>班级</th>
                <th>创建时间</th>
                <th>状态</th>
                <th>注册</th>
                <th>结课</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="(clazz,index) in clazzes">
                <td>{{clazz.className}}</td>
                <td>{{clazz.createTime}}</td>
                <td v-if="clazz.isFinish">
                    <Tag type="border" color="yellow">已结束</Tag>
                </td>
                <td v-else>
                    <Tag type="border" color="green">正在进行</Tag>
                </td>
                <sec:authorize access="hasRole('ROLE_TEACHER')">
                    <td v-if="clazz.isPublicRegister">
                        <Tag type="border" color="green">已开放</Tag>
                    </td>
                    <td v-else>
                        <i-button type="primary" @click="openRegister(index)">点击开放</i-button>
                    </td>
                </sec:authorize>
                <td v-if="clazz.isFinish">
                    <Tag type="border" color="green">已结课</Tag>
                </td>
                <td v-else>
                    <i-button type="success" size="large" @click="finish(index)">结课并计算总成绩</i-button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <%--增设班级--%>
    <Modal v-model="addClass" :closable="false" :mask-closable="false" width="800">
        <i-form>
            <Form-item label="课程">
                <i-input disabled :placeholder="course.courseName"></i-input>
            </Form-item>
            <Form-item label="班级名称">
                <i-input v-model="className" required></i-input>
            </Form-item>
            <Form-item label="开始时间">
                <Date-picker type="date" placeholder="选择日期" v-model="startTime"></Date-picker>
            </Form-item>
            <Form-item label="结束时间">
                <Date-picker type="date" placeholder="选择日期" v-model="endTime"></Date-picker>
            </Form-item>
            <Form-item label=" 考试时间">
                <Date-picker type="date" placeholder="选择日期" v-model="examTime"></Date-picker>
            </Form-item>
            <Form-item label="平时成绩占比">
                <i-select v-model="scorePercent">
                    <i-option v-for="n in 10" :value="n">
                        {{n}}成
                    </i-option>
                </i-select>
            </Form-item>
            <Form-item label="开放注册">
                <i-switch size="large" v-model="isPublicRegister">
                    <span slot="open">开启</span>
                    <span slot="close">关闭</span>
                </i-switch>
            </Form-item>
            <Form-item>
                <i-button type="primary" size="large" @click="submit">提交</i-button>
            </Form-item>
        </i-form>
    </Modal>
    <%--审核注册学生--%>
    <Modal v-model="reviewRegister" :closable="false" :mask-closable="false" width="800" @on-ok="submitRegisters">
        <p>选择班级</p>
        <i-select @on-change="showRegisters" size="large">
            <i-option v-for="clazz in clazzes" :value="clazz.classId">
                {{clazz.className}}
            </i-option>
        </i-select>
        <table class="table">
            <thead>
            <tr>
                <th>id</th>
                <th>姓名</th>
                <th>学号</th>
                <th>操作</th>
                <th>
                    <i-button @click="approveAll" type="primary">全部通过审核</i-button>
                </th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="c in registers">
                <td>{{c.account.userId}}</td>
                <td>{{c.account.name}}</td>
                <td>{{c.account.userInfo.cardNumber}}</td>
                <td>
                    <i-button type="primary" @click="approve(c.classmateId)">通过审核</i-button>
                </td>
            </tr>
            </tbody>
        </table>
        点击下方确认按钮后,上述操作才能生效
    </Modal>
    <Modal v-model="uploadExcel" :closable="false" :mask-closable="false" width="500">
        <p>选择班级</p>
        <i-select size="large" v-model="importClassId">
            <i-option v-for="clazz in clazzes" :value="clazz.classId">
                {{clazz.className}}
            </i-option>
        </i-select>
        注意:上传的 Excel 需要保证第一列即为学号
        文件应添加后缀名(xls or xlsx), 以便后台选择特定方式解析
        <Upload type="drag" action="teacher/importStudent" :data="{'classId':importClassId}" name="excel"
                :on-success="handleSuccess">
            <div style="padding: 20px 0">
                <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
                <p>点击或将文件拖拽到这里上传</p>
            </div>
        </Upload>
    </Modal>
    <Modal v-model="showErrorList">
        <p>失败名单</p>
        <p v-for="s in returnData">{{s}}</p>
        <p> 失败原因:学号未注册</p>
    </Modal>
</div>

<%@include file="../../js.jsp" %>
<script src="static/dateFormat.js"></script>
<script>
    new Vue({
        el: '#container',
        data: {
            courseId:${courseId},
            teacherId:<sec:authentication property="principal.username" />,
            course: {},
            clazzes: [],
            studentNum: 0,
            menu: '1',
            addClass: false,
            reviewRegister: false,
            registers: [],
            approveRegisters: [],
            className: '',
            startTime: '',
            endTime: '',
            examTime: '',
            scorePercent: '',
            isPublicRegister: false,
            uploadExcel: false,
            importClassId: 0,
            returnData: [],
            showErrorList: false
        },
        methods: {
            finishClass: function (index) {
                $.get("teacher/finishClass/" + this.clazzes[index].classId, function (data) {
                    if (data === false) {
                        alert("结课失败，请重试或联系管理员")
                    } else {
                        alert("结课成功");
                    }
                }.bind(this))
            },
            finish: function (index) {
                $.get("teacher/existsUnMarkExercise/" + this.clazzes[index].classId, function (data) {
                    if (data === true) {
                        alert("当前课程存在未批改作业，请先将作业批改完成后尝试。")
                    } else {
                        $.get("teacher/existFinalExercise/" + this.clazzes[index].classId, function (data) {
                            if (data === true) {
                                this.finishClass(index);
                            } else {
                                alert("当前课程中不存在最后考试，无法结课");
                            }
                        }.bind(this));
                    }
                }.bind(this));
            },
            getCourseInfo: function (courseId) {
                $.get("course/info/" + courseId, function (data) {
                    this.course = data;
                }.bind(this))
            },
            getStudentNum: function (courseId) {
                $.get("course/" + courseId + "/studentNum", function (data) {
                    this.studentNum = data;
                }.bind(this))
            },
            getClazzesByCourseId: function (courseId) {
                $.get("course/" + courseId + "/clazzes", function (data) {
                    this.clazzes = data;
                }.bind(this))
            },
            goToChapters: function () {
                //跳转至课程章节信息
                window.location.href = "course/" + this.courseId + "/courseChapterInfo";
            },
            openRegister: function (index) {
                $.get("teacher/openRegister", {classId: this.clazzes[index].classId}, function (data) {
                    if (data === true) {
                        alert('开放注册成功!');
                    }
                })
            },
            submit: function () {
                this.startTime = new Date(this.startTime).Format("yyyy-MM-dd");
                this.endTime = new Date(this.startTime).Format("yyyy-MM-dd");
                this.examTime = new Date(this.startTime).Format("yyyy-MM-dd");
                $.post("teacher/class", {
                    className: this.className, startTime: this.startTime, endTime: this.endTime,
                    examTime: this.examTime, scorePercent: this.scorePercent, isPublicRegister: this.isPublicRegister,
                    courseId: this.courseId, teacherId: this.teacherId
                }, function (data) {
                    if (data === true) {
                        alert("添加成功");
                        this.addClass = false;
                    } else {
                        alert("添加失败");
                    }
                })
            },
            changeMenu: function (name) {
                this.menu = name;
            },
            showRegisters: function (classId) {
                $.get("teacher/class/" + classId + "/registers", function (data) {
                    this.registers = data;
                }.bind(this));
            },
            approve: function (id) {
                for (let i = 0; i < this.registers.length; i++) {
                    if (this.registers[i].classmateId === id) {
                        this.approveRegisters.push(id);
                        //删除该元素
                        this.registers.splice(i, 1);
                        break;
                    }
                }
            },
            approveAll: function () {
                //for in 不能注入????
                for (let i = 0; i < this.registers.length; i++) {
                    this.approveRegisters.push(this.registers[i].classmateId);
                }
                //清空 register
                this.registers = [];
            },
            submitRegisters: function () {
                if (this.approveRegisters.length !== 0) {
                    $.post("teacher/approveRegisters", {approveRegisters: this.approveRegisters}, function (data) {
                        if (data === true) {
                            alert("审核成功!");
                        } else {
                            alert("审核失败!");
                        }
                    })
                }
            },
            handleSuccess: function (data) {
                if (data[0] === "success") {
                    if (data.length === 1) {
                        alert("上传成功!");
                    } else {
                        data.splice(0, 1);
                        this.returnData = data;
                        this.showErrorList = true;
                    }

                } else {
                    alert(data[1]);
                    this.returnData = [];
                }
            }
        },
        mounted: function () {
            this.getCourseInfo(this.courseId);
            this.getStudentNum(this.courseId);
            this.getClazzesByCourseId(this.courseId);
        },
        filters: {
            formatExamType: function (type) {
                switch (type) {
                    case 1:
                        return '提交论文';
                    case 2:
                        return '开卷考试';
                    case 3:
                        return '闭卷考试';
                    case 4:
                        return '开卷机试';
                    case 5:
                        return '闭卷机试';
                }
            },
            formatIsFinish: function (flag) {
                return flag === true ? '已结课' : '正在进行';
            }
        }
    });
    new Vue({
        el: '#teacher-course',
        data: {
            isActive: true
        }
    })
</script>
</body>
</html>