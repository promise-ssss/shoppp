package 订单;

import javax.servlet.http.*;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.*;
import java.io.*;
import java.sql.*;

public class SubmitOrderAction extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String orderInfo = request.getParameter("orderInfo");
        String logname = request.getParameter("logname");
        Connection con = null;
        Statement stmt = null;

        try {
            Context context = new InitialContext();
            Context contextNeeded = (Context) context.lookup("java:comp/env");
            DataSource ds = (DataSource) contextNeeded.lookup("mobileConn");
            con = ds.getConnection();
            String insertQuery = "INSERT INTO orderForm (logname, orderInfo) VALUES (?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(insertQuery)) {
                pstmt.setString(1, logname);
                pstmt.setString(2, orderInfo);
                pstmt.executeUpdate();
            }
            String selectQuery = "SELECT * FROM orderForm WHERE logname = ?";
            try (PreparedStatement pstmt = con.prepareStatement(selectQuery)) {
                pstmt.setString(1, logname);
                ResultSet rs = pstmt.executeQuery();

                out.println("<h2>Order Submitted Successfully</h2>");
                out.println("<table border=1>");
                out.println("<tr><th>订单序号</th><th>用户名称</th><th>订单信息</th></tr>");

                while (rs.next()) {
                    out.println("<tr>");
                    out.println("<td>" + rs.getString(1) + "</td>");
                    out.println("<td>" + rs.getString(2) + "</td>");
                    out.println("<td>" + rs.getString(3) + "</td>");
                    out.println("</tr>");
                }

                out.println("</table>");
            }

            String deleteQuery = "DELETE FROM orderForm WHERE logname = ?";
            try (PreparedStatement pstmt = con.prepareStatement(deleteQuery)) {
                pstmt.setString(1, logname);
                pstmt.executeUpdate();
            }

        } catch (SQLException | NamingException e) {
            out.println("<h1>Error: " + e.getMessage() + "</h1>");
        } finally {
            // Close the database connection
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                out.println("<h1>Error closing the database connection: " + e.getMessage() + "</h1>");
            }
        }
    }
}
