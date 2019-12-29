package project.university.GUI;

import project.university.console.Author;
import project.university.console.View;
import project.university.game.Game;
import project.university.game.Interests;
import project.university.shows.Show;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class ManageActivity extends JFrame{
    Author author;
    GUIConsole console ;
    ResourceBundle rs;
    String column[];
    String [][] data;
    JTable jt;
    JScrollPane sp;
    JButton delete;
    JButton add;
    JButton game;
    JButton exit;
    Container container;

    JMenuBar mb = new JMenuBar();
    JMenu menu;
    JMenu language;
    JMenuItem menu_russian;
    JMenuItem menu_ukrainian;
    JMenuItem menu_spanish;
    JMenuItem menu_czech;

    ManageActivity(Author author, ResourceBundle rs){
        super("Manage");
        this.author = author;
        this.console =  new GUIConsole(author);
        this.setBounds(100, 100, 250, 400);
        container = this.getContentPane();
        this.rs = rs;
        menu = new JMenu(rs.getString("settingsText"));
        language = new JMenu(rs.getString("languageText"));
        menu_russian = new JMenuItem(rs.getString("russianText"));
        menu_ukrainian = new JMenuItem(rs.getString("ukrainianText"));
        menu_spanish = new JMenuItem(rs.getString("spanishText"));
        menu_czech = new JMenuItem(rs.getString("czechText"));
        String data[] = {rs.getString("nameText"),rs.getString("ratingText"),rs.getString("themeText"),rs.getString("authorText")};
        column = data;
        delete = new JButton(rs.getString("deleteText"));
        add = new JButton(rs.getString("addText"));
        game  = new JButton(rs.getString("playText"));
        exit = new JButton(rs.getString("exitText"));

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
        updateTable();
    }

    public void updateTable(){
        data = console.getMassive();
        jt  = new JTable(data,column);
        sp = new JScrollPane(jt);
        container.setLayout(new GridLayout(6,1));
        jt.setBounds(30,40,200,300);
        container.add(sp);
        container.setSize(300,400);
        container.add(delete);
        container.add(add);
        container.add(game);
        container.add(exit);
        delete.addActionListener(new DeleteListener());
        add.addActionListener(new AddListener());
        game.addActionListener(new GameListener());
        exit.addActionListener(new ExitListner());
    }

    class DeleteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
            int rowNum = jt.getSelectedRow();
            if (data[rowNum][3].equals(author.getLogin())){
                Show show = new Show(data[rowNum][0], Integer.parseInt(data[rowNum][1]), Interests.valueOf(data[rowNum][2]));
                console.make(show, "delete");

                try{
                    TimeUnit.MILLISECONDS.sleep(100);
                }catch(InterruptedException e1){}
                setVisible(false);
                ManageActivity manageActivity = new ManageActivity(author, rs);
                manageActivity.setVisible(true);
            }
            }catch (ArrayIndexOutOfBoundsException e1){

            }
        }
    }
    class AddListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            AddActivity addActivity = new AddActivity(author, rs);
            setVisible(false);
            addActivity.setVisible(true);
        }
    }
    class GameListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            View view = new View(3);
            Game viewGame = new Game(view);
            viewGame.start();
            HashSet<Show> shows = viewGame.getHashSet();
            Iterator<Show> iterator = shows.iterator();
            while (iterator.hasNext()){
                console.make(iterator.next(), "add");
            }
            try{
                TimeUnit.MILLISECONDS.sleep(100);
            }catch(InterruptedException e1){}
            setVisible(false);
            ManageActivity manageActivity = new ManageActivity(author, rs);
            manageActivity.setVisible(true);
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
            ManageActivity manageActivity = new ManageActivity(author, rs);
            manageActivity.setVisible(true);
        }
    }
    class UkrainianListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("uk"));
            setVisible(false);
            ManageActivity manageActivity = new ManageActivity(author, rs);
            manageActivity.setVisible(true);
        }
    }
    class SpanishListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("es", "PA"));
            setVisible(false);
            ManageActivity manageActivity = new ManageActivity(author, rs);
            manageActivity.setVisible(true);
        }
    }
    class CzechListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            rs = ResourceBundle.getBundle("project.university.characters", new Locale("cs"));
            setVisible(false);
            ManageActivity manageActivity = new ManageActivity(author, rs);
            manageActivity.setVisible(true);
        }
    }
}