<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                    aria-expanded="false" aria-controls="navbar">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="${pageContext.request.contextPath}/">精准教育</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <sec:authorize access="hasRole('ROLE_TEACHER')">
                    <li id="teacher-course" :class="{active:isActive}">
                        <a href="${pageContext.request.contextPath}/teacher/course/courseList?teacherId=${sessionScope.account.userId}">课程管理</a>
                    </li>
                    <li id="teacher-problem" :class="{active:isActive}"><a href="${pageContext.request.contextPath}/teacher/exercise/problemManage">题库管理</a></li>
                    <li id="teacher-exercise" :class="{active:isActive}"><a href="${pageContext.request.contextPath}/teacher/exercise/exerciseManage">作业管理</a></li>
                    <li id="teacher-score" :class="{active:isActive}"><a href="${pageContext.request.contextPath}/teacher/score/scoreManage">成绩管理</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_STUDENT')">
                    <li id="student-course" :class="{active:isActive}">
                        <a href="${pageContext.request.contextPath}/student/courseManage">我的课程</a>
                    </li>
                    <li id="student-exercise" :class="{active:isActive}">
                        <a href="${pageContext.request.contextPath}/student/exerciseManage">我的作业</a>
                    </li>

                    <li id="student-score" :class="{active:isActive}">
                        <a href="${pageContext.request.contextPath}/student/score">我的成绩</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_USER')">
                    <li id="userInfo" :class="{active:isActive}"><a href="${pageContext.request.contextPath}/userInfo">个人信息</a></li>
                </sec:authorize>
                <li id="notice" :class="{active:isActive}">
                    <a href="${pageContext.request.contextPath}/notice">通知公告</a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <sec:authorize access="hasRole('ROLE_USER')">
                    <li><a href="${pageContext.request.contextPath}/userInfo"
                           class="btn btn-link">${sessionScope.account.name}</a></li>
                    <li><a href="${pageContext.request.contextPath}/spring_logout">注销</a></li>
                </sec:authorize>
                <sec:authorize access="not hasRole('ROLE_USER')">
                    <li ><a href="${pageContext.request.contextPath}/sign">登录</a></li>
                </sec:authorize>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</nav>
