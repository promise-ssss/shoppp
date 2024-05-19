<%@ page import="save.data.Login" %>
<%@ page import="java.sql.*" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page contentType="text/html" %>
<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<HEAD>
    <%@ include file="txt/vscode1.txt" %> <!--引入顶栏-->
</HEAD>

<title>查看购物车</title>

<style>
    #tom {
        font-family: 微软雅黑;
        font-size: 26;
        color: black
    }
</style>

<HTML>
<body background=image/11.jpg> <!--引入名为11的图像-->

<s:if test="%{#session.loginBean == null || #session.loginBean.logname == null || #session.loginBean.logname.length() == 0}">
    <s:redirect action="login" namespace="/txt"/>
</s:if>

<div align="center">
    <s:bean name="dataSource" var="ds" scope="request" />
    <s:bean name="loginBean" var="loginBean" scope="request" />

    <s:set name="#request" var="con" value="%{#ds.connection}" />
    <s:set name="#request" var="sql" value="%{#con.createStatement()}" />
    <s:set name="#request" var="rs" value="%{#sql.executeQuery('SELECT goodsId, goodsName, goodsPrice, goodsAmount FROM shoppingForm WHERE logname = \'' + #loginBean.logname + '\'')}" />

    <!-- 打印表头信息 -->
    <table border="1" align="center">
        <tr>
            <th id="tom" width="120">商品ID</th>
            <th id="tom" width="120">商品名</th>
            <th id="tom" width="120">商品价格</th>
            <th id="tom" width="120">购买数量</th>
            <th id="tom" width="50">修改数量</th>
            <th id="tom" width="50">删除图书</th>
        </tr>

        <s:iterator value="#rs" var="row">
            <tr>
                <td id="tom"><s:property value="#row.goodsId"/></td>
                <td id="tom"><s:property value="#row.goodsName"/></td>
                <td id="tom"><s:property value="#row.goodsPrice"/></td>
                <td id="tom"><s:property value="#row.goodsAmount"/></td>
                <td id="tom">
                    <form action="updateAction" method="post">
                        <input type="text" id="tom" name="update" size="3" value="<s:property value='#row.goodsAmount'/>"/>
                        <input type="hidden" name="goodsId" value="<s:property value='#row.goodsId'/>"/>
                        <input type="submit" id="tom" value="更新数量"/>
                    </form>
                </td>
                <td id="tom">
                    <form action="deleteAction" method="post">
                        <input type="hidden" name="goodsId" value="<s:property value='#row.goodsId'/>"/>
                        <input type="submit" id="tom" value="删除该商品"/>
                    </form>
                </td>
            </tr>
        </s:iterator>
    </table>

    <form action="buyAction" method="post">
        <input type="hidden" name="logname" value="<s:property value='#loginBean.logname'/>"/>
        <input type="submit" id="tom" value="生成订单(同时清空购物车)"/>
    </form>
</div>
</body>
</HTML>
