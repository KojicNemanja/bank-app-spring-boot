package my.bank.users.transaction.dao;

import my.bank.database.DBHandler;
import my.bank.users.clients.models.Client;
import my.bank.users.transaction.models.Transaction;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class TransactionDAO {
    private DBHandler dbHandler = null;

    public int save(Transaction transaction){
        try {
            dbHandler = new DBHandler();
            String query = String.format("""
                            INSERT INTO `transactions`(`user_id`, `payer`, `payee`, `amount`, `date`, `type`)
                            VALUES (%d, '%s', '%s', %f, '%s', '%s');""", transaction.getUser_id(),
                    transaction.getPayer(), transaction.getPayee(), transaction.getAmount(),
                    transaction.getDate(), transaction.getType());
            return dbHandler.insert_update_delete(query);
        }catch (Exception ex){
            ex.printStackTrace();
            return 0;
        }
    }

    public ArrayList<Transaction> get_all(int client_id){
        ArrayList<Transaction> transactions = new ArrayList();
        String query = String.format("""
                SELECT * FROM `transactions` WHERE `user_id` = %d
                ORDER BY `date` DESC;""", client_id);
        Statement st = null;
        ResultSet rs = null;
        try{
            dbHandler = new DBHandler();
            st = dbHandler.getConn().createStatement();
            rs = st.executeQuery(query);
            while (rs.next()){
                transactions.add(new Transaction(rs));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try{rs.close(); st.close();}catch (Exception e){}
            dbHandler.close_connection();
        }
        return transactions;
    }
}
