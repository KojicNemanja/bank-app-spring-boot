package my.bank.users.employees.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import my.bank.users.employees.freemrker.EmployeeTemplate;
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
    public String employee(HttpSession session, HttpServletResponse response) throws IOException {
        User user = (User) session.getAttribute("user");
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("user", user);
        return EmployeeTemplate.render("home", model_data);
    }
}
