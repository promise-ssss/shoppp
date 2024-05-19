<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="s" %>

<HEAD>
    <%@ include file="txt/vscode1.txt" %>   <!--引入顶栏-->
</HEAD>

<title>分页浏览页面</title>

<style>
    #tom {
        font-family: 宋体;
        font-size: 26;
        color: black
    }
</style>

<s:useAction name="showDetailAction" var="detailAction" />

<HTML>
<body background="image/11.jpg"> <!-- 引入11.jpg -->
<s:form action="showDetailAction" method="post">
    <center>
        <table id="tom" border="1" align="center">
            <%  String [][] table = detailAction.getDataBean().getTableRecord();
                if(table == null) {
                    out.print("没有记录");
                    return;
                }
            %>
            <tr>
                <th>商品ID</th>
                <th>商品名</th>
                <th>(图书)出版社</th>
                <th>商品价格</th>
                <th>查看细节</th>
                <td>添加到购物车</td>
            </tr>
            <%  int totalRecord = table.length;
                int pageSize = detailAction.getDataBean().getPageSize();  //每页显示的记录数。
                int totalPages = detailAction.getDataBean().getTotalPages();
                if(totalRecord % pageSize == 0)
                    totalPages = totalRecord / pageSize;//总页数。
                else
                    totalPages = totalRecord / pageSize + 1;
                detailAction.getDataBean().setPageSize(pageSize);
                detailAction.getDataBean().setTotalPages(totalPages);
                if(totalPages >= 1) {
                    if(detailAction.getDataBean().getCurrentPage() < 1)
                        detailAction.getDataBean().setCurrentPage(detailAction.getDataBean().getTotalPages());
                    if(detailAction.getDataBean().getCurrentPage() > detailAction.getDataBean().getTotalPages())
                        detailAction.getDataBean().setCurrentPage(1);
                    int index = (detailAction.getDataBean().getCurrentPage() - 1) * pageSize;
                    int start = index;  //table的currentPage页起始位置。
                    for(int i = index; i < pageSize + index; i++) {
                        if(i == totalRecord)
                            break;
                        out.print("<tr>");
                        for(int j = 0; j < table[0].length; j++) {
                            out.print("<td>"+table[i][j]+"</td>");
                        }
                        String detail =
                                "<s:a action='showDetailAction' namespace='/'> <s:param name='ISBN' value='" + table[i][0] + "' /> 查看详情 </s:a>";
                        out.print("<td>"+detail+"</td>");
                        String shopping =
                                "<a href ='putGoodsAction?ISBN=" + table[i][0] + "'>添加到购物车</a>";
                        out.print("<td>"+shopping+"</td>");
                        out.print("</tr>");
                    }
                }
            %>
        </table>
        <center>
            <div style="text-align: center;">
                <p id="tom">全部记录数:<s:property value="dataBean.totalRecords"/>。
                    <br>每页最多显示<s:property value="dataBean.pageSize"/>
                    条记录。
                    <br>当前显示第<s:property value="dataBean.currentPage"/>页
                    (共有<s:property value="dataBean.totalPages"/>页)。</p>
                <table id="tom">
                    <tr>
                        <td><s:submit action="showDetailAction" method="post" value="上一页">
                            <s:param name="currentPage" value="<%=detailAction.getDataBean().getCurrentPage()-1 %>"/>
                        </s:submit></td>
                        <td><s:submit action="showDetailAction" method="post" value="下一页">
                            <s:param name="currentPage" value="<%=detailAction.getDataBean().getCurrentPage()+1 %>"/>
                        </s:submit></td>
                        <td><s:submit action="showDetailAction" method="post" value="提交">
                            <s:param name="currentPage" value="<%=detailAction.getDataBean().getCurrentPage() %>"/>
                        </s:submit></td>
                    </tr>
                    <tr>
                        <td></td><td></td>
                        <td><s:form action="showDetailAction" method="post">
                            每页显示<s:textfield name="pageSize" value="<%=detailAction.getDataBean().getPageSize()%>" size="1"/>
                            条记录<s:submit value="确定"/>
                        </s:form></td>
                    </tr>
                </table>
            </div>
        </center>
    </center>
</s:form>
</body>
</HTML>
