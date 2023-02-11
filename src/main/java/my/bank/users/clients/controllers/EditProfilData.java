package my.bank.users.clients.controllers;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import my.bank.users.clients.freemarker.ClientTemplate;
import my.bank.users.employees.dao.UserDAO;
import my.bank.users.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class EditProfilData {

    @GetMapping("/client/edit")
    @ResponseBody
    public String edit_profil(HttpSession session){
        User user = (User) session.getAttribute("user");
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("user", user);
        return ClientTemplate.render("edit_data", model_data);
    }

    @PostMapping("/client/edit")
    public void edit(User form, String re_password,
                     HttpServletResponse response, HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
        if (form.getPassword().equals(re_password)){
            UserDAO userDAO = new UserDAO();
            user.setUsername(form.getUsername());
            user.setPassword(form.getPassword());
            if(userDAO.transactional_edit(user) > 0){
                response.sendRedirect("/client?Edit=true");
                return;
            }
        }
        response.sendRedirect("/client?Edit=false");
    }
}
