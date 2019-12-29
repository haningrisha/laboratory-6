package project.university.shows;

import project.university.game.Interests;
import project.university.shows.Show;

public class News extends Show {
    public News(int rating, Interests interest) {
        super("News", rating, interest);
    }

    @Override
    public void initialPlay(){
        System.out.println("Новости начинаются");
    }
    @Override
    public void play(){
        if (theme == Interests.SPACE){
            makeNews("космонавтах");
            makeNews("гигантских растениях");
            makeNews("невесомости");
        }
        if (theme == Interests.DANCE){
            makeNews("лысине Дружинина");
            makeNews("голосе Мигеля");
            makeNews("этой третьей, которая пришла на смену Бузовой");
        }

    }
    private void makeNews(String news){
        System.out.println("Новости передают о " + news);

    }

}
