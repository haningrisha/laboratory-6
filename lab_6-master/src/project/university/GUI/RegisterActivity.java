package project.university.GUI;

import project.university.console.Register;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.swing.*;

public class RegisterActivity extends JFrame{
    JMenuBar mb = new JMenuBar();
    JMenu menu;
    JMenu language;
    JMenuItem menu_russian;
    JMenuItem menu_ukrainian;
    JMenuItem menu_spanish;
    JMenuItem menu_czech;

    ResourceBundle rs;

    JLabel email;
    JTextField emailIn = new JTextField( 5);
    JButton register;
    JButton exit;
    JLabel space1 = new JLabel("");

    public RegisterActivity(ResourceBundle rs){
        super("Register");
        this.rs = rs;
        this.setBounds(100, 100, 250, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menu = new JMenu(rs.getString("settingsText"));
        language = new JMenu(rs.getString("languageText"));
        menu_russian = new JMenuItem(rs.getString("russianText"));
        menu_ukrainian = new JMenuItem(rs.getString("ukrainianText"));
        menu_spanish = new JMenuItem(rs.getString("spanishText"));
        menu_czech = new JMenuItem(rs.getString("czechText"));

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

        email = new JLabel(rs.getString("emailText"));
        register = new JButton(rs.getString("registerText"));
        exit = new JButton(rs.getString("exitText"));

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(3,2,2, 2));
        container.add(space1);
        container.add(email);
        container.add(emailIn);
        container.add(exit);
        container.add(register);
        register.addActionListener(new RegisterListener());
        exit.addActionListener(new ExitListner());
    }
    class RegisterListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            String  emailText= emailIn.getText();
            Register registretion = new Register();
            registretion.registration(emailText);
            JOptionPane.showMessageDialog(null, rs.getString("confirmationText"), rs.getString("confirmationTitle"), JOptionPane.PLAIN_MESSAGE);
            setVisible(false);
            LoginActivity loginActivity = new LoginActivity(rs);
            loginActivity.setVisible(true);
        }
    }
    class ExitListner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            LoginActivity loginActivity = new LoginActivity(rs);
            loginActivity.setVisible(true);
        }
    }
    class RussianListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("ru"));
            setVisible(false);
            RegisterActivity registerActivity = new RegisterActivity(rs);
            registerActivity.setVisible(true);
        }
    }
    class UkrainianListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("uk"));
            setVisible(false);
            RegisterActivity registerActivity = new RegisterActivity(rs);
            registerActivity.setVisible(true);
        }
    }
    class SpanishListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("es", "PA"));
            setVisible(false);
            RegisterActivity registerActivity = new RegisterActivity(rs);
            registerActivity.setVisible(true);
        }
    }
    class CzechListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("cs"));
            setVisible(false);
            RegisterActivity registerActivity = new RegisterActivity(rs);
            registerActivity.setVisible(true);
        }
    }
}
