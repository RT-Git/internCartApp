package xyz.ravitripathi.interncart.pojo;

/**
 * Created by ravi on 17/01/18.
 */


public class SignupPOJO {

    final String userName;
    final String firstName;
    final String lastName;
    final String password;

    public SignupPOJO(String username, String password, String firstName, String lastName) {
        this.userName = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }
}

