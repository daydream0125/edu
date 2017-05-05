<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="../static.jsp" %>
    <title>Title</title>
    <style>
        .demo-spin-col{
            height: 100px;
            position: relative;
            border: 1px solid #eee;
        }
        .demo-spin-icon-load{
            animation: ani-demo-spin 1s linear infinite;
        }
    </style>
    <link href="static/editor/css/wangEditor.min.css" type="text/css" rel="stylesheet">
</head>
<body>
<%@include file="../navigation.jsp" %>
<div id="container" class="container">



</div>
<%@include file="../js.jsp"%>
<script type="text/javascript">
new Vue({
    el:'#container',
    data:{
        show:true,
        userId:'',
        password:'',
        info:''
    },
    methods:{
      submit:function () {
      }
    }

})
</script>
</body>
</html>
