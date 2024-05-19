package 会员;


import save.data.Login;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.io.IOException;

public class ModifyUserAction {

    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            Login loginBean = getSessionLoginBean(request);
            String name = loginBean.getLogname();
            String password = loginBean.getPassword();
            String password1 = request.getParameter("password");
            String newpassword = request.getParameter("newpassword");
            Connection con = null;
            PreparedStatement pre = null;

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

                if (password.equals(password1)) {
                    System.out.println("原密码正确");
                } else {
                    System.out.println("原密码错误");
                    response.sendRedirect("index6-userUpdate.jsp");
                    return;
                }

                String updateCondition = "update user set password = ? where logname = ?";
                pre = con.prepareStatement(updateCondition);
                pre.setString(1, newpassword);
                pre.setString(2, name);
                int n1 = pre.executeUpdate();

                if (n1 == 1) {
                    System.out.println("修改成功");
                    loginBean.setPassword(password);
                    response.sendRedirect("index5-login.jsp");
                    session.invalidate();
                    loginBean.setBackNews("修改成功，请重新登录");
                } else {
                    System.out.println("修改失败");
                    response.sendRedirect("index6-userUpdate.jsp");
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    closeResources(pre, con);
                } catch (Exception ee) {
                    ee.printStackTrace();
                }
            }
        } catch (Exception ex) {
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

    private void closeResources(PreparedStatement pre, Connection con) throws Exception {
        if (pre != null) {
            pre.close();
        }
        if (con != null) {
            con.close();
        }
    }
}