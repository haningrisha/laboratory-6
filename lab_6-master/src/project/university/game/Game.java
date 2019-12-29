package project.university.game;

import project.university.shows.News;
import project.university.console.View;
import project.university.game.Viewers;
import project.university.shows.Dance;
import project.university.shows.HnigStory;
import project.university.shows.Show;

import java.util.HashSet;

public class Game {
    View view;
    public Game(View view){
        this.view = view;
    }
    public void start(){

        Viewers viewers = new Viewers(Interests.SPACE);
        view.addViewers(viewers);

        Dance dance = new Dance(110);
        view.addShow(dance);
        News news = new News(80,Interests.SPACE);
        view.addShow(news);
        HnigStory hnigStory = new HnigStory(90);
        view.addShow(hnigStory);
        view.start();
    }
    public HashSet<Show> getHashSet(){
        return view.getHashSet();
    }
}
