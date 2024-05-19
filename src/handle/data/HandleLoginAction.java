package handle.data;

import save.data.Login;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


    public class HandleLoginAction extends ActionSupport {

        private String logname;
        private String password;
        
        public String execute() throws Exception {

            boolean boo = (logname.length() > 0) && (password.length() > 0);

            try {
                Context context = new InitialContext();
                Context contextNeeded = (Context) context.lookup("java:comp/env");
                DataSource ds = (DataSource) contextNeeded.lookup("mobileConn");
                try (Connection con = ds.getConnection(); Statement sql = con.createStatement()) {

                    ResultSet rs = sql.executeQuery(condition);
                    boolean m = rs.next();
                    if (m) {
                        // 登录成功
                        success(logname, password);
                        return SUCCESS;
                    } else {
                        // 登录失败
                        String backNews = "您输入的用户名不存在，或密码不匹配";
                        fail(logname, backNews);
                        return INPUT;
                    }
                }
            } catch (Exception e) {
                String backNews = "" + e;
                fail(logname, backNews);
                return INPUT;
            }
        }

        private void success(String logname, String password) {
            Login loginBean = getLoginBeanFromSession();
            String name = loginBean.getLogname();
            if (name.equals(logname)) {
                loginBean.setBackNews(logname + "已经登录了");
                loginBean.setLogname(logname);
                loginBean.setPassword(password);
            } else {
                loginBean.setBackNews(logname + "登录成功");
                loginBean.setLogname(logname);
                loginBean.setPassword(password);
            }
        }

        private void fail(String logname, String backNews) {
            Login loginBean = getLoginBeanFromSession();
            loginBean.setBackNews(backNews);
            loginBean.setLogname(logname);
            loginBean.setPassword("");
        }

        private Login getLoginBeanFromSession() {
            HttpSession session = request.getSession(true);
            Login loginBean = (Login) session.getAttribute("loginBean");
            if (loginBean == null) {
                loginBean = new Login();
                session.setAttribute("loginBean", loginBean);
                loginBean = (Login) session.getAttribute("loginBean");
            }
            return loginBean;
        }
        
        public String getLogname() {
            return logname;
        }

        public void setLogname(String logname) {
            this.logname = logname;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }