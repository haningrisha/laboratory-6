package project.university.GUI;

import project.university.console.Author;
import project.university.console.SignIn;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;
import javax.swing.event.MenuDragMouseEvent;
import javax.swing.event.MenuDragMouseListener;

public class LoginActivity extends JFrame {
    Author author;

    JMenuBar mb = new JMenuBar();
    JMenu menu;
    JMenu language;
    JMenuItem menu_russian;
    JMenuItem menu_ukrainian;
    JMenuItem menu_spanish;
    JMenuItem menu_czech;


    ResourceBundle rs;
    JLabel email;
    JLabel password;
    JLabel incompatiblePassword = new JLabel("");
    JLabel space = new JLabel("");
    JLabel space1 = new JLabel("");

    JTextField emailIn = new JTextField(5);
    JTextField passwordIn = new JPasswordField(5);

    JButton register;
    JButton login;

    public LoginActivity(ResourceBundle rs) {
        super("Login");
        this.rs = rs;
        this.setBounds(100, 100, 250, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menu = new JMenu(rs.getString("settingsText"));
        language = new JMenu(rs.getString("languageText"));
        menu_russian = new JMenuItem(rs.getString("russianText"));
        menu_ukrainian = new JMenuItem(rs.getString("ukrainianText"));
        menu_spanish = new JMenuItem(rs.getString("spanishText"));
        menu_czech = new JMenuItem(rs.getString("czechText"));

        email = new JLabel(rs.getString("emailText") + ":");
        password = new JLabel(rs.getString("passwordText") + ":");
        register = new JButton(rs.getString("registerText"));
        login = new JButton(rs.getString("loginText"));

        language.add(menu_czech);
        language.add(menu_russian);
        language.add(menu_spanish);
        language.add(menu_ukrainian);
        menu.add(language);
        mb.add(menu);
        menu_russian.addActionListener(new RussianListener());
        menu_czech.addActionListener(new CzechListener());
        menu_spanish.addActionListener(new SpanishListener());
        menu_ukrainian.addActionListener(new UkrainianListener());

        this.add(mb);
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(5, 2, 2, 2));
        container.add(space1);
        container.add(email);
        container.add(emailIn);
        container.add(password);
        container.add(passwordIn);
        container.add(incompatiblePassword);
        container.add(space);
        container.add(register);
        container.add(login);

        register.addActionListener(new RegisterListener());
        login.addActionListener(new LoginListener());
    }

    class RegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            RegisterActivity registerActivity = new RegisterActivity(rs);
            registerActivity.setVisible(true);

        }
    }

    class LoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String emailText = emailIn.getText();
            String passwordText = passwordIn.getText();
            if (SignIn.signin(emailText, passwordText)) {
                author = new Author(emailText, passwordText);
                setVisible(false);
                ManageActivity manageActivity = new ManageActivity(author, rs);
                manageActivity.setVisible(true);
            } else {
                incompatiblePassword.setText(rs.getString("tryAgain"));
                incompatiblePassword.setForeground(Color.RED);
            }
        }
    }
    class RussianListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("ru"));
            LoginActivity loginActivity = new LoginActivity(rs);
            setVisible(false);
            loginActivity.setVisible(true);
        }
    }
    class UkrainianListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("uk"));
            LoginActivity loginActivity = new LoginActivity(rs);
            setVisible(false);
            loginActivity.setVisible(true);
        }
    }
    class SpanishListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("es", "PA"));
            LoginActivity loginActivity = new LoginActivity(rs);
            setVisible(false);
            loginActivity.setVisible(true);
        }
    }
    class CzechListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("cs"));
            LoginActivity loginActivity = new LoginActivity(rs);
            setVisible(false);
            loginActivity.setVisible(true);
        }
    }
}

