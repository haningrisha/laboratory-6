package project.university.Server;

import project.university.console.Box;
import project.university.console.Package;
import project.university.console.Postgres;
import project.university.shows.Show;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server  extends Thread{
    private ServerBox box;
    private Socket s;
    private int num;

    public static void main (String[] args){
        try{
            int i = 0;
            ServerSocket ss = new ServerSocket(1221, 0 , InetAddress.getByName("localhost"));
             while(true){
                new Server(i, ss.accept());
                i++;
             }
        }catch (Exception e) {
            System.out.println("init err " + e);
        }
    }

    private Server(int num, Socket socket){
        this.s = socket;
        this.num = num;

        setDaemon(true);
        setPriority(NORM_PRIORITY);
        start();
    }
    public void run(){
        try {

            InputStream i = s.getInputStream();
            ObjectInputStream oi = new ObjectInputStream(i);
            OutputStream out = s.getOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(out);
            System.out.println("tag1");

            Package letter = (Package) oi.readObject();
            System.out.println(letter.command());
            try {
                box = new ServerBox(letter.getAuthor());
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
            if (letter.command().equals("load")){
                System.out.println("Я в load");
                Box hashBox = (Box)oi.readObject();
                box = new ServerBox(hashBox.getCollection(), letter.getAuthor());
                Postgres db = new Postgres();
                db.addShowsSet(box.getCollection(), letter.getAuthor());
                System.out.println(box.show());
                System.out.println("tag2");
            }else if(letter.command().equals("getMassive")){
                System.out.println(letter.command());
                System.out.println(num);
                String[][] answer = box.getMassive();
                System.out.println("Длина массива" + answer.length);
                oo.writeObject(answer);
                i.close();
                oo.close();
            }else if(letter.command().equals("delete")){
                Show show = (Show) oi.readObject();
                show.setAuthor(letter.getAuthor());
                box.remove(show);
            }else if(letter.command().equals("add")){
                Show show = (Show) oi.readObject();
                show.setAuthor(letter.getAuthor());
                box.add(show.name, show.getRating(), show.getTheme(), false);
            }else{
                System.out.println(letter.command());
                System.out.println(num);
                Scanner scanner = new Scanner(letter.command());
                String answer = box.doCommand(scanner.next(), scanner);
                System.out.println(answer);
                oo.writeObject(answer);
                i.close();
                oo.close();
                System.out.println(answer);
            }

    }catch (Exception e){
        System.err.println("init error "+e);
        run();
    }
    }
}

