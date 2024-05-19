package 购物车;


import save.data.Login;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteAction {

    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            String goodsId = request.getParameter("goodsId");
            Connection con = null;
            PreparedStatement pre = null;
            Login loginBean = getSessionLoginBean(request);

            try {
                validateLoginBean(loginBean, response);
            } catch (Exception exp) {
                response.sendRedirect("txt/login.jsp");
                return;
            }

            try {
                Context context = new InitialContext();
                Context contextNeeded = (Context) context.lookup("java:comp/env");
                DataSource ds = (DataSource) contextNeeded.lookup("mobileConn");
                con = ds.getConnection();

                String deleteSQL = "delete  from shoppingForm where goodsId=?";
                pre = con.prepareStatement(deleteSQL);
                pre.setString(1, goodsId);
                pre.executeUpdate();
                con.close();
                response.sendRedirect("lookShoppingCar.jsp");
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
        } catch (IOException ex) {
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
            response.sendRedirect("txt/login.jsp");
            throw new IOException("Invalid loginBean");
        }
    }
}