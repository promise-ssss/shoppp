<%@ page contentType="text/html" pageEncoding="utf-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="s" %>

<s:useBean id="userBean" class="save.data.Register" scope="request"/>

<html>
<head>
    <title>注册页面</title>
    <style>
        .footer {
            position: absolute;
            background-color: rgb(96, 125, 139);
            left: 50%;
            transform: translateX(-50%);
            width: 100%;
            height: 60px;
            bottom: 0px;
        }

        .footer2 {
            text-align: center;
            color: rgba(255, 255, 255, 0.8);
            font-size: 20px;
        }

        #ok {
            font-family: 微软雅黑;
            font-size: 26;
            color: black;
        }

        #yes {
            font-family: 微软雅黑;
            font-size: 18;
            color: black;
        }
    </style>
</head>

<body background="css/1.jpg">
<div align="center">
    <s:form action="registerAction" method="post">
        <br>
        <p id="ok">小黄人商店</p>
        <p id="ok">欢迎您注册</p>
        <table id="ok">
            <tr>
                <td>用户名称:</td>
                <td><s:textfield id="ok" name="logname"/></td>
                <td>用户密码:</td>
                <td><s:password id="ok" name="password"/></td>
            </tr>
            <tr>
                <td>重复密码:</td>
                <td><s:password id="ok" name="again_password"/></td>
                <td>联系电话:</td>
                <td><s:textfield id="ok" name="phone"/></td>
            </tr>
            <tr>
                <td>邮寄地址:</td>
                <td><s:textfield id="ok" name="address"/></td>
                <td>真实姓名:</td>
                <td><s:textfield id="ok" name="realname"/></td>
            </tr>
        </table>
        <br><hr>
        <s:submit id="ok" value="提交"/>
        <hr>
    </s:form>
</div>

<div align="center">
    注册反馈：
    <s:property value="#userBean.backNews"/>

    <!--底栏-->
    <footer class="footer">
        <p class="footer2">作者：<a target="_blank" href="https://xxxy.sie.edu.cn">21软件1班</a></p>
    </footer>
</div>
</body>
</html>
