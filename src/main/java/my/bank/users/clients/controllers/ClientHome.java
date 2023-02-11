package my.bank.users.clients.controllers;

import jakarta.servlet.http.HttpSession;
import my.bank.users.clients.dao.ClientDAO;
import my.bank.users.clients.freemarker.ClientTemplate;
import my.bank.users.clients.models.Client;
import my.bank.users.models.User;
import my.bank.users.transaction.dao.TransactionDAO;
import my.bank.users.transaction.models.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class ClientHome {

    @GetMapping("/client")
    @ResponseBody
    public String client_home(HttpSession session,
                              @RequestParam(value = "Payment", required = false) String pay_status,
                              @RequestParam(value = "Edit", required = false) String edit_status){
        User user = (User) session.getAttribute("user");
        HashMap<String, Object> model_data = new HashMap<>();
        Client client = new ClientDAO().getForId(user.getId());
        model_data.put("client", client);
        ArrayList<Transaction> transactions = new TransactionDAO().get_all(client.getId());
        model_data.put("transactions", transactions);
        if(pay_status != null){
            model_data.put("Payment", pay_status);
        }
        if(edit_status != null){
            model_data.put("Edit", edit_status);
        }
        return ClientTemplate.render("home", model_data);
    }
}
