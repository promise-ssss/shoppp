<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="s" %>

<title>浏览商品页面</title>

<style>
   #tom {
      font-family: 宋体;
      font-size: 26;
      color: black
   }
</style>

<HTML>
<body background="image/11.jpg"> <!-- 引入11.jpg -->
<div align="center">
   <br>

   <p id="tom">选择你想要的商品，来进入商店吧！</p>
   <s:form action="queryAction" id="tom" method="post">
      <s:select id="tom" name="fenleiNumber" list="resultSet" listKey="id" listValue="mobileCategory"
                headerKey="" headerValue="-- 请选择 --"/>
      <s:submit id="tom" value="提交"/>
   </s:form>
</div>
</body>
</HTML>
