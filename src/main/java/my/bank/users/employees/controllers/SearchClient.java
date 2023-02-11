package my.bank.users.employees.controllers;

import jakarta.servlet.http.HttpServletResponse;
import my.bank.users.clients.dao.ClientDAO;
import my.bank.users.clients.models.Client;
import my.bank.users.employees.freemrker.EmployeeTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class SearchClient {

    @GetMapping("/employee/search/{option}/{input_data}")
    @ResponseBody
    public String search(@PathVariable(name = "input_data") String input,
                         @PathVariable(name = "option") String option,
                         @RequestParam(value = "Payment", required = false) String payment_status,
                         @RequestParam(value = "EditClient", required = false) String edit_status,
                         @RequestParam(value = "ClientBan", required = false) String ban_status,
                         HttpServletResponse response) throws IOException {
        Client client;
        ClientDAO clientDAO = new ClientDAO();
        if(option.equals("acc")) {
            client = clientDAO.getForAccNumber(input);
        }else{
            client = clientDAO.getForJMBG(input);
        }
        if(client != null){
            HashMap<String, Object> model_data = new HashMap<>();
            model_data.put("client", client);
            if(payment_status != null){
                model_data.put("payment_mess", payment_status);
            }
            if(edit_status != null){
                model_data.put("edit_mess", edit_status);
            }
            if(ban_status != null){
                model_data.put("ban_status", ban_status);
            }
            return EmployeeTemplate.render("search_client", model_data);
        }
        return  "Invalid data!";
    }
}
