package my.bank.users.employees.dao;

import my.bank.database.DBHandler;
import my.bank.users.models.Address;
import my.bank.users.models.User;

import java.sql.SQLException;

public class AddressDAO {
    private DBHandler dbHandler = null;
    public AddressDAO(){}

    public int save(Address address) throws SQLException {
        dbHandler = new DBHandler();
        String query = String.format("""
                INSERT INTO `address`(`person_id`, `street_name`, `street_number`, `city`)\s
                VALUES ('%s','%s','%s','%s')""", address.getUser_id(), address.getStreet_name(),
                address.getStreet_number(), address.getCity());
        return dbHandler.insert_update_delete(query);
    }

    public int edit(Address address) throws SQLException {
        String query = String.format("""
                UPDATE `address` SET `street_name`='%s',`street_number`='%s',`city`='%s'
                WHERE `person_id`=%d""", address.getStreet_name(), address.getStreet_number(), address.getCity(),
                address.getUser_id());
        dbHandler = new DBHandler();
        return dbHandler.insert_update_delete(query);
    }
}
