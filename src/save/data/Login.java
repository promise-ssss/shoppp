package save.data;
import java.util.*;
@SuppressWarnings("unused")
public class Login {
    String logname="",
           password="",
           backNews="δ��¼";
    public void setLogname(String logname){  
       this.logname = logname;
    }
    public String getLogname(){
       return logname;
    }
    public void setBackNews(String s) {
       backNews = s;
    } 
    public String getBackNews(){
       return backNews;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
