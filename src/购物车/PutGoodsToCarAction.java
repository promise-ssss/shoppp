package ¹ºÎï³µ;

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
import java.sql.ResultSet;
import java.sql.SQLException;

public class PutGoodsToCarAction {

    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            Connection con = null;
            PreparedStatement pre = null;
            ResultSet rs;
            String ISBN = request.getParameter("ISBN");
            Login loginBean = getSessionLoginBean(request);

            try {
                validateLoginBean(loginBean, response);
            } catch (Exception exp) {
                response.sendRedirect("index5-login.jsp");
                return;
            }

            try {
                Context context = new InitialContext();
                Context contextNeeded = (Context) context.lookup("java:comp/env");
                DataSource ds = (DataSource) contextNeeded.lookup("mobileConn");
                con = ds.getConnection();

                String queryMobileForm = "select * from mobileform where ISBN =?";
                String queryShoppingForm = "select goodsAmount from shoppingform where goodsId =?";
                String updateSQL = "update shoppingform set goodsAmount =? where goodsId=?";
                String insertSQL = "insert into shoppingform values(?,?,?,?,?)";

                pre = con.prepareStatement(queryShoppingForm);
                pre.setString(1, ISBN);
                rs = pre.executeQuery();

                if (rs.next()) {
                    int amount = rs.getInt(1);
                    amount++;
                    pre = con.prepareStatement(updateSQL);
                    pre.setInt(1, amount);
                    pre.setString(2, ISBN);
                    pre.executeUpdate();
                } else {
                    pre = con.prepareStatement(queryMobileForm);
                    pre.setString(1, ISBN);
                    rs = pre.executeQuery();

                    if (rs.next()) {
                        pre = con.prepareStatement(insertSQL);
                        pre.setString(1, rs.getString("ISBN"));
                        pre.setString(2, loginBean.getLogname());
                        pre.setString(3, rs.getString("good_name"));
                        pre.setFloat(4, rs.getFloat("good_price"));
                        pre.setInt(5, 1);
                        pre.executeUpdate();
                    }
                }

                con.close();
                response.sendRedirect("lookShoppingCar.jsp");
            } catch (SQLException exp) {
                response.getWriter().print("" + exp);
            } catch (NamingException exp) {
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
            response.sendRedirect("index5-login.jsp");
            throw new IOException("Invalid loginBean");
        }
    }
}