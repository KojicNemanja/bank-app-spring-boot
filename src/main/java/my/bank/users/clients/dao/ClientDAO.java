package my.bank.users.clients.dao;

import my.bank.database.DBHandler;
import my.bank.users.clients.account.generate.CreateAccNumber;
import my.bank.users.clients.account.model.Account;
import my.bank.users.clients.account.model.AccountStatus;
import my.bank.users.clients.models.Client;
import my.bank.users.employees.dao.AccountDAO;
import my.bank.users.employees.dao.AddressDAO;
import my.bank.users.employees.dao.PersonDAO;
import my.bank.users.employees.dao.UserDAO;
import my.bank.users.models.Address;
import my.bank.users.models.User;
import my.bank.users.status.UserStatus;
import my.bank.users.transaction.dao.TransactionDAO;
import my.bank.users.transaction.models.Transaction;
import org.apache.commons.codec.digest.DigestUtils;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Statement;

public class ClientDAO {
    private DBHandler dbHandler = null;

    public ClientDAO(){}

    private Client getForQuery(String query){
        Client client = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            dbHandler = new DBHandler();
            st = dbHandler.getConn().createStatement();
            rs = st.executeQuery(query);
            if(rs.next()){
                client = new Client(rs);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{rs.close(); st.close();}catch (Exception ex){}
            dbHandler.close_connection();
        }
        return client;
    }

    public int save(User user_data, Address address_data){
        int result = 0;
        int generted_id = -1;
        try {
            dbHandler = new DBHandler();
            dbHandler.disable_auto_commit();
            // I
            //save person and get id
            Client client = new Client();
            client.setFirst_name(user_data.getFirst_name());
            client.setLast_name(user_data.getLast_name());
            client.setJmbg(user_data.getJmbg());
            client.setPhone_number(user_data.getPhone_number());

            generted_id = new PersonDAO().save(client);

            if(generted_id > 0){
                client.setId(generted_id);
                // II
                // save address
                Address address = new Address();
                address.setUser_id(client.getId());
                address.setStreet_name(address_data.getStreet_name());
                address.setStreet_number(address_data.getStreet_number());
                address.setCity(address_data.getCity());
                client.setAddress(address);

                result = new AddressDAO().save(address);
                if(result > 0){
                    // III
                    //save user
                    client.setUsername(user_data.getUsername());
                    String hash_pass = DigestUtils.sha256Hex(user_data.getPassword());
                    client.setPassword(hash_pass);
                    client.setUser_status(UserStatus.CLIENT);

                    result = new UserDAO().save(client);
                    if(result > 0){
                        // IV
                        //create account
                        //save account
                        CreateAccNumber accNumber = new CreateAccNumber(client.getId());
                        Account account = new Account(
                                client.getId(),
                                accNumber.getAcc_number(),
                                new BigDecimal(0.0),
                                AccountStatus.ACTIVE
                        );
                        client.setAccount(account);

                        result = new AccountDAO().save(account);
                        if(result > 0){
                            //commit all
                            dbHandler.commit();
                        }
                    }
                }
            }
        }catch (Exception ex){
            dbHandler.roll_back();
            ex.printStackTrace();
            return 0;
        }finally {
            dbHandler.close_connection();
        }
        if(result > 0){
            return generted_id;
        }
        return -1;
    }

    public int edit(Client client){
        int result = 0;
        try {
            dbHandler = new DBHandler();
            dbHandler.disable_auto_commit();

            PersonDAO personDAO = new PersonDAO();
            AddressDAO addressDAO = new AddressDAO();
            AccountDAO accountDAO = new AccountDAO();
            UserDAO userDAO = new UserDAO();

            result = personDAO.edit(client);
            result *= addressDAO.edit(client.getAddress());
            result *= accountDAO.edit(client.getAccount());
            result *= userDAO.edit(client);

            if(result > 0) {
                dbHandler.commit();
            }
        }catch (Exception ex){
            dbHandler.roll_back();
            ex.printStackTrace();
        }finally {
            dbHandler.close_connection();
        }

        return result;
    }

    public Client getForId(int client_id){
        String query = String.format("""
                    SELECT p.id, p.first_name, p.last_name, p.jmbg, p.phone_number, a.street_name, a.street_number, a.city,
                    u.username, u.password, u.status as user_status, ac.number, ac.balance, ac.status as acc_status
                    FROM person as p, address as a, users as u, account as ac
                    WHERE (a.person_id = p.id) AND (u.person_id = p.id) AND (ac.user_id = p.id)
                    AND(p.id = %d)""", client_id);
        return getForQuery(query);
    }

    public Client getForAccNumber(String acc_number){
        String query = String.format("""
                    SELECT p.id, p.first_name, p.last_name, p.jmbg, p.phone_number, a.street_name, a.street_number, a.city,
                    u.username, u.password, u.status as user_status, ac.number, ac.balance, ac.status as acc_status
                    FROM person as p, address as a, users as u, account as ac
                    WHERE (a.person_id = p.id) AND (u.person_id = p.id) AND (ac.user_id = p.id)
                    AND(ac.number = '%s')""", acc_number);
        return getForQuery(query);
    }

    public Client getForJMBG(String jmbg){
        String query = String.format("""
                    SELECT p.id, p.first_name, p.last_name, p.jmbg, p.phone_number, a.street_name, a.street_number, a.city,
                    u.username, u.password, u.status as user_status, ac.number, ac.balance, ac.status as acc_status
                    FROM person as p, address as a, users as u, account as ac
                    WHERE (a.person_id = p.id) AND (u.person_id = p.id) AND (ac.user_id = p.id)
                    AND(p.jmbg = '%s')""", jmbg);
        return getForQuery(query);
    }

    public int payment(Transaction transaction, Client client){
        int result = 0;
        try{
            dbHandler = new DBHandler();
            dbHandler.disable_auto_commit();

            TransactionDAO transactionDAO = new TransactionDAO();
            AccountDAO accountDAO = new AccountDAO();

            result = transactionDAO.save(transaction);
            result *= accountDAO.edit(client.getAccount());
            if(result > 0) {
                dbHandler.commit();
            }
        }catch (Exception ex){
            dbHandler.roll_back();
            ex.printStackTrace();
        }finally {
            dbHandler.close_connection();
        }
        return result;
    }

    public int send_money(Client payer, Client payee,
                          Transaction payer_transaction, Transaction payee_transaction){
        int result = 0;
        try{
            dbHandler = new DBHandler();
            dbHandler.disable_auto_commit();

            TransactionDAO transactionDAO = new TransactionDAO();
            AccountDAO accountDAO = new AccountDAO();

            result = transactionDAO.save(payer_transaction);
            result *= transactionDAO.save(payee_transaction);

            result *= accountDAO.edit(payer.getAccount());
            result *= accountDAO.edit(payee.getAccount());
            if(result > 0){
                dbHandler.commit();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            dbHandler.close_connection();
        }
        return result;
    }
}
