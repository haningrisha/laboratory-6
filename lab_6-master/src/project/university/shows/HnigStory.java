package project.university.shows;


import project.university.game.Hnigl;
import project.university.game.Interests;
import project.university.game.UnrealPeople;

import java.util.Objects;

public class HnigStory extends Show {
    public HnigStory(int rating){
        super("HnigStory", rating, Interests.SPACE);
    }
    private Hnigl hnigl = new Hnigl();

    @Override
    public void initialPlay() {
        System.out.println("Начинается передача о капитане Хныгле");
    }
    @Override
    public void play() {

        class Space {
            private boolean gravity = false;

            private void start(UnrealPeople person) {
                acceptIntoGravityField(person);
                person.start();
            }

            private void acceptIntoGravityField(UnrealPeople person) {
                if (!gravity) {
                    person.fly();
                }
            }
        }
        Space space = new Space();
        space.start(hnigl);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        HnigStory hnigStory = (HnigStory) o;
        return Objects.equals(hnigl, hnigStory.hnigl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hnigl);
    }

}
