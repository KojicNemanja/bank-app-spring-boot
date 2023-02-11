package my.bank.users.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class Person {
    private Integer id;
    private String first_name, last_name, jmbg, phone_number;
    private Address address;

    public Person(){
        id = null;
        first_name = "Undefined";
        last_name="Undefined";
        jmbg = "Undefined";
        phone_number = "Undefined";
        address = new Address();
    }

    public Person(Integer id, String first_name, String last_name, String jmbg,
                  String phone_number, Address address) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.jmbg = jmbg;
        this.phone_number = phone_number;
        this.address = address;
    }

    public Person(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.first_name = rs.getString("first_name");
        this.last_name = rs.getString("last_name");
        this.jmbg = rs.getString("jmbg");
        this.phone_number = rs.getString("phone_number");
        this.address = new Address(
               /* rs.getString("street_name"),
                rs.getString("street_number"),
                rs.getString("city")*/
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getFullName(){
        return this.first_name + " " + this.last_name;
    }
}
