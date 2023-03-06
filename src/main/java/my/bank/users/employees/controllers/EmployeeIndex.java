package my.bank.users.employees.controllers;

import freemarker.template.TemplateException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import my.bank.freemarker.Template;
import my.bank.users.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class EmployeeIndex {

    @GetMapping("/employee")
    @ResponseBody
    public String employee(HttpSession session, HttpServletResponse response) throws IOException, TemplateException {
        User user = (User) session.getAttribute("user");
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("user", user);
        return Template.render("/employee/home", model_data);
    }
}
