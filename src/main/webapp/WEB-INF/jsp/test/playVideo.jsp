<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../static.jsp" %>
    <title>Title</title>
    <%
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    %>

    <base href="<%=basePath%>">
</head>
<body>
<%@include file="../navigation.jsp" %>
<div id="container" class="container">
    <button @click="video=true">play</button>
    <Modal v-model="video" title="普通的Modal对话框标题" width="720" height="420">
        <div style="text-align: center">

        <video width="520" height="420" controls="controls" >
            <source src="course-video/test.mp4" type="video/mp4"/>
        </video>
        </div>
    </Modal>
    <Upload type="drag" action="//jsonplaceholder.typicode.com/posts/">
        <div style="padding: 20px 0">
            <Icon type="ios-cloud-upload" size="52" style="color: #3399ff"></Icon>
            <p>点击或将文件拖拽到这里上传</p>
        </div>
    </Upload>
</div>

<script>
    new Vue({
        el: '#container',
        data: {
            video: false
        }
    })
</script>
</body>
</html>
