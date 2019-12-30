package model;

public class Zombie {
    public int health;
    public boolean isMoving = true;
    public static int numberOfZombie = 0;
    public int lane;

    public Zombie(int lane) {
        this.lane = lane;
    }

    public int getHealth() {
        return this.health;
    }
}
