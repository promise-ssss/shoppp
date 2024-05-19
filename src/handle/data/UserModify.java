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
                loginBean=new Login();  //�����µ�����ģ�� ��
                session.setAttribute("loginBean",loginBean);
                loginBean=(Login)session.getAttribute("loginBean");
            }
        }
catch(Exception exp){
            loginBean=new Login();  //�����µ�����ģ�� ��
            session.setAttribute("loginBean",loginBean);
        }
        String name = loginBean.getLogname();
        String password = loginBean.getPassword();
        String password1 = request.getParameter("password");
        String newpassword = request.getParameter("newpassword");
        Connection con=null;
        PreparedStatement pre=null; //Ԥ������䡣
        try{
            loginBean = (Login)session.getAttribute("loginBean");
            if(loginBean==null){
                response.sendRedirect("root.jsp");//�ض��򵽵�¼ҳ�档
                return;
            }
            else {
                boolean b =loginBean.getLogname()==null||
                        loginBean.getLogname().length()==0;
                if(b){
                    response.sendRedirect("root.jsp");//�ض��򵽵�¼ҳ�档
                    return;
                }
            }
        }
        catch(Exception exp){
            response.sendRedirect("root.jsp");//�ض��򵽵�¼ҳ�档
            return;
        }
        try {
            Context context = new InitialContext();
            Context  contextNeeded=(Context)context.lookup("java:comp/env");
            DataSource ds=
                    (DataSource)contextNeeded.lookup("mobileConn");//������ӳء�
            con = ds.getConnection();//ʹ�����ӳ��е����ӡ�
            //���û������ԭ���������ݿ��е�������бȽϣ����������ȷ�����������������һ�£����޸�����
            if(password.equals(password1) ){
                System.out.println("ԭ������ȷ");
            }
            else{
                System.out.println("ԭ�������");
                response.sendRedirect("index6-userUpdate.jsp");
            }
            String updateCondition = "update user set password = ? where logname = ?";
            pre = con.prepareStatement(updateCondition);
            pre.setString(1, newpassword);
            pre.setString(2, name);
            int n1 = pre.executeUpdate();
            if(n1==1){
                System.out.println("�޸ĳɹ�");
                loginBean.setPassword(password);
                //��ת����¼ҳ��
                response.sendRedirect("index5-login.jsp");
                //�˳���¼
                session.invalidate();
                loginBean.setBackNews("�޸ĳɹ�,�����µ�¼");
            }
            else{
                System.out.println("�޸�ʧ��");
                response.sendRedirect("index6-userUpdate.jsp");
            }
            response.sendRedirect("index6-userUpdate.jsp");
        }
        catch(Exception e){
            e.printStackTrace();
        }
}
}
