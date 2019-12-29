package project.university.game;


public class Viewers extends RealPeople {

    Viewers(Interests interest){
        super(interest);
    }

    @Override
    public String toString() {
        return "Viewers{" +
                "interest=" + interest +
                '}';
    }
}
