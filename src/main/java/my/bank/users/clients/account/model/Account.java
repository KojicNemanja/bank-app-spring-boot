package my.bank.users.clients.account.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    private Integer users_id;
    private String acc_number;
    private BigDecimal balance;
    private AccountStatus acc_status;

    public Account(){
       /* users_id = null;
        acc_number = "Undefined";
        balance = new BigDecimal(0.0);
        acc_status = AccountStatus.INACTIVE;*/
    }

    public Account(Integer users_id, String acc_number, BigDecimal balance, AccountStatus status) {
        this.users_id = users_id;
        this.acc_number = acc_number;
        this.balance = balance;
        this.acc_status = status;
    }

    public Account(ResultSet rs) throws SQLException {
        setUsers_id(rs.getInt("id"));
        setAcc_number(rs.getString("number"));
        setBalance(rs.getBigDecimal("balance"));
        String acc_status = rs.getString("acc_status");
        if("ACTIVE".equals(acc_status)){
            setAcc_status(AccountStatus.ACTIVE);
        }else if("INACTIVE".equals(acc_status)){
            setAcc_status(AccountStatus.INACTIVE);
        }else{
            setAcc_status(AccountStatus.BLOCKED);
        }
    }

    public Integer getUsers_id() {
        return users_id;
    }

    public void setUsers_id(Integer users_id) {
        this.users_id = users_id;
    }

    public String getAcc_number() {
        return acc_number;
    }

    public void setAcc_number(String acc_number) {
        this.acc_number = acc_number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public AccountStatus getAcc_status() {
        return acc_status;
    }

    public void setAcc_status(AccountStatus acc_status) {
        this.acc_status = acc_status;
    }
}
