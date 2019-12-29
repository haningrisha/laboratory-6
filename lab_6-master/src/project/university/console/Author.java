package project.university.console;

import java.io.Serializable;

public class Author implements Serializable {
    private String login;
    private String password;
    private String email;
    public Author(String login, String password, String email){
        this.login = login;
        this.password = password;
        this.email = email;
    }
    public Author(String login, String password){
        this.login = login;
        this.password = password;
    }
    public Author(String login){
        this.login = login;
    }
    public String getLogin(){
        return login;
    }
    String getEmail(){
        return email;
    }

    private String getPassword(){
        return password;
    }
}
