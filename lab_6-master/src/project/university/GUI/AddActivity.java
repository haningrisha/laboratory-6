package project.university.GUI;

import project.university.console.Author;
import project.university.game.Interests;
import project.university.shows.Show;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import javax.swing.*;

public class AddActivity extends JFrame{
    Author author;
    ResourceBundle rs;
    String[] themes = {
            "SPACE",
            "DANCE"
    };
    JLabel name;
    JLabel rating;
    JLabel theme;
    JTextField nameIn = new JTextField( 5);
    JTextField ratingIn = new JTextField(5);
    JComboBox themeIn = new JComboBox(themes);
    JButton add;
    JButton cancel;
    JLabel space1 = new JLabel("");

    JMenuBar mb = new JMenuBar();
    JMenu menu;
    JMenu language;
    JMenuItem menu_russian;
    JMenuItem menu_ukrainian;
    JMenuItem menu_spanish;
    JMenuItem menu_czech;

    public AddActivity(Author author, ResourceBundle rs){
        super("Add");
        this.author = author;
        this.setBounds(100, 100, 250, 200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.rs = rs;
        menu = new JMenu(rs.getString("settingsText"));
        language = new JMenu(rs.getString("languageText"));
        menu_russian = new JMenuItem(rs.getString("russianText"));
        menu_ukrainian = new JMenuItem(rs.getString("ukrainianText"));
        menu_spanish = new JMenuItem(rs.getString("spanishText"));
        menu_czech = new JMenuItem(rs.getString("czechText"));
        name = new JLabel(rs.getString("nameText")+":");
        rating = new JLabel(rs.getString("ratingText")+":");
        theme = new JLabel(rs.getString("themeText")+":");
        add = new JButton(rs.getString("addText"));
        cancel = new JButton(rs.getString("cancelText"));

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
        container.setLayout(new GridLayout(5,2,2, 2));
        container.add(space1);
        container.add(name);
        container.add(nameIn);
        container.add(rating);
        container.add(ratingIn);
        container.add(theme);
        container.add(themeIn);
        container.add(cancel);
        container.add(add);
        add.addActionListener(new AddListener());
        cancel.addActionListener(new CancelListener());
    }
    class AddListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Show show = new Show(nameIn.getText(), Integer.parseInt(ratingIn.getText()), Interests.valueOf((String) themeIn.getSelectedItem()));
            GUIConsole console = new GUIConsole(author);
            console.make(show, "add");
            try{
                TimeUnit.SECONDS.sleep(1);
            }catch(InterruptedException e1){}
            setVisible(false);
            ManageActivity manageActivity = new ManageActivity(author, rs);
            manageActivity.setVisible(true);
        }
    }
    class CancelListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            ManageActivity manageActivity = new ManageActivity(author, rs);
            manageActivity.setVisible(true);
        }
    }
    class RussianListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("ru"));
            setVisible(false);
            AddActivity addActivity = new AddActivity(author, rs);
            addActivity.setVisible(true);
        }
    }
    class UkrainianListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("uk"));
            setVisible(false);
            AddActivity addActivity = new AddActivity(author, rs);
            addActivity.setVisible(true);
        }
    }
    class SpanishListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("es", "PA"));
            setVisible(false);
            AddActivity addActivity = new AddActivity(author, rs);
            addActivity.setVisible(true);
        }
    }
    class CzechListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("cs"));
            setVisible(false);
            AddActivity addActivity = new AddActivity(author, rs);
            addActivity.setVisible(true);
        }
    }
}