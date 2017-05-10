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
        <a href="javascript:">题库管理</a>
        <hr>
    </div>
    <div style="float: right;">
        <i-button type="primary" size="large" @click="goToAddProblem">
            新增题目
        </i-button>
        <i-button type="primary" size="large" @click="goToProblemList">
            查看题库
        </i-button>
    </div>
    <Row type="flex" justify="center">
        <i-col span="8">
            <i-input size="large" style="width: 500px;" placeholder="输入小节名称以查询" v-model="keywords">
                <i-button slot="append" icon="search" @click="search1">搜索</i-button>
            </i-input>
        </i-col>
    </Row>
    <br>
    <br>
    <div v-if="showSearchResult">
        <div>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>小节名称</th>
                    <th>题目数量</th>
                    <th>所属课程</th>
                    <th>查看题目</th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="(res,index) in currentResult">
                    <td>{{res[1]}}</td>
                    <td>{{problemCount[index]}}</td>
                    <td>{{res[2]}}</td>
                    <td>
                        <i-button type="primary" @click="viewProblem(res[0])">查看题目</i-button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div style="float: right">
            <Page :total="resultLen" @on-change="fetchData" show-total :page-size="pageSize" show-elevator>
            </Page>
        </div>
    </div>

</div>
<%@include file="../../js.jsp" %>
<script>
    new Vue({
        el: '#container',
        data: {
            search: false,
            showSearchResult: false,
            keywords: '',
            searchResult: [],
            problemCount: 0,
            resultLen: 0,
            currentResult: [],
            pageSize: 4
        },
        methods: {
            viewProblem: function (sectionId) {
                window.location.href = "teacher/viewProblem?sectionId=" + sectionId;
            },
            goToProblemList: function () {
                window.location.href = "teacher/problemList";
            },
            getProblemCount:function () {
                let chapterIds = this.currentResult.map(function (p) {
                    return p[0]
                });
                this.problemCount = [];
                $.get("teacher/problemCountByChapterId", {chapterIds: chapterIds}, function (data) {
                    this.problemCount = data;
                }.bind(this));
            },
            //todo 无限请求 重大 bug
            fetchData: function (page) {
                let index = (page - 1) * this.pageSize;
                this.currentResult = this.searchResult.slice(index, index + this.pageSize);
                this.getProblemCount();

            },
            search1: function () {
                $.get("teacher/searchProblems", {keywords: this.keywords}, function (data) {
                    this.searchResult = data;
                    this.resultLen = this.searchResult.length;
                    this.currentResult = this.searchResult.slice(0, this.pageSize);
                    this.getProblemCount();
                }.bind(this));
                this.showSearchResult = true;

            },
            goToAddProblem: function () {
                window.location.href = "teacher/addProblemPage";
            }
        }
    });

    let active = new Vue({
        el: "#teacher-problem",
        data: {
            isActive: true
        }
    })

</script>
</body>
</html>
