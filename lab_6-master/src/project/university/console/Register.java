package project.university.console;


import project.university.console.MailSender;
import project.university.console.PasswordGenerator;
import project.university.console.Postgres;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class Register {
    Postgres db = new Postgres();
    private String email;
    private String password;
    public void registration(String email){
        this.email = email;
        createPassword();
    }
    void createPassword(){
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .build();
        password = passwordGenerator.generate(8);
        if (!db.addAccount(email,new String(getMessageDigest(password)) , email)){
            createPassword();
        }

        System.out.println("Аккаунт успешно создан");
        MailSender.send(email, password);
        System.out.println("Пароль был выслан вам на почту");
    }
    static byte[] getMessageDigest (final String text)
    {
        final String algo = "MD2";
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(algo);
            md.update(text.getBytes());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (md != null)
            return md.digest();
        else
            return null;
    }
}



