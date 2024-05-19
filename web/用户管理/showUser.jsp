<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="save.data.Login" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="java.sql.*" %>

<jsp:useBean id="loginBean" class="save.data.Login" scope="session"/>

<HEAD>
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
     out.print("<th>真实姓名");
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
       out.print("<tr>");
       out.print("<td>"+username+"</td>");
       out.print("<td>"+password+"</td>");
       out.print("<td>"+phone+"</td>");
       out.print("<td>"+addess+"</td>");
         out.print("<td>"+realname+"</td>");
       String shopping =
       "<a href ='deleteUserAction?username="+username+"'>删除该用户</a>";
       out.print("<td>"+shopping+"</td>"); 
       out.print("</tr>");
     } 
     out.print("</table>");
     out.print("<br>");
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
