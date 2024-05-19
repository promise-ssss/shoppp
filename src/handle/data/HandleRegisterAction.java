package handle.data;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import save.data.Register;

import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class HandleRegisterAction extends ActionSupport {

    private String logname;
    private String password;
    private String again_password;
    private String phone;
    private String address;
    private String realname;

    public String execute() throws Exception {
        request.setCharacterEncoding("utf-8");
        Connection con = null;
        PreparedStatement sql = null;
        Register userBean = new Register();
        request.setAttribute("userBean", userBean);

        if (logname == null)
            logname = "";
        if (password == null)
            password = "";

        if (!password.equals(again_password)) {
            userBean.setBackNews("两次密码不同，注册失败");
            return INPUT;
        }

        boolean isLD = true;
        for (int i = 0; i < logname.length(); i++) {
            char c = logname.charAt(i);
            if (!(Character.isLetterOrDigit(c) || c == '_'))
                isLD = false;
        }

        boolean boo = logname.length() > 0 && password.length() > 0 && isLD;
        String backNews = "";

        try {
            Context context = new InitialContext();
            Context contextNeeded = (Context) context.lookup("java:comp/env");
            DataSource ds = (DataSource) contextNeeded.lookup("mobileConn");
            con = ds.getConnection();

            String insertCondition = "INSERT INTO user VALUES (?,?,?,?,?)";
            sql = con.prepareStatement(insertCondition);

            if (boo) {
                sql.setString(1, logname);
                sql.setString(2, password);
                sql.setString(3, phone);
                sql.setString(4, address);
                sql.setString(5, realname);
                int m = sql.executeUpdate();

                if (m != 0) {
                    backNews = "注册成功";
                    userBean.setBackNews(backNews);
                    userBean.setLogname(logname);
                    userBean.setPhone(phone);
                    userBean.setAddress(address);
                    userBean.setRealname(realname);
                    return SUCCESS;
                }
            } else {
                backNews = "信息填写不完整或名字中有非法字符";
                userBean.setBackNews(backNews);
                return INPUT;
            }
        } catch (SQLException exp) {
            backNews = "该会员名已被使用，请您更换名字" + exp;
            userBean.setBackNews(backNews);
            return INPUT;
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ee) {
            }
        }
        return SUCCESS;
    }

}
