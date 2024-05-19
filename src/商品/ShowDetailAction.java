import com.opensymphony.xwork2.ActionSupport;
import save.data.Login;

public class ShowDetailAction extends ActionSupport {

    private String ISBN;
    private Login loginBean;
    private String publishDate;
    private String productImage;

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Login getLoginBean() {
        return loginBean;
    }

    public void setLoginBean(Login loginBean) {
        this.loginBean = loginBean;
    }

    public String execute() {
        try {
            loginBean = (Login) session.get("loginBean");
            if (loginBean == null || loginBean.getLogname() == null || loginBean.getLogname().length() == 0) {
                return LOGIN; // �ض��򵽵�¼ҳ��
            }
        } catch (Exception exp) {
            return LOGIN; // �ض��򵽵�¼ҳ��
        }

        if (ISBN == null) {
            addActionError("û�в�Ʒ�ţ��޷��鿴ϸ��");
            return ERROR;
        }
        publishDate = "detailMess";
        productImage = "'image\"+picture+\"' width=260 height=350 ";
        return SUCCESS;
    }
}
