package model;

public class NormalZombie extends Zombie {

    public NormalZombie(int x, int y, int lane) {
        super(x, y,"src/resource/image/zombie_normal.gif" , 7, 1, lane);
        imagePath = "src/resource/image/zombie_normal.gif";
    }
}
