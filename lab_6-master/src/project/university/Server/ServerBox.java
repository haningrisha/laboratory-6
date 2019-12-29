package project.university.Server;

import project.university.console.Author;
import project.university.console.Postgres;
import project.university.game.Interests;
import project.university.shows.Show;
import project.university.console.ParameterToMuchException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;

/**
 * Класс, описывающий методы для работы с коллекцией
 * @author Grigoriy Hanin
 */
public class ServerBox implements Serializable {
    private static final long serialVersionUID = 1L;
    private Postgres db= new Postgres();
    private Author author;
    /**
     * Коллекция для обработки
     */
    private HashSet<Show> hashSet = new HashSet<>();
    /**
     * Дата создания коллекции
     */
    private String date;

    /**
     * Конструктор Box, с загрузкой новой коллекции
     *
     * @param hashSet Колллекция для обработки
     */
    ServerBox(HashSet<Show> hashSet, Author author) {
        this.author =author;
        this.hashSet.addAll(hashSet);


    }

    /**
     * Конструтор Box, загружающий колллекцию из файла
     */
    ServerBox(Author author) {
        this.author = author;
        load(false);
    }

    /**
     * Метод возвращающий размер коллекции
     *
     * @return Размер коллекции
     */
    int size() {
        return hashSet.size();
    }

    /**
     * Метод заполняющий дату создания коллекции
     *
     * @param date дата создания коллекции
     */
    void getDate(String date) {
        this.date = date;
    }

    /**
     * Метод для заполнения коллекции
     *
     * @param hashSet коллекция для обработки
     */
    void setCollection(HashSet<Show> hashSet, Boolean save) {
        this.hashSet = hashSet;
        if (save) {
            save(false);
        }
    }

    /**
     * Метод возвращающий обрабатываемую коллекцию
     *
     * @return обрабатываемая коллекция
     */
    public HashSet<Show> getCollection() {
        return hashSet;
    }

    /**
     * Метод принимающий комманду и Scanner, которым считывется строка, и вызывающий методы комманд.
     *
     * @param string  текст комманды
     * @param scanner Scanner, которым эта команда была считана
     * @return Возвращяет true или false, в зависимости от того существует ли такая команда
     */
    String doCommand(String string, Scanner scanner) {
        switch (string) {
            case "add":
                String[] strings = read(scanner.nextLine());
                if (strings.length != 0) {
                    return add(strings, true);
                }
                return "Неправильный формат ввода";
            case "show":
                return show();
            case "save":
                return save(true);
            case "load":
                return load(true);
            case "info":
                return info();
            case "remove":
                try {
                    return remove(read(scanner.nextLine()));
                } catch (ConcurrentModificationException e) {
                    return "ConcurrentModificationException";
                }
            case "removeAll":
                return removeAll();
            case "help":
                return help();
            default:
                return "Команда не найдена";
        }
    }

    /**
     * Метод проверяет наличие комманды
     *
     * @param command комманда для проверки
     * @return возвращает true, если команда существует, false, если нет
     */
    boolean checkProgram(String command) {
        switch (command) {
            case "add":
                return true;
            case "show":
                return true;
            case "save":
                return true;
            case "load":
                return true;
            case "info":
                return true;
            case "remove":
                return true;
            case "removeAll":
                return true;
            case "help":
                return true;
            default:
                return false;
        }
    }

