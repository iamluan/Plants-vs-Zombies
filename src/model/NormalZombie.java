package model;

public class NormalZombie extends Zombie {

    public NormalZombie(int x, int y, int lane) {
        super(x, y,"file:resource/image/zombie_normal.gif" , 7, 1, lane);
        imagePath = "file:resource/image/zombie_normal.gif";
    }
}
