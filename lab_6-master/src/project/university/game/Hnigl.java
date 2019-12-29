package project.university.game;


import java.io.Serializable;
import java.util.Objects;

public class Hnigl extends UnrealPeople implements Serializable {
    private static boolean flyAbility = false;//не состояние хныгля
    private static int weight = 90;

    class Rifle implements Serializable{
        int force;
        Rifle(int force){
            this.force = force;
        }
    }

    private Rifle rifle = new Rifle(10);

    @Override
    public void fly(){
        flyAbility = true;
    }
    @Override
    public void start(){
        rifleShot(rifle);
    }
    private void rifleShot(Rifle rifle){
        System.out.println("Хныгль стреляет из ружья");
        if (flyAbility){
            Flight flight = new Flight(){
                public int aroundEarth(int weight, int force){
                    int time = weight * force / 30;
                    System.out.println("Хныгль облетает вокруг земли");
                    System.out.println("Время полёта: " + time + " минут");
                    return time;
                }
            };
            if (flight.aroundEarth(weight, rifle.force) < 60){
                supernatural();
            }
        }
    }

    private void supernatural(){
        System.out.println("Всех удивил");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hnigl hnigl = (Hnigl) o;
        return Objects.equals(rifle, hnigl.rifle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rifle);
    }

    @Override
    public String toString() {
        return "Hnigl{" +
                "rifle=" + rifle +
                '}';
    }
}
// поля должны быть private
// не обращаться к полям напрямую