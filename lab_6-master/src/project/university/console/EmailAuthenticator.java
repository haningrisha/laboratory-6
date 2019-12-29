package project.university.console;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

class EmailAuthenticator extends Authenticator
{
    private String login   ;
    private String password;

    EmailAuthenticator (final String login, final String password){
        this.login    = login;
        this.password = password;
    }
    public PasswordAuthentication getPasswordAuthentication(){
        return new PasswordAuthentication(login, password);
    }
}