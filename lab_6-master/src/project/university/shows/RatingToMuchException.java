package project.university.shows;

import project.university.console.View;

public class RatingToMuchException extends Exception{
    public RatingToMuchException(String show) {
        super("Слишком большой рейтинг");
        try {
            View.box.save(false);
        }catch (NullPointerException e){

        }

        System.err.println(RatingToMuchException.this.toString() + " Слишком большой рейтинг у шоу " + show);
    }

    @Override
    public String toString() {
        return "RatingToMuchException{}";
    }
}
