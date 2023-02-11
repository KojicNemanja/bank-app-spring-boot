package my.bank.users.clients.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import my.bank.users.models.User;
import my.bank.users.status.UserStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class ClientInterceptorHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user == null){
            response.sendRedirect("/login");
            return false;
        }
        if(user.getUser_status() != UserStatus.CLIENT){
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
