package handle.data;

import save.data.Login;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserModify extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    public  void  service(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Login loginBean=null;
        HttpSession session=request.getSession(true);
        try{  loginBean=(Login)session.getAttribute("loginBean");
            if(loginBean==null){
                loginBean=new Login();  //创建新的数据模型 。
                session.setAttribute("loginBean",loginBean);
                loginBean=(Login)session.getAttribute("loginBean");
            }
        }
catch(Exception exp){
            loginBean=new Login();  //创建新的数据模型 。
            session.setAttribute("loginBean",loginBean);
        }
        String name = loginBean.getLogname();
        String password = loginBean.getPassword();
        String password1 = request.getParameter("password");
        String newpassword = request.getParameter("newpassword");
        Connection con=null;
        PreparedStatement pre=null; //预处理语句。
        try{
            loginBean = (Login)session.getAttribute("loginBean");
            if(loginBean==null){
                response.sendRedirect("root.jsp");//重定向到登录页面。
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
        try {
            Context context = new InitialContext();
            Context  contextNeeded=(Context)context.lookup("java:comp/env");
            DataSource ds=
                    (DataSource)contextNeeded.lookup("mobileConn");//获得连接池。
            con = ds.getConnection();//使用连接池中的连接。
            //将用户输入的原密码与数据库中的密码进行比较，如果密码正确且两次输入的新密码一致，则修改密码
            if(password.equals(password1) ){
                System.out.println("原密码正确");
            }
            else{
                System.out.println("原密码错误");
                response.sendRedirect("index6-userUpdate.jsp");
            }
            String updateCondition = "update user set password = ? where logname = ?";
            pre = con.prepareStatement(updateCondition);
            pre.setString(1, newpassword);
            pre.setString(2, name);
            int n1 = pre.executeUpdate();
            if(n1==1){
                System.out.println("修改成功");
                loginBean.setPassword(password);
                //跳转到登录页面
                response.sendRedirect("index5-login.jsp");
                //退出登录
                session.invalidate();
                loginBean.setBackNews("修改成功,请重新登录");
            }
            else{
                System.out.println("修改失败");
                response.sendRedirect("index6-userUpdate.jsp");
            }
            response.sendRedirect("index6-userUpdate.jsp");
        }
        catch(Exception e){
            e.printStackTrace();
        }
}
}
