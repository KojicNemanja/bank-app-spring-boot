package my.bank.users.employees.controllers;

import jakarta.servlet.http.HttpServletResponse;
import my.bank.users.clients.account.model.Account;
import my.bank.users.clients.account.model.AccountStatus;
import my.bank.users.clients.dao.ClientDAO;
import my.bank.users.clients.models.Client;
import my.bank.users.employees.freemrker.EmployeeTemplate;
import my.bank.users.models.Address;
import my.bank.users.models.User;
import my.bank.users.status.UserStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;

@Controller
public class EditClient {


    @GetMapping("/employee/client/edit/{client_id}")
    @ResponseBody
    public String edit(@PathVariable(value = "client_id") int client_id){
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.getForId(client_id);
        HashMap<String, Object> model_data = new HashMap<>();
        model_data.put("client", client);
        return EmployeeTemplate.render("edit_client", model_data);
    }

    @PostMapping("/employee/client/edit/{client_id}")
    public void edit(@PathVariable(value = "client_id") int id,
                     User user_form_data,
                     Address address_form_data,
                     String client_status,
                     String account_status,
                     HttpServletResponse response) throws IOException {
        System.out.println(client_status);
        System.out.println(account_status);
        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.getForId(id);
        if(client != null){
            client.setFirst_name(user_form_data.getFirst_name());
            client.setLast_name(user_form_data.getLast_name());
            client.setJmbg(user_form_data.getJmbg());
            client.setPhone_number(user_form_data.getPhone_number());
            Address new_address = new Address(
                    client.getId(),
                    address_form_data.getStreet_name(),
                    address_form_data.getStreet_number(),
                    address_form_data.getCity()
            );
            client.setAddress(new_address);

            UserStatus userStatus = switch (client_status){
                case "client" -> UserStatus.CLIENT;
                default -> UserStatus.BANNED;
            };
            client.setUser_status(userStatus);

            AccountStatus accountStatus = switch (account_status){
                case "active" -> AccountStatus.ACTIVE;
                case "inactive" -> AccountStatus.INACTIVE;
                default -> AccountStatus.BLOCKED;
            };
            Account account = new Account(
                    client.getId(),
                    client.getAccount().getAcc_number(),
                    client.getAccount().getBalance(),
                    accountStatus
            );

            client.setAccount(account);
            String redirect_path = String.format("/employee/search/acc/%s", client.getAccount().getAcc_number());
            if(clientDAO.edit(client) > 0){
                response.sendRedirect(redirect_path + "?EditClient=true");
            }else{
                response.sendRedirect(redirect_path + "?EditClient=false");
            }
        }
    }
}
