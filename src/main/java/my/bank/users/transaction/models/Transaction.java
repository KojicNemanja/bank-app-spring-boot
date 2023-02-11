package my.bank.users.transaction.models;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction {
    private Integer user_id;
    private String payer, payee;
    private BigDecimal amount;
    private String account;
    private String date;
    private TransactionType type;

    public Transaction(){
        setPayer("");
        setPayee("");
        setAmount(new BigDecimal(0.0));
        setAccount("0000000");
        setDate("");
        setType(null);
    }

    public Transaction(Integer user_id, String payer, String payee, BigDecimal amount, String account, String date, TransactionType type) {
        this.user_id = user_id;
        this.payer = payer;
        this.payee = payee;
        this.amount = amount;
        this.account = account;
        this.date = date;
        this.type = type;
    }

    public Transaction(ResultSet rs) throws SQLException {
        setUser_id(rs.getInt("user_id"));
        setPayer(rs.getString("payer"));
        setPayee(rs.getString("payee"));
        setAmount(rs.getBigDecimal("amount"));
        setDate(rs.getString("date"));
        String type;
        if(rs.getString("type").equals("DEPOSIT")){
            setType(TransactionType.DEPOSIT);
        }else{
            setType(TransactionType.WITHDRAW);
        }
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
