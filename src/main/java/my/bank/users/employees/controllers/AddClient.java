package my.bank.users.employees.controllers;

import freemarker.template.TemplateException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import my.bank.freemarker.Template;
import my.bank.users.clients.dao.ClientDAO;
import my.bank.users.clients.models.Client;
import my.bank.users.models.Address;
import my.bank.users.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class AddClient {

    @GetMapping("/employee/add_client")
    @ResponseBody
    public String open_acc_input(HttpSession session,
                                 @RequestParam(name = "Save", required = false) String save
                                 ) throws IOException, TemplateException {
        if (save != null){
            HashMap<String, Object> model_data = new HashMap<>();
            model_data.put("Save", save);
            Client client = (Client) session.getAttribute("new_client");
            if(client != null){
                model_data.put("NewClient", client);
                session.removeAttribute("new_client");
            }
            return Template.render("/employee/add_client", model_data);
        }
        return Template.render("/employee/add_client");
    }

    @PostMapping("/employee/add_client")
    @ResponseBody
    public void open_acc_submit(User user_data,
                                Address address_data,
                                HttpServletResponse response,
                                HttpSession session) throws IOException {

        ClientDAO clientDAO = new ClientDAO();
        int client_id = clientDAO.save(user_data, address_data);
        if(client_id > 0){
            Client client = new ClientDAO().getForId(client_id);
            session.setAttribute("new_client", client);
            response.sendRedirect("/employee/add_client?Save=true");
        }else {
            response.sendRedirect("/employee/add_client?Save=false");
        }

    }
}
