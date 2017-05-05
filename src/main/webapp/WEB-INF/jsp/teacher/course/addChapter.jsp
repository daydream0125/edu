<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="../../static.jsp" %>
<html>
<head>
    <title>增加课程章节信息</title>
    <style>
        p{
            font-size: 20px;
            font-weight: 800;
        }
    </style>
</head>
<body>
<%@include file="../../navigation.jsp" %>

<div class="container" id="container">
    <h1>增加课程章节信息</h1>
    <br>
    <i-form>
        <Form-item>
            <p>章序号(第几章)</p>
            <Input-number :max="40" :min="1" v-model="num" size="large"></Input-number>
        </Form-item>
        <Form-item>
            <p>章名称</p>
            <i-input type="text" v-model="title" size="large" placeholder="章名称"></i-input>
        </Form-item>
        <Form-item>
            <p> 章下节数</p>
            <Input-number :max="40" :min="1" v-model="sectionsNum" size="large"></Input-number>
        </Form-item>
        <Form-item>
            <p> 小节名称</p>
            <i-input type="text" v-model="sections[n-1]" v-for="n in sectionsNum" size="large"
                     placeholder="小节名称"><span slot="prepend">第{{n}}小节</span></i-input>
        </Form-item>
        <Form-item>
            <i-button size="large" type="primary" @click="submit">提交</i-button>
        </Form-item>
    </i-form>
</div> <!-- /container -->
<%@include file="../../js.jsp" %>

<script>
    const app = new Vue({
        el: '#container',
        data: {
            courseId:${courseId},
            num: 1,
            title: '',
            sections: [],
            sectionsNum: 1
        },
        methods: {
            submit: function () {
                $.post("teacher/chapter", {
                    num: this.num,
                    title: this.title,
                    sections: this.sections,
                    courseId: this.courseId
                }, function (data) {
                    if (data === true) {
                        alert("添加成功");
                    } else {
                        alert("添加失败,请重新添加");
                    }
                });
            }
        }
    });
    let active = new Vue({
        el: "#teacher-course",
        data: {
            isActive: true
        }
    })

</script>
</body>
</html>