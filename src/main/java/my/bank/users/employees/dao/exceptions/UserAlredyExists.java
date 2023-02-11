package my.bank.users.employees.dao.exceptions;

public class UserAlredyExists extends Exception{
    public UserAlredyExists(String message) {
        super(message);
    }
}
