package project.university.console;

import project.university.console.Author;
import project.university.console.Box;
import project.university.console.Package;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Date;
/**
 * Класс осущетсвляющий работу с коллекцией, с помощью консоли
 * @author Grigoriy Hanin
 */
class Console {
    /**
     * Box с которым идёт работа через консоль
     */
    private Box box;
    private Author author;

    Console(Box box, Author author){
        this.box = box;
        this.author = author;
    }

    /**
     * Метод начинающий работу с коллекцией
     */
    void start(){
        Date date = new Date();
        long startTime = date.getTime();
        try{


        while (true) {
                Socket s = new Socket("localhost",1221);
                OutputStream out = s.getOutputStream();
                InputStream in = s.getInputStream();
                ObjectOutputStream o = new ObjectOutputStream(out);
                ObjectInputStream oi = new ObjectInputStream(in);

                System.out.println("Введите команду");
                Scanner scanner = new Scanner(System.in);
                String line = scanner.nextLine();
                Package letter = new Package(author, line);
                if (line.equals("Выход") || line.equals("Exit")){
                    break;
                }
                else if (line.equals("load")){
                    o.writeObject(letter);
                    o.writeObject(box);
                    o.close();
                    oi.close();
                }
                else {
                    o.writeObject(letter);
                    System.out.println((String) oi.readObject());
                    o.close();
                    oi.close();
                }
            }
            }catch (java.io.IOException e){
            if (date.getTime()-startTime < 3000){
                start();
            }else {
                System.out.println("Сервер не активен");
            }
            }catch (Exception e){
                System.out.println("init error" + e);
        }
    }
}
