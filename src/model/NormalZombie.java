package model;

public class NormalZombie extends Zombie {

    public NormalZombie(int x, int y, int lane) {
        super(x, y, 7, 1, lane);
        this.imagePath = "src/resource/image/zombie_normal.gif";
    }
}
