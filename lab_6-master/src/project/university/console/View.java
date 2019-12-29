package project.university.console;

import project.university.game.Viewers;
import project.university.shows.RatingToMuchException;
import project.university.shows.RatingToSmallException;
import project.university.shows.Show;

import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

public class View {
    private int quantityShow;
    private String dateHashSet;
    public static Box box = new Box();
    public View(int quantityShow){
        this.quantityShow = quantityShow;
        Date date = new Date();
        dateHashSet = date.toString();
    }
    public String getDateHashSet(){
        return dateHashSet;
    }
    private HashSet<Show> shows = new HashSet<>(quantityShow);

    public HashSet getHashSet(){
        return shows;
    }

    private static Viewers ourViewer;
    private static int countShow = 0;
    public void addShow(Show show){
        shows.add(show);
        box.setCollection(shows, true);
        box.save(false);
        countShow++;
    }

    public void addViewers(Viewers viewers){
        ourViewer = viewers;
    }

    public void start(){
        if (countShow < quantityShow) {
            throw new QuantityShowTooMuchException(shows);
        }
        findChanel(shows);

    }

    private static void findChanel(HashSet<Show> shows){

        Iterator<Show> i = shows.iterator();
        while (i.hasNext()){
            Show currentShow = i.next();
            if (currentShow.getRating() > 50){
                currentShow.initialPlay();
                if (suitability(ourViewer, currentShow)){
                    currentShow.play();
                }
            }
        }
    }

    private static boolean suitability(Viewers viewers, Show show){ // метод определиния соответствия программы ключевой аудиториии
    try{
        if (viewers.getInterest() == show.getTheme()){

            show.setRating(show.getRating() + 10);
            if (show.getRating() > 100){
                throw new RatingToMuchException(show.toString());
            }
            System.out.println("Передача " + show.toString() + " нравится " + "рейтинг шоу вырос до "+ show.getRating());
            return true;
        }
        else{
            show.setRating(show.getRating() - 10);
            if (show.getRating() < 0){
                throw new RatingToSmallException(show.toString());
            }
            System.out.println("Передача " + show.toString() + " не нравится" + " рейтинг шоу упал до " + show.getRating());
            return false;
        }}catch (RatingToMuchException e){
        show.setRating(100);
        System.out.println("Рейтинг передачи "+ show.toString() +" понижается до " + show.getRating());
        return true;
    }catch (RatingToSmallException e){
        show.setRating(0);
        System.out.println("Рейтинг передачи " + show.toString() +" повышается до " + show.getRating());
        return false;
    }
    }

    @Override
    public String toString() {
        return "View{}";
    }
}

class QuantityShowTooMuchException extends RuntimeException{
    QuantityShowTooMuchException(HashSet<Show> shows) {
        try{
            View.box.save(false);
        }
        catch (NullPointerException e){

        }
        System.err.println(QuantityShowTooMuchException.this.toString() + "Шоу меньше, чем было запланировано");
        System.exit(0);
    }

    @Override
    public String toString() {
        return "QuantityShowTooMuchException{}";
    }
}
