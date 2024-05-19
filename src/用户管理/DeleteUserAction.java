package 用户管理;

import save.data.Login;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteUserAction {

    public String deleteUser(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        Connection con = null;
        PreparedStatement pre = null;
        Login loginBean = null;
        HttpSession session = request.getSession(true);
        try {
            loginBean = (Login) session.getAttribute("loginBean");
            if (loginBean == null || loginBean.getLogname() == null || loginBean.getLogname().length() == 0) {
                return "root.jsp"; // Redirect to login page if not logged in
            }

            Context context = new InitialContext();
            Context contextNeeded = (Context) context.lookup("java:comp/env");
            DataSource ds = (DataSource) contextNeeded.lookup("mobileConn"); // Get connection pool
            con = ds.getConnection(); // Use connection from the pool

            String deleteSQL = "delete from user where logname=?";
            pre = con.prepareStatement(deleteSQL);
            pre.setString(1, username);
            pre.executeUpdate();

            con.close(); // Return connection to the pool

            // Set attribute for username and forward to success page
            request.setAttribute("username", username);
            return "deleteSuccess.jsp";
        } catch (SQLException | NamingException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
            return "errorPage.jsp"; // Redirect to an error page
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ee) {
                ee.printStackTrace(); // Handle or log the exception appropriately
            }
        }
    }
}
