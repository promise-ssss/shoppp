package handle.data;

import com.opensymphony.xwork2.ActionSupport;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class HandleQueryAction extends ActionSupport {

    private ResultSet resultSet;

    public ResultSet getResultSet() {
        return resultSet;
    }

    public String execute() {
        Connection con = null;
        Statement sql;
        try {
            Context context = new InitialContext();
            Context contextNeeded = (Context) context.lookup("java:comp/env");
            DataSource ds = (DataSource) contextNeeded.lookup("mobileConn"); // ������ӳء�
            con = ds.getConnection(); // ʹ�����ӳ��е����ӡ�
            sql = con.createStatement();
            // ��ȡmobileClassify����÷��ࣺ
            resultSet = sql.executeQuery("SELECT * FROM mobileclassify");
            return SUCCESS;
        } catch (Exception e) {
            addActionError(e.getMessage());
            return ERROR;
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (Exception ee) {
            }
        }
    }
}
