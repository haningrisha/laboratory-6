package project.university.shows;

import project.university.game.Interests;

public class Dance extends Show {
    public Dance(int rating) {
        super("Dance", rating, Interests.DANCE);
    }
    @Override
    public void initialPlay(){
        System.out.println("Послышалась музыка");
        System.out.println("На экране появляются танцующие пары");
        System.out.println("Пары танцуют");
    }

    @Override
    public void play() {
        System.out.println("Жури восхищены танцом");
    }

}
