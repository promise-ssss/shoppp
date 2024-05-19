<%@ page contentType="text/html" %>
<%@ page pageEncoding = "utf-8" %>

<HEAD>
    <%@ include file="txt/vscode1.txt" %>    <!--引入顶栏-->
</HEAD>

<title>查询页面</title>
<style>
    #tom{
        font-family:宋体;font-size:26;color:black;
    }
</style>
<HTML><body background =image/11.jpg >    <!--引入名为11的图像-->
<div align="center">
    <br>
    <p id=tom>欢迎查询！</p>
    <p id=tom>查询时可以输入商品的编号，名称，价格区间或者种类。<br>
        商品名支持模糊查询哦。
    </p>
    <form action="searchByConditionAction" id =tom method="post" >  <!--交给searchByConditionServlet-->


        输入查询信息:<input type=text id=tom name="searchMess"><br>    <!--searchMess和servlet相连-->
        <input type =radio name="radio" id =tom value="ISBN"/>      <!--radio和servlet相连-->
        商品编号
        <input type =radio name="radio" id =tom value="good_name">   <!--radio和servlet相连-->
        商品名
        <input type =radio name="radio" value="good_price">   <!--radio和servlet相连-->
        商品价格区间
        <input type =radio name="radio" value="good_category">   <!--radio和servlet相连-->
        商品种类
        <br><input type=submit id =tom value="提交">
    </form>
</div></body></HTML>