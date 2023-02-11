package my.bank.users.employees.dao;
import my.bank.database.DBHandler;
import my.bank.users.models.User;
import my.bank.users.status.UserStatus;

import java.sql.*;

public class UserDAO {
    private DBHandler dbHandler = null;

    public UserDAO(){}

    public int save(User user) throws SQLException {
        dbHandler = new DBHandler();
        String query = String.format("""
            INSERT INTO `users`(`person_id`, `username`, `password`, `status`)
            VALUES (%d, '%s', '%s', '%s');""", user.getId(), user.getUsername(),
                user.getPassword(), user.getUser_status().toString());
        return dbHandler.insert_update_delete(query);
    }

    public int edit(User user) throws SQLException {
        String query = String.format("""
                UPDATE `users` SET `username`='%s',`password`='%s',`status`='%s'
                WHERE `person_id` = %d""", user.getUsername(), user.getPassword(), user.getUser_status(),
                user.getId());
        dbHandler = new DBHandler();
        return dbHandler.insert_update_delete(query);
    }

    public User getForUsername(String username){
        User user = null;
        String query = String.format("""
                SELECT p.id, p.first_name, p.last_name, p.jmbg,  p.phone_number, a.street_name, a.street_number, a.city, u.username, u.password, u.status
                FROM person as p, address AS a, users AS u
                WHERE (u.username = '%s') AND (u.person_id = p.id) AND (p.id = a.person_id);""", username);

        ResultSet rs = null;
        PreparedStatement ps = null;
        try{
            dbHandler = new DBHandler();
            ps = dbHandler.getConn().prepareStatement(query);
            rs = ps.executeQuery();
            if(rs.next()){
                user = new User(rs);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            try {rs.close(); ps.close();}catch (Exception e){}
            dbHandler.close_connection();
        }
        return user;
    }


}
