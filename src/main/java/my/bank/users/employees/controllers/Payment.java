package my.bank.users.employees.controllers;

import jakarta.servlet.http.HttpServletResponse;
import my.bank.users.clients.dao.ClientDAO;
import my.bank.users.clients.models.Client;
import my.bank.users.employees.freemrker.EmployeeTemplate;
import my.bank.users.employees.payment.ClientDeposit;
import my.bank.users.employees.payment.ClientWithdraw;
import my.bank.users.transaction.models.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class Payment {

    @GetMapping("/employee/payment/{client_acc}")
    @ResponseBody
    public String payment(@PathVariable(value = "client_acc") String acc){
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.getForAccNumber(acc);
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("client", client);
        return EmployeeTemplate.render("payment", model_data);
    }

    @PostMapping("/employee/payment/{payment_type}/{client_acc}")
    public void payment(Transaction transaction_form,
                        HttpServletResponse response,
                        @PathVariable(value = "payment_type") String payment_type,
                        @PathVariable(value = "client_acc") String client_acc) throws IOException {

        int result = 0;

        if (payment_type.equals("deposit")) {
            result = ClientDeposit.deposit(transaction_form);
        } else {
            result = ClientWithdraw.withdraw(transaction_form);
        }
        String path_redirect = String.format("/employee/search/acc/%s", client_acc);
        if (result > 0) {
            response.sendRedirect(path_redirect + "?Payment=true");
        } else {
            response.sendRedirect(path_redirect + "?Payment=false");
        }
    }
}
