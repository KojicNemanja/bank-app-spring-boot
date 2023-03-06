package my.bank.users.clients.controllers;

import freemarker.template.TemplateException;
import jakarta.servlet.http.HttpSession;
import my.bank.freemarker.Template;
import my.bank.users.clients.dao.ClientDAO;
import my.bank.users.clients.models.Client;
import my.bank.users.models.User;
import my.bank.users.transaction.dao.TransactionDAO;
import my.bank.users.transaction.models.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@Controller
public class ClientTransactions {

    @GetMapping("/client/transactions")
    @ResponseBody
    public String transactions(HttpSession session) throws TemplateException, IOException {
        User user = (User) session.getAttribute("user");
        ClientDAO clientDAO = new ClientDAO();
        TransactionDAO transactionDAO = new TransactionDAO();
        Client client = clientDAO.getForId(user.getId());
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("client", client);
        ArrayList<Transaction> client_transactions = transactionDAO.get_all(user.getId());
        model_data.put("transactions", client_transactions);
        return Template.render("/client/client_transactions", model_data);
    }
}
