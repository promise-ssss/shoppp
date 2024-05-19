<%--
  Created by IntelliJ IDEA.
  User: 永恒的誓言
  Date: 2023/7/10
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="loginBean" class="save.data.Login" scope="session"/>
<html>
<head>
    <title>修改用户信息</title>
</head>
<body  background =image/11.jpg>
<%  if(loginBean==null){
    response.sendRedirect("index5-login.jsp");//重定向到登录页面。
    return;
}
else {
    boolean b =loginBean.getLogname()==null||
            loginBean.getLogname().length()==0;
    if(b){
        response.sendRedirect("index5-login.jsp");//重定向到登录页面。
        return;
    }
}
%>
<h1>修改密码</h1>
<form action="userModifyAction" method="post">
    <table>
        <tr>
            <td>原密码：</td>
            <td><input type="password" name="password" ></td>
        </tr>
        <tr>
            <td>新密码：</td>
            <td><input type="password" name="newpassword" ></td>
        </tr>
        <tr>
            <td><input type="submit" value="修改"></td>
            <td><input type="reset" value="重置"></td>
        </tr>
    </table>

</body>
</html>
