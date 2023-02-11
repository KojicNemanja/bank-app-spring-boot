package my.bank.controllers;

import freemarker.template.TemplateException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import my.bank.freemarker.Template;
import my.bank.users.employees.dao.UserDAO;
import my.bank.users.models.User;
import my.bank.users.status.UserStatus;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class Login {

    @GetMapping("/login")
    @ResponseBody
    String login(@RequestParam(name = "Login", required = false) String login_error) throws TemplateException, IOException {
        if(login_error != null){
            HashMap<String, Object> model_data = new HashMap<>();
            model_data.put("Login", login_error);
            return Template.render("login", model_data);
        }
        return Template.render("login");
    }

    @PostMapping("/login")
    @ResponseBody
    void submit(HttpSession session,
                HttpServletResponse response,
                User data_form) throws TemplateException, IOException {
        String username = data_form.getUsername();
        User user = new UserDAO().getForUsername(username);
        if(user != null && user.getUser_status() != UserStatus.BANNED){
            String password = DigestUtils.sha256Hex(data_form.getPassword());
            if(user.getPassword().equals(password)) {
                session.setAttribute("user", user);
                response.sendRedirect("/");
                return;
            }
        }
        response.sendRedirect("/login?Login=false");
    }
}
