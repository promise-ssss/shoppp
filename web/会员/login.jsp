<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="s" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="sb" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<s:useActionBean name="loginBean" var="loginBean" scope="session"/>

<!DOCTYPE html>
<html>
<head>
    <title>登录页面</title>
    <%@ include file="vscode1.txt" %>
    <link rel="stylesheet" href="css/style1.css"/>
</head>
<body background="css/1.jpg">

<div align="center">
    <br>
    <p id="ok">登录</p>
    <s:form action="loginAction" method="post">
        <table id="ok">
            <tr>
                <td>用户名称：</td>
                <td><s:textfield id="ok" name="logname" /></td>
            </tr>
            <tr>
                <td>用户密码：</td>
                <td><s:password id="ok" name="password" /></td>
            </tr>
        </table>
        <br><hr>
        <s:submit id="ok" value="提交"/>
        <hr>
    </s:form>
</div>

<div align="center">
    登录反馈信息:<br>
    <sb:write name="loginBean" property="backNews"/>
    <br>登录名称:<br><sb:write name="loginBean" property="logname"/>

    <!--底栏-->
    <footer class="footer">
        <p class="footer2">作者：<a target="_blank" href="https://xxxy.sie.edu.cn">21软件1班</a></p>
    </footer>
</div>

</body>
</html>