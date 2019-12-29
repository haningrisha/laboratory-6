package project.university.console;


import project.university.game.Game;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int count = 0;
        boolean signed = false;
        Author author = new Author("", "", "");
        View view = new View(3);


        while (true) {
            if(signed){
                if(count > 0) {
                    System.out.println("Выберите режим (игра/коллекция/выход)");
                }else{
                    System.out.println("Выберите режим (игра/выход)");
                }
                Scanner scanner = new Scanner(System.in);
                String command = scanner.next();
                if (command.equals("игра")) {
                    Game game = new Game(view);
                    game.start();
                    System.err.println("Reached tag1");
                    Box box = new Box(view.getHashSet());
                    box.save(false);
                    count++;
                }else if (command.equals("коллекция") && count > 0) {
                    Box box = new Box(view.getHashSet());
                    box.save(false);
                    box.getDate(view.getDateHashSet());
                    Console console = new Console(box, author);
                    console.start();
                    box.save(true);
                }else if (command.equals("выход")){
                    if (count > 0) {
                    }
                    System.exit(0);
                }else if (command.equals("коллекция") && count == 0){
                    Box box = new Box();
                    if (box.size() == 0){
                        System.out.println("Пусто");
                    } else {
                        Console console = new Console(box, author);
                        console.start();
                    }
                }else {
                    System.out.println("Попробуйте ещё");
                }
            }else {
                System.out.println("Вход/Регистрация");
                Scanner scanner = new Scanner(System.in);
                String command = scanner.next();
                if(command.equals("Регистрация")){
                    Register register = new Register();
                    register.registration("df");
                }
                if(command.equals("Вход")){

                    System.out.println("Введите логин");
                    String login = scanner.next();
                    java.io.Console console = System.console();
                    String password;
                    try {
                        char[] b = console.readPassword("Введите пароль: ");
                        password = new String(b);
                    }catch (Exception e){
                        System.out.println("Введите пароль");
                        password = scanner.next();
                    }
                    System.out.println(password);
                    if (SignIn.signin(login,password)){
                        signed=true;
                        author = new Author(login, password);
                    }

                }
            }
        }
    }

    @Override
    public String toString() {
        return "Main{}";
    }
}
