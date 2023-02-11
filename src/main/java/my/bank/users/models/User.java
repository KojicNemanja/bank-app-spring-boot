package my.bank.users.models;

import my.bank.users.status.UserStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class User extends Person{
    private String username, password;
    private UserStatus user_status;

    public User(){
        super();
        username = null;
        password = null;
        user_status = UserStatus.BANNED;
    }

    public User(Integer id, String first_name, String last_name, String jmbg, String phone_number, Address address,
                String username, String password, UserStatus status) {
        super(id, first_name, last_name, jmbg, phone_number, address);
        setUsername(username);
        setPassword(password);
        this.user_status = status;
    }

    public User(ResultSet rs) throws SQLException {
        super(rs);
        setUsername(rs.getString("username"));
        setPassword(rs.getString("password"));
        String user_status = rs.getString("status");
        if("EMPLOYEE".equals(user_status)){
            this.user_status = UserStatus.EMPLOYEE;
        }else if("CLIENT".equals(user_status)){
            this.user_status = UserStatus.CLIENT;
        }else {
            this.user_status = UserStatus.BANNED;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getUser_status() {
        return user_status;
    }

    public void setUser_status(UserStatus user_status) {
        this.user_status = user_status;
    }

}
