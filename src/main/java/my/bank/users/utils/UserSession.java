package my.bank.users.utils;

import jakarta.servlet.http.HttpSession;
import my.bank.users.models.User;


public class UserSession {

    //private HttpSession session;

    /*public UserSession(HttpSession session){
        this.session = session;
    }

    public User get(){
        if(this.session.getAttribute("user") != null){
            return (User) this.session.getAttribute("user");
        }
        return null;
    }
    */
    public static User get(HttpSession session){
        if(session.getAttribute("user") != null){
            return (User) session.getAttribute("user");
        }
        return null;
    }
}
