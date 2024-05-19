package 商品管理;

import save.data.Login;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddGoodAction {

    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            String ISBN = request.getParameter("ISBN");
            String name = request.getParameter("name");
            String price = request.getParameter("price");
            String publish = request.getParameter("publish");
            String date = request.getParameter("date");
            String id = request.getParameter("id");
            Connection con = null;
            PreparedStatement pre = null;
            Login loginBean = getSessionLoginBean(request);

            try {
                validateLoginBean(loginBean, response);
            } catch (Exception exp) {
                response.sendRedirect("root.jsp");
                return;
            }

            try {
                Context context = new InitialContext();
                Context contextNeeded = (Context) context.lookup("java:comp/env");
                DataSource ds = (DataSource) contextNeeded.lookup("mobileConn");
                con = ds.getConnection();
                String addSQL = "insert into mobileform values(?,?,?,?,?,null,?,null)";
                pre = con.prepareStatement(addSQL);
                pre.setString(1, ISBN);
                pre.setString(2, name);
                pre.setString(3, price);
                pre.setString(4, publish);
                pre.setString(5, date);
                pre.setString(6, id);
                pre.executeUpdate();
                con.close();

                //将用户名传入deleteSuccess.jsp页面
                request.setAttribute("username", ISBN);
                //跳转到提示页面
                request.getRequestDispatcher("addSuccess.jsp").forward(request, response);
            } catch (SQLException e) {
                response.getWriter().print("" + e);
            } catch (NamingException exp) {
                response.getWriter().print("" + exp);
            } finally {
                try {
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception ee) {
                }
            }
        } catch (IOException | ServletException ex) {
            ex.printStackTrace();
        }
    }

    private Login getSessionLoginBean(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        Login loginBean = (Login) session.getAttribute("loginBean");
        if (loginBean == null) {
            loginBean = new Login();
            session.setAttribute("loginBean", loginBean);
        }
        return loginBean;
    }

    private void validateLoginBean(Login loginBean, HttpServletResponse response) throws IOException {
        if (loginBean == null || loginBean.getLogname() == null || loginBean.getLogname().length() == 0) {
            response.sendRedirect("root.jsp");
            throw new IOException("Invalid loginBean");
        }
    }
}
