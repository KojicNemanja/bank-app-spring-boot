package my.bank.users.clients.models;

import my.bank.users.clients.account.model.Account;
import my.bank.users.models.Address;
import my.bank.users.models.User;
import my.bank.users.status.UserStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Client extends User {
    private Account account;

    public Client(){
        super();
        account = new Account();
    }

    public Client(Integer id, String first_name, String last_name, String jmbg, String phone_number, Address address, String username,
                  String password, UserStatus status, Account account) {
        super(id, first_name, last_name, jmbg, phone_number, address, username, password, status);
        this.account = account;
    }

    public Client(ResultSet rs) throws SQLException {
        setId(rs.getInt("id"));
        setFirst_name(rs.getString("first_name"));
        setLast_name(rs.getString("last_name"));
        setJmbg(rs.getString("jmbg"));
        setPhone_number(rs.getString("phone_number"));
        setAddress(new Address(rs));
        setUsername(rs.getString("username"));
        setPassword(rs.getString("password"));
        String user_status = rs.getString("user_status");
        if(user_status.equals("CLIENT")){
            setUser_status(UserStatus.CLIENT);
        }else{
            setUser_status(UserStatus.BANNED);
        }
        setAccount(new Account(rs));
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
