package my.bank.users.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Address {
    private Integer user_id;
    private String street_name, street_number, city;

    public Address(){
        user_id = null;
        street_name = "Undefined";
        street_number = "Undefined";
        city = "Undefined";
    }

    public Address(Integer user_id, String street_name, String street_number, String city) {
        this.user_id = user_id;
        this.street_name = street_name;
        this.street_number = street_number;
        this.city = city;
    }

    public Address(ResultSet rs) throws SQLException {
        setUser_id(rs.getInt("id"));
        setStreet_name(rs.getString("street_name"));
        setStreet_number(rs.getString("street_number"));
        setCity(rs.getString("city"));
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getStreet_name() {
        return street_name;
    }

    public void setStreet_name(String street_name) {
        this.street_name = street_name;
    }

    public String getStreet_number() {
        return street_number;
    }

    public void setStreet_number(String street_number) {
        this.street_number = street_number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
