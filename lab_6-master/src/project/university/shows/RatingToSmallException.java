package project.university.shows;

import project.university.console.View;

public class RatingToSmallException extends Exception{
    public RatingToSmallException(String show){
        super("Слишком маленький рейтинг");
        try {
            View.box.save(false);
        }catch (NullPointerException e){

        }
        System.err.println(RatingToSmallException.this.toString() + " Слишком маленький рейтинг " + show);
    }

    @Override
    public String toString() {
        return "RatingToSmallException{}";
    }
}
