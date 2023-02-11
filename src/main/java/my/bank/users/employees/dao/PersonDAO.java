package my.bank.users.employees.dao;
import my.bank.database.DBHandler;
import my.bank.users.models.User;
import java.sql.SQLException;

public class PersonDAO {
    DBHandler dbHandler = null;

    public PersonDAO(){}

    public int save(User user) throws SQLException {
        dbHandler = new DBHandler();
        String query = String.format("""
                    INSERT INTO `person`(`first_name`, `last_name`, `jmbg`, `phone_number`)
                    VALUES ('%s','%s','%s','%s')""", user.getFirst_name(), user.getLast_name(),
                user.getJmbg(), user.getPhone_number());
        return dbHandler.insert_with_generated_keys(query);
    }

    public int edit(User user) throws SQLException {
        dbHandler = new DBHandler();
        String query = String.format("""
                UPDATE `person` SET `first_name`='%s',`last_name`='%s',`jmbg`='%s',`phone_number`='%s'
                WHERE `id` =%d;""", user.getFirst_name(), user.getLast_name(), user.getJmbg(), user.getPhone_number(),
                user.getId());
        return dbHandler.insert_update_delete(query);
    }
}
