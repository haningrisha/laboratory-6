package project.university.shows;

import project.university.console.Author;
import project.university.game.Interests;
import project.university.game.Play;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

public class Show implements Play, Serializable, Comparable<Show> {
    private ZonedDateTime creation_time = ZonedDateTime.now();
    Author author = new Author("admin");
    public String name;
    int rating;
    Interests theme;
    public Show(String name, int rating, Interests theme){
        this.name = name;
        try { this.rating = rating;
            if (rating > 100){
                throw new RatingToMuchException(Show.this.toString());
            }
            if (rating < 0){
                throw new RatingToSmallException(Show.this.toString());
            }
        }catch (RatingToMuchException e){
            this.rating = 100;
            System.out.println("Рейтинг шоу " + Show.this.toString() + " понижается до 100");
        }catch (RatingToSmallException e ){
            this.rating = 0;
            System.out.println("Ретинг шоу " + Show.this.toString() + " повышен до 0");
        }
        this.theme = theme;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public ZonedDateTime getCreation_time(){
        return creation_time;
    }

    public int getRating() {
        return rating;
    }

    public Interests getTheme() {
        return theme;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void play(){}

    public void initialPlay(){}

    @Override
    public int compareTo(Show o) {
        return this.name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Show)) return false;
        Show show = (Show) o;
        return show.name.equals(name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating, theme);
    }

    @Override
    public String toString() {
        return "{\"name\":\"" + name  + "\", \"rating\":\"" + rating + "\", \"theme\":\"" + theme + "\""+", author:\""+ author.getLogin() +"}";
    }
}

