

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import save.data.Record_Bean;

public class SearchByConditionAction {
    
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            HttpSession session = request.getSession(true);
            String searchMess = request.getParameter("searchMess");
            String radioMess = request.getParameter("radio");

            if (searchMess == null || searchMess.length() == 0) {
                response.getWriter().print("没有查询信息，无法查询");
                return;
            }

            Connection con = null;
            String queryCondition = "";
            float max = 0;
            float min = 0;

            if (radioMess.contains("ISBN")) {
                queryCondition = "SELECT ISBN, good_name, book_publish, good_price " +
                        "FROM mobileform WHERE ISBN='" + searchMess + "'";
            } else if (radioMess.contains("good_name")) {
                queryCondition = "SELECT ISBN, good_name, book_publish, good_price " +
                        "FROM mobileform WHERE good_name LIKE '%" + searchMess + "%'";
            } else if (radioMess.contains("good_price")) {
                String priceMess[] = searchMess.split("[-]+");
                try {
                    min = Float.parseFloat(priceMess[0]);
                    max = Float.parseFloat(priceMess[1]);
                } catch (NumberFormatException exp) {
                    min = 0;
                    max = 0;
                }
                queryCondition = "SELECT ISBN, good_name, book_publish, good_price " +
                        "FROM mobileform WHERE good_price BETWEEN " + min + " AND " + max;
            } else if (radioMess.contains("good_category")) {
                queryCondition = "SELECT ISBN, good_name, book_publish, good_price " +
                        "FROM mobileform WHERE good_category ='" + searchMess + "'";
            }

            Record_Bean dataBean = null;

            try {
                dataBean = (Record_Bean) session.getAttribute("dataBean");
                if (dataBean == null) {
                    dataBean = new Record_Bean(); // 创建bean。
                    session.setAttribute("dataBean", dataBean); // 是session bean。
                }
            } catch (Exception exp) {
            }

            try {
                Context context = new InitialContext();
                Context contextNeeded = (Context) context.lookup("java:comp/env");
                DataSource ds = (DataSource) contextNeeded.lookup("mobileConn"); // 获得连接池。
                con = ds.getConnection(); // 使用连接池中的连接。
                PreparedStatement sql = con.prepareStatement(queryCondition,
                        ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = sql.executeQuery();
                rs.last();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount(); // 得到结果集的列数。

                int rows = rs.getRow(); // 得到记录数。
                String[][] tableRecord = dataBean.getTableRecord();
                tableRecord = new String[rows][columnCount];
                rs.beforeFirst();
                int i = 0;
                while (rs.next()) {
                    for (int k = 0; k < columnCount; k++)
                        tableRecord[i][k] = rs.getString(k + 1);
                    i++;
                }
                dataBean.setTableRecord(tableRecord); // 更新bean。
                con.close(); // 连接返回连接池。
                response.sendRedirect("byPageShow.jsp"); // 重定向。
            } catch (Exception e) {
                response.getWriter().print("" + e);
            } finally {
                try {
                    con.close();
                } catch (Exception ee) {
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
