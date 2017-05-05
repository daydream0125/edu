<%@ page pageEncoding="UTF-8" language="java" %>
<%@include file="../../static.jsp" %>
<html>
<head>
    <title>课程信息</title>
    <style>
        p {
            color: #0f0f0f;
            font-size: 20px
        }

        table {
            color: #0f0f0f;
            font-size: 20px
        }
    </style>
</head>
<body>
<%@include file="../../navigation.jsp" %>
<div id="container" class="container">
    <h1>{{course.courseName}}</h1>
    <p>简介:{{course.courseDescription}}
    </p>
    <sec:authorize access="hasRole('ROLE_TEACHER')">
        <i-button type="primary" size="large" @click="goToChapters">查看课程内容</i-button>
    </sec:authorize>
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
            </tr>
            </thead>
            <tbody>
            <tr v-for="clazz in clazzes">
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
                        <i-button type="ghost" @click="openRegister(clazz.classId)">点击开放</i-button>
                    </td>
                </sec:authorize>
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
            <i-option v-for="clazz in clazzes" :value="clazz.classId" >
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
                    <th><i-button @click="approveAll" type="primary">全部通过审核</i-button></th>
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
        <i-select  size="large" v-model="importClassId">
            <i-option v-for="clazz in clazzes" :value="clazz.classId" >
                {{clazz.className}}
            </i-option>
        </i-select>
        注意:上传的 Excel 需要保证第一列即为学号
        文件应添加后缀名(xls or xlsx), 以便后台选择特定方式解析
        <Upload type="drag" action="teacher/importStudent" :data="{'classId':importClassId}" name="excel" :on-success="handleSuccess">
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
<script>
    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    // 例子：
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
    Date.prototype.Format = function (fmt) {
        let o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "H+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (let k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    };
    new Vue({
        el: '#container',
        data: {
            courseId:${courseId},
            teacherId:${sessionScope.account.userId},
            course: {},
            clazzes: [],
            studentNum: 0,
            menu: '1',
            addClass: false,
            reviewRegister: false,
            registers: [],
            approveRegisters:[],
            className: '',
            startTime: '',
            endTime: '',
            examTime: '',
            scorePercent: '',
            isPublicRegister: false,
            uploadExcel:false,
            importClassId:0,
            returnData:[],
            showErrorList:false
        },
        methods: {
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
            openRegister: function (classId) {
                $.get("teacher/openRegister", {classId: classId}, function (data) {
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
            approve:function (id) {
                for (let i = 0; i < this.registers.length; i++) {
                    if (this.registers[i].classmateId === id) {
                        this.approveRegisters.push(id);
                        //删除该元素
                        this.registers.splice(i,1);
                        break;
                    }
                }
            },
            approveAll:function () {
                //for in 不能注入????
                for ( let i = 0; i < this.registers.length; i++) {
                    this.approveRegisters.push(this.registers[i].classmateId);
                }
                //清空 register
                this.registers = [];
            },
            submitRegisters:function () {
                if (this.approveRegisters.length !== 0) {
                    $.post("teacher/approveRegisters",{approveRegisters:this.approveRegisters},function (data) {
                        if (data === true) {
                            alert("审核成功!");
                        } else {
                            alert("审核失败!");
                        }
                    })
                }
            },
            handleSuccess:function (data) {
                if (data[0] === "success") {
                    if (data.length === 1) {
                        alert("上传成功!");
                    } else {
                        data.splice(0,1);
                        this.returnData = data;
                        this.showErrorList  = true;
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
    })
</script>
</body>
</html>