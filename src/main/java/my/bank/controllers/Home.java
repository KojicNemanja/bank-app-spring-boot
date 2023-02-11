package my.bank.controllers;

import freemarker.template.TemplateException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import my.bank.users.models.User;
import my.bank.users.status.UserStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class Home {

    @GetMapping("/")
    @ResponseBody
    public void index(HttpSession session, HttpServletResponse response) throws IOException, TemplateException {
        User user = (User) session.getAttribute("user");
        if(user.getUser_status().equals(UserStatus.EMPLOYEE)){
            response.sendRedirect("/employee");
        }else{
            response.sendRedirect("/client");
        }
    }
}
