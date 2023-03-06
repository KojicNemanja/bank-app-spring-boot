package my.bank.users.clients.controllers;

import freemarker.template.TemplateException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import my.bank.freemarker.Template;
import my.bank.users.clients.dao.ClientDAO;
import my.bank.users.clients.models.Client;
import my.bank.users.clients.payment.ClientSend;
import my.bank.users.models.User;
import my.bank.users.transaction.models.Transaction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;


@Controller
public class SendMoney {

    @GetMapping("/client/send")
    @ResponseBody
    public String send(HttpSession session) throws TemplateException, IOException {
        User user = (User) session.getAttribute("user");
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.getForId(user.getId());
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("client", client);
        return Template.render("/client/send_money", model_data);
    }

    @PostMapping("/client/send")
    public void send(Transaction data,
                     HttpSession session, HttpServletResponse response) throws IOException {
        User user = (User)  session.getAttribute("user");
        Client payer = new ClientDAO().getForId(user.getId());
        if(payer != null) {
            if (ClientSend.send(payer, data) > 0) {
                response.sendRedirect("/client?Payment=true");
            } else {
                response.sendRedirect("/client?Payment=false");
            }
        }else{
            response.sendRedirect("/client?Payment=false");
        }
    }
}
