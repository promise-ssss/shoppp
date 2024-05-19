package handle.data;

import save.data.Login;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AdminLogin extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    public void service(HttpServletRequest request,
                        HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        Connection con =null;
        Statement sql;
        String logname=request.getParameter("username").trim(),
                password=request.getParameter("password").trim();

        boolean boo=(logname.length()>0)&&(password.length()>0);
        try{
            Context context = new InitialContext();
            Context  contextNeeded=(Context)context.lookup("java:comp/env");
            DataSource ds=
                    (DataSource)contextNeeded.lookup("mobileConn");//������ӳء�
            con= ds.getConnection();//ʹ�����ӳ��е����ӡ�
            String condition="select * from admin where logname = '"+logname+
                    "' and password ='"+password+"'";
            sql=con.createStatement();
            if(boo){
                ResultSet rs=sql.executeQuery(condition);
                boolean m=rs.next();
                if(m==true){
                    //���õ�¼�ɹ��ķ���:
                    success(request,response,logname,password);
                    RequestDispatcher dispatcher=
                            request.getRequestDispatcher("rootIndex.jsp");//ת��
                    dispatcher.forward(request,response);
                }
                else{
                    String backNews="��������û��������ڣ������벻����";
                    //���õ�¼ʧ�ܵķ���:
                    fail(request,response,logname,backNews);
                }
            }
            else{
                String backNews="�������û���������";
                fail(request,response,logname,backNews);
            }
            con.close();//���ӷ������ӳء�
        }
        catch(SQLException exp){
            String backNews=""+exp;
            fail(request,response,logname,backNews);
        }
        catch(NamingException exp){
            String backNews="û���������ӳ�"+exp;
            fail(request,response,logname,backNews);
        }
        finally{
            try{
                con.close();
            }
            catch(Exception ee){}
        }
    }
    public void success(HttpServletRequest request,
                        HttpServletResponse response,
                        String logname,String password) {
        Login loginBean=null;
        HttpSession session=request.getSession(true);
        try{  loginBean=(Login)session.getAttribute("loginBean");
            if(loginBean==null){
                loginBean=new Login();  //�����µ�����ģ�� ��
                session.setAttribute("loginBean",loginBean);
                loginBean=(Login)session.getAttribute("loginBean");
            }
            String name =loginBean.getLogname();
            if(name.equals(logname)) {
                loginBean.setBackNews(logname+"�Ѿ���¼��");
                loginBean.setLogname(logname);
            }
            else {  //����ģ�ʹ洢�µĵ�¼�û�:
                loginBean.setBackNews(logname+"��¼�ɹ�");
                loginBean.setLogname(logname);
            }
        }
        catch(Exception ee){
            loginBean=new Login();
            session.setAttribute("loginBean",loginBean);
            loginBean.setBackNews(ee.toString());
            loginBean.setLogname(logname);
        }
    }
    public void fail(HttpServletRequest request,
                     HttpServletResponse response,
                     String logname,String backNews) {
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter out=response.getWriter();
            out.println("<html><body>");
            out.println("<h2>"+logname+"��¼�������<br>"+backNews+"</h2>") ;
            out.println("���ص�¼ҳ��<br>");
            out.println("<a href =root.jsp>��¼ҳ��</a>");
            out.println("</body></html>");

        }
        catch(IOException exp){}
    }
}
