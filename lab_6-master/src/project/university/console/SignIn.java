package project.university.console;

import project.university.console.Postgres;
import project.university.console.Register;

import java.util.HashMap;

public class SignIn {
    public static boolean signin(String login, String password){
        Postgres db = new Postgres();
        HashMap<String,String> accounts = db.getAuthor();
        try {
            if (accounts.get(login).equals(new String(Register.getMessageDigest(password)))) {
                return true;
            }else{
                return false;
            }
        }catch (NullPointerException e){
            return false;
        }


    }
}
