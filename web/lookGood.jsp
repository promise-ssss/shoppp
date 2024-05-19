<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<title>浏览商品页面</title>

<style>
  #tom {
    font-family: 宋体;
    font-size: 26;
    color: black
  }
</style>

<HTML>
<body background="image/11.jpg"> <!--引入11.jpg-->
<div align="center">
  <br>

  <p id="tom">选择商品类别！</p>

  <s:form action="queryGoodsAction" method="post">
    <s:select id="tom" name="fenleiNumber" list="categories" listKey="id" listValue="mobileCategory"/>
    <s:submit id="tom" value="提交"/>
  </s:form>

</div>
</body>
</HTML>
