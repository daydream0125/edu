<%@ page pageEncoding="UTF-8" language="java" %>
<%@ include file="../../static.jsp"%>
<html>
<head>
    <title>
        成绩管理
    </title>
</head>
<body>
<%@include file="../../navigation.jsp"%>
<div class="container" id="container">
    <h1>这里是成绩管理</h1>
    <form enctype="multipart/form-data" method="post" id="img">
        <input type="file" name="pic" id="pic">
        <input type="hidden" name="title" value="123">
        <button @click="submit">submit</button>
    </form>

</div>
<%@include file="../../js.jsp"%>
<script>
    new Vue({
        el:'#container',
        data:{
            solution:'abc'
        },
        methods:{
          submit:function () {
              let formData = new FormData(document.getElementById('img'));
              $.ajax({
                  url:'${pageContext.request.contextPath}/testPic',
                  data:formData,
                  type:'post',
                  cache: false,
                  processData: false,
                  contentType: false,
                  success:function (data) {
                  }.bind(this),
                  error:function () {
                      alert("error");
                  }
              })
          }
        }
    })
</script>
</body>
</html>