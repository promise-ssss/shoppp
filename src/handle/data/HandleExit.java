package handle.data;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
@SuppressWarnings("serial")
public class HandleExit extends HttpServlet {   
   public void init(ServletConfig config) throws ServletException{
      super.init(config);
   }
   public  void  service(HttpServletRequest request,HttpServletResponse response) 
                        throws ServletException,IOException {
       HttpSession session=request.getSession(true); 
       session.invalidate();              //�����û���session����
       response.sendRedirect("index9-exit.jsp"); //������ҳ��
   }
}
