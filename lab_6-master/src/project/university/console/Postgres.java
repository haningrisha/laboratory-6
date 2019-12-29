package project.university.console;

import project.university.console.Author;
import project.university.game.Interests;
import project.university.shows.Show;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class Postgres {
    private String url = "jdbc:postgresql://localhost:5434/postgres";
    private String user = "postgres";
    private String password = "123";

    boolean addAccount(String login, String password, String email){
        String sql = "INSERT INTO accounts VALUES " + "('"+ login +"', '"+ password + "', '" + email + "'); ";
        System.out.println(sql);
        return add(sql);

    }
    public void addShow(String name, int rating, Interests theme, Author author){
        Show show = new Show(name, rating, theme);
        show.setAuthor(author);
        System.out.println("Я здесь");
        if (checkShow(show)){
            String sql = "INSERT INTO shows VALUES " + "('"+ name +"', " + rating + ", '" + theme.toString() + "','"+ "1994-11-19" +"', '"+ author.getLogin() + "');";
            add(sql);
            System.out.println("Объект добавлен");
        }

    }
    public void addShowsSet(HashSet<Show> hashSet, Author author){
        Iterator<Show> iterator = hashSet.iterator();
        System.out.println("Я в addShowsSet");
        System.out.println(hashSet);
        while (iterator.hasNext()){
            Show show = iterator.next();
            System.out.println("Я в итераторе");
            addShow(show.name, show.getRating(), show.getTheme(), author);
        }
    }
    private boolean add(String string){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            stmt.executeUpdate(string);
            stmt.close();
            c.close();

        }catch (org.postgresql.util.PSQLException e){
            return false;

        }catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
            return false;
        }
        System.out.println("Record created sucsesfully");
        return true;
    }

    HashMap<String, String> getAuthor(){
        Connection c = null;
        Statement stmt = null;
        HashMap<String, String> hashMap= new HashMap<>();
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM accounts;");
            while (rs.next()){
                hashMap.put(rs.getString("login"),rs.getString("password"));
            }
            c.close();

        }catch (Exception e ){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        return hashMap;
    }
    public HashSet<Show> getShows(){
        Connection c = null;
        Statement stmt = null;
        HashSet<Show> hashSet= new HashSet<>();
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM shows;");
            while (rs.next()){
                String name = rs.getString("name");
                int rating = rs.getInt("rating");
                Interests theme = Interests.valueOf(rs.getString("theme"));
                String author_name = rs.getString("author");
                Author author = new Author(author_name);
                Show show = new Show(name, rating, theme);
                show.setAuthor(author);
                hashSet.add(show);
            }
            c.close();

        }catch (Exception e ){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
        return hashSet;
    }
    public String[][] getMassive(){
        Connection c = null;
        Statement stmt = null;

        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM shows;");
            int i = 0;

            while (rs.next()){
                i ++;
            }
            rs = stmt.executeQuery("SELECT * FROM shows;");
            String[][] massive = new String[i][4];
            i = 0;

            while (rs.next()){
                massive[i][0] = rs.getString("name");
                massive[i][1] = String.valueOf(rs.getInt("rating"));
                massive[i][2] = rs.getString("theme");
                massive[i][3] = rs.getString("author");
                i++;
            }
            c.close();
            return massive;
        }catch (Exception e ){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            String[][] massive = {};
            return massive;
        }
    }
    public void deleteAll(Author author){
        Connection c = null;
        Statement stmt = null;
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
            stmt = c.createStatement();
            stmt.executeQuery("DELETE FROM shows WHERE author = '" + author.getLogin() + "';");
            c.close();

        }catch (Exception e ){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

    }
    public void deleteShow(Show show, Author author){
        Connection c = null;
        Statement stmt = null;
        try{
            Class.forName("org.postgresql.Driver");
            c = DriverManager.getConnection(url, user, password);
            stmt = c.createStatement();
            stmt.executeQuery("DELETE FROM shows WHERE author = '" + author.getLogin() + "' AND name = '"+ show.name + "';");
            c.close();

        }catch (Exception e ){
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
        }
    }
    public boolean checkShow(Show show){
        if(!getShows().isEmpty()){
        if(getShows().contains(show)){
            System.out.println("Такое шоу есть");
            return false;
        }else {
            System.out.println("Такого шоу нет");
            return true;
        }
        }else {
            return true;
        }
    }
}