    /**
     * Метод загружает коллекцию из XML-файла
     *
     * @param massege если true- вывод сообщение об удачном выполнении команды, если false- нет
     */
    public String load(boolean massege) {
        File file = new File("collection.xml");
        hashSet = new HashSet<>();
        try {
            Scanner scanner = new Scanner(file);
            if (scanner.hasNext()) {
                scanner.nextLine();
            }
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                if (line.equals("</collection>")) {
                    break;
                }
                line = line.trim();
                int start = line.lastIndexOf("<class>") + 7;
                int end = line.lastIndexOf("</class>");
                line = line.substring(start, end);
                add(read(line), massege);
            }
            return "Команда выполнена";
        } catch (FileNotFoundException e) {
            return "Файл не найден :-(";
        }

    }

    /**
     * Метод удаляет все содержымое коллекции(изменения не сохраняются в XML-файл
     */
    private String removeAll() {
        db.deleteAll(author);
        return "";
    }

    /**
     * Метод осуществляет обработку массива типа String, разделяя её на параметры, которые затем отправляет на добавление
     *
     * @param separate обрабатываемый массив String
     * @param message  если true- вывод сообщение об удачном выполнении команды, если false- нет
     */
    private String add(String[] separate, boolean message) {
        try {
            HashMap<String, String> map = makeParameter(separate);
            String name = map.get("name");
            int rating = Integer.parseInt(map.get("rating"));
            Interests theme = Interests.valueOf(map.get("theme"));
            add(name, rating, theme, message);
            return "Команда выполнена";
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Неверный формат команды (пример: add {name=\"Idiot\", rating=\"0\", theme=\"SPACE\")";
        } catch (NumberFormatException e) {
            return "Неправильный формат ввода";
        } catch (NullPointerException e){
            return "Неправильный формат ввода";
        }
    }

    /**
     * Метод обрабатывает массив строк, находя в нём имя, рейтинг и тему
     *
     * @param strings обрабаьтываемая строка
     * @return возвращает HashMap с параметрами
     */
    private HashMap<String, String> makeParameter(String[] strings) {
        try {
            HashMap<String, String> hashMap = new HashMap();
            for (int i = 0; i < strings.length; i++) {
                String[] line = strings[i].split(":");
                line[1] = line[1].split("\"")[1];
                switch (line[0].split("\"")[1].trim()) {
                    case "name":
                        hashMap.put("name", line[1].trim());
                        break;
                    case "rating":
                        hashMap.put("rating", line[1].trim());
                        break;
                    case "theme":
                        hashMap.put("theme", line[1].trim());
                        break;
                    default:
                        throw new ParameterToMuchException("неправильный формат ввода");
                }
            }
            return hashMap;
        }catch (ParameterToMuchException e){
            HashMap<String,String> hashMap = new HashMap();
            return hashMap;
        }
    }

    /**
     * Метод обрабатывает команду в формате Json, созадавая массив String из параметров
     *
     * @param line строка для обработки
     * @return Возвращает String- массив параметров
     */
    private String[] read(String line) {

        try {
            line = line.trim();
            try {
                String lines = " " + line + " ";
                String test = lines.split("}")[1];
                String test2 = lines.split("\\{")[0];
                if (!test.equals(" ") || !test2.equals(" ")) {
                    throw new StringIndexOutOfBoundsException();
                }
            } catch (ArrayIndexOutOfBoundsException e){

            }
            line = line.substring(line.lastIndexOf('{') + 1, line.lastIndexOf("}"));
            line = line.trim();
            String[] separate = line.split(",");
            if (separate[0].equals(line)){
                throw new StringIndexOutOfBoundsException();
            }
            return separate;
        } catch (StringIndexOutOfBoundsException e) {
            System.err.println("Неправильный формат ввода");
            String[] strings = {};
            return strings;
        }
    }

    /**
     * Метод принимает объект типа Show и добавляет его в коллекцию
     *
     * @param object  принимаемый объект
     * @param message Если true- вывод сообщение об удачном выполнении команды, если false- нет
     */
    private String add(Show object, boolean message) {
        hashSet.add(object);
        return "Команда выполена";

    }

    /**
     * Принимает параметры Show, создаёт из них экземпляр Show и отправляет экземпляр на добавление в коллекцию
     *
     * @param name    параметр Show.name
     * @param rating  параметр Show.rating
     * @param theme   параметр Show.theme
     * @param message если true- вывод сообщение об удачном выполнении команды, если false- нет
     */
    synchronized void add(String name, int rating, Interests theme, boolean message) {
        db.addShow(name, rating, theme, author);
        Show show = new Show(name, rating, theme);
        add(show, message);

    }

    /**
     * Метод выводит все эллементы коллекции
     */
    public String show() {
        String answer = "";
        HashSet<Show> hashSet = db.getShows();
        Iterator<Show> iterator = hashSet.iterator();
        while (iterator.hasNext()){
            Show show = iterator.next();
            answer += show.toString() + "\n";
        }
        return answer + "\n" + "Команда выполнена";
    }

    /**
     * Метод сохраняет коллекцию в XML-файл
     *
     * @param massege если true- вывод сообщение об удачном выполнении команды, если false- нет
     */
    String save(Boolean massege) {
        File file = new File("collection.xml");
        try (PrintWriter printWriter = new PrintWriter(file)) {
            printWriter.println("<collection>");
            Iterator iterator = hashSet.iterator();
            while (iterator.hasNext()) {
                Object object = iterator.next();
                printWriter.println("   <class>" + object.toString() + "</class>");
            }
            printWriter.println("</collection>");
            return "Сохранение выполена";
        } catch (FileNotFoundException e) {
            return "Файл не найден, введите \"ок\" для продолжения";
        }
    }

    /**
     * Метод выводит информацию о коллекции: тип, дату создания, размер
     */
    private String info() {
        return "Type: HashSet" + "\n" + "Initialization date: " + date + "\n" + "Size: " + hashSet.size() + "\n Команда выполнена";
    }

    /**
     * Метод принимает String- массив имен объектов для удаления и удаляет объекты коллекции, если их имена
     * совпадают с именем в массиве
     *
     * @param line String- массив имен удаляемых эллементов
     */
    private String remove(String[] line) {

        boolean check = false;
        try {
            for (String string : line) {
                for (Show show : hashSet) {
                    if (show.name.equals(string.split("\"")[1])) {
                        remove(show);
                        check = true;
                    }
                }
            }
            if (!check) {
                return "Объект не существует";
            }
            return "Команда выполнена";
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Неверный формат команды";
        }
    }

    /**
     * Метод удаляет элемент коллекции
     *
     * @param object элемент коллекции для удаления
     */
    String remove(Show object) {
        hashSet.remove(object);
        db.deleteShow(object, author);
        save(false);
        return "Команда выполена";
    }

    /**
     * Метод выводит имеющиеся команды с пояснениями
     */
    private String help() {
        return "add {\"name\":\"name\", \"rating\":\"rating\", \"theme\":\"THEME\"} - добавление эллемента в коллекцию\n" +
                "remove {name}- удаление эллемента из коллекции\n" +
                "removeAll- удаление всех эллементов\n" +
                "save- сохранение изменений\n" +
                "show- показать эллементы коллекции\n" +
                "info- показать информацию о коллекции\n" +
                "load- загрузить коллекцию из XML\n";
    }
    String[][] getMassive(){
        System.out.println(db.getMassive().length);
        return db.getMassive();
    }
}
