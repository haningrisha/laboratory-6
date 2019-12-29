package project.university.game;

import java.util.Objects;

abstract class RealPeople {
    Interests interest;
    RealPeople(Interests interest){
        this.interest = interest ;

    }

    public Interests getInterest() {
        return interest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RealPeople)) return false;
        RealPeople that = (RealPeople) o;
        return interest == that.interest;
    }

    @Override
    public int hashCode() {
        return Objects.hash(interest);
    }

    @Override
    public String toString() {
        return "RealPeople{" +
                "interest=" + interest +
                '}';
    }
}
