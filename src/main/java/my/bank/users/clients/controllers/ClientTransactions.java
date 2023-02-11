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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class ClientTransactions {

    @GetMapping("/client/transactions")
    @ResponseBody
    public String transactions(HttpSession session){
        User user = (User) session.getAttribute("user");
        ClientDAO clientDAO = new ClientDAO();
        TransactionDAO transactionDAO = new TransactionDAO();
        Client client = clientDAO.getForId(user.getId());
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("client", client);
        ArrayList<Transaction> client_transactions = transactionDAO.get_all(user.getId());
        model_data.put("transactions", client_transactions);
        return ClientTemplate.render("client_transactions", model_data);
    }
}
