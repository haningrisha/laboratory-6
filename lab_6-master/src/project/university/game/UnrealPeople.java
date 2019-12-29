package project.university.game;


import project.university.game.Fly;

public abstract class UnrealPeople implements Fly {
    @Override
    public void fly(){}
    public void start(){}

    @Override
    public String toString() {
        return "UnrealPeople{}";
    }

}

