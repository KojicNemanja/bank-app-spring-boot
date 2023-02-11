package my.bank.users.employees.dao;

import my.bank.database.DBHandler;
import my.bank.users.clients.account.model.Account;
import my.bank.users.transaction.models.Transaction;
import my.bank.users.transaction.models.TransactionType;

import java.math.BigDecimal;
import java.sql.SQLException;

public class AccountDAO {
    private DBHandler dbHandler = null;

    public AccountDAO(){}

    public int save(Account account) throws SQLException {
        dbHandler = new DBHandler();
        String query = String.format("""
                INSERT INTO `account`(`user_id`, `number`, `balance`, `status`)
                VALUES (%d, '%s', %f, '%s');""", account.getUsers_id(), account.getAcc_number(),
                account.getBalance(), account.getAcc_status());
        return dbHandler.insert_update_delete(query);
    }

    public int save(Transaction transaction, Account account){
        try{
            BigDecimal client_amount = new BigDecimal(account.getBalance() + "");
            BigDecimal transaction_amount = new BigDecimal(transaction.getAmount() + "");
            BigDecimal new_amount;
            if(transaction.getType() == TransactionType.DEPOSIT) {
                new_amount = client_amount.add(transaction_amount);
            }else{
                new_amount = client_amount.subtract(transaction_amount);
            }
            String query = String.format("""
                    UPDATE `account` SET `balance`= %f
                    WHERE `user_id` = %d;""", new_amount, account.getUsers_id());
            dbHandler = new DBHandler();
            return dbHandler.insert_update_delete(query);
        }catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }

    public int edit(Account account) throws SQLException {
        String query = String.format("""
                UPDATE `account` SET `number`='%s',`balance`=%f,`status`='%s'
                WHERE `user_id`=%d;""", account.getAcc_number(), account.getBalance(), account.getAcc_status(),
                account.getUsers_id());
        dbHandler = new DBHandler();
        return dbHandler.insert_update_delete(query);
    }

}
