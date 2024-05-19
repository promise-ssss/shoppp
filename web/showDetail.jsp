<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="s" %>

<HEAD>
    <%@ include file="txt/vscode1.txt" %>   <!--引入顶栏-->
</HEAD>

<title>商品详情</title>

<style>
    #tom{
        font-family: 宋体;
        font-size: 26;
        color: black
    }
</style>

<HTML>
<center>
    <body background="image/11.jpg">    <!--引入名为11的图像-->
    <s:useAction name="showDetailAction" var="detailAction" />

    <s:if test="detailAction.hasErrors()">
        <s:iterator value="detailAction.actionErrors">
            <s:property />
        </s:iterator>
    </s:if>

    <s:else>
        <s:if test="detailAction.ISBN != null">
            <table id="tom" border="1" align="center">
                <tr>
                    <th>商品ID</th>
                    <th>商品名</th>
                    <th>(图书)出版社</th>
                    <th>商品价格</th>
                    <th>放入购物车<th>
                </tr>
                <tr>
                    <td><s:property value="ISBN" /></td>
                    <td><s:property value="productName" /></td>
                    <td><s:property value="maker" /></td>
                    <td><s:property value="price" /></td>
                    <td><s:a action="putGoodsAction" namespace="/">
                        <s:param name="ISBN" value="%{ISBN}" />
                        添加到购物车
                    </s:a>
                    </td>
                </tr>
            </table>

            <br>
            <p id="tom">产品详情:</p>
            <div align="center" id="tom">出版日期：<s:property value="publishDate" /></div>
            <s:property value="productImage" />
        </s:if>
    </s:else>

    </body>
</center>
</HTML>
