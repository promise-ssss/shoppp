<%--
  Created by IntelliJ IDEA.
  User: 永恒的誓言
  Date: 2023/7/8
  Time: 23:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html" %>
<%@ page pageEncoding = "utf-8" %>
<%@ page import="save.data.Login" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="java.sql.*" %>


<jsp:useBean id="loginBean" class="save.data.Login" scope="session"/>

<HEAD>
    <%@ include file="vscode1.txt" %>   <!--引入顶栏-->
</HEAD>

<title>用户详情</title>

<style>
    #tom{
        font-family:宋体;font-size:26;color:black
    }
</style>

<HTML>
<center>
    <body background =image/11.jpg >    <!--引入名为11的图像-->
        <%  try{
         loginBean = (Login)session.getAttribute("loginBean");
         if(loginBean==null){
           response.sendRedirect("root.jsp");//重定向到登录页面.
           return;
         }
         else {
           boolean b =loginBean.getLogname()==null||
                   loginBean.getLogname().length()==0;
           if(b){
              response.sendRedirect("root.jsp");//重定向到登录页面。
              return;
           }
         }
      }
      catch(Exception exp){
           response.sendRedirect("root.jsp");//重定向到登录页面。
           return;
      }

   Context context = new InitialContext();
   Context contextNeeded = (Context)context.lookup("java:comp/env");
   DataSource ds = (DataSource)contextNeeded.lookup("mobileConn");//获得连接池。
   Connection con = null;
   Statement sql;
   ResultSet rs;
   try{
     con= ds.getConnection();//使用连接池中的连接。
     sql=con.createStatement();
     String query="SELECT * FROM user ";
     rs=sql.executeQuery(query);
     out.print("<table id=tom border=1 align=center>");
     out.print("<tr>");
     out.print("<th>用户名");
     out.print("<th>密码");
     out.print("<th>电话");
     out.print("<th>地址");
     out.print("<th>删除该用户<th>");
     out.print("</tr>");
     String picture="background.jpg";
     String detailMess="";
     while(rs.next()){
       String username=rs.getString(1);
       String password=rs.getString(2);
       String phone=rs.getString(3);
       String addess=rs.getString(4);
       String realname=rs.getString(5);
       detailMess=rs.getString(5);
       picture=rs.getString(6);
       out.print("<tr>");
       out.print("<td>"+username+"</td>");
       out.print("<td>"+password+"</td>");
       out.print("<td>"+phone+"</td>");
       out.print("<td>"+addess+"</td>");
         out.print("<td>"+realname+"</td>");
       String shopping =
       "<a href ='putGoodsServlet?username="+username+"'>添加到购物车</a>";
       out.print("<td>"+shopping+"</td>");
       out.print("</tr>");
     }
     out.print("</table>");
     out.print("<br>");
     out.print("<p id=tom>产品详情:</p>");
     out.println("<div align=center id=tom>"+"出版日期："+detailMess+"<div>");
     String pic ="<img src='image/"+picture+"' width=260 height=350 ></img>";
     out.print(pic); //产片图片
     con.close(); //连接返回连接池。
  }
  catch(SQLException exp){}
  finally{
     try{
          con.close();
     }
     catch(Exception ee){}
  }
%>
</center>
</body></HTML>

