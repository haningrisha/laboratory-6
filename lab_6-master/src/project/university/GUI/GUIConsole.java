package project.university.GUI;

import project.university.console.Author;
import project.university.console.Package;
import project.university.shows.Show;


import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;


public class GUIConsole {
    Author author;

    public GUIConsole(Author author){
        this.author = author;
    }

    public String[][] getMassive() {
        Date date = new Date();
        long startTime = date.getTime();

        try {
            Socket s = new Socket("localhost", 1221);
            OutputStream out = s.getOutputStream();
            InputStream in = s.getInputStream();
            ObjectOutputStream o = new ObjectOutputStream(out);
            ObjectInputStream oi = new ObjectInputStream(in);
            String line = "getMassive";
            Package letter = new Package(author, line);
            o.writeObject(letter);
            String[][] massive = (String[][]) oi.readObject();
            System.out.println("Длина массива GUIconsole: "+massive.length);
            return massive;
        } catch (java.io.IOException e) {
            if (date.getTime() - startTime < 3000) {
                return getMassive();
            } else {
                System.out.println("Сервер не активен");
                String[][] massive = {};
                return massive;
            }
        } catch (Exception e) {
            System.out.println("init error" + e);
            String[][] massive = {};
            return massive;
        }
    }
    public void make(Show show, String line){
        Date date = new Date();
        long startTime = date.getTime();

        try {
            Socket s = new Socket("localhost", 1221);
            OutputStream out = s.getOutputStream();
            ObjectOutputStream o = new ObjectOutputStream(out);
            Package letter = new Package(author, line);
            o.writeObject(letter);
            o.writeObject(show);

        } catch (java.io.IOException e) {
            if (date.getTime() - startTime < 3000) {
            } else {
                System.out.println("Сервер не активен");
            }
        } catch (Exception e) {
            System.out.println("init error" + e);
        }
    }
}
