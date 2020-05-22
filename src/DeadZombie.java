import javax.swing.*;
import java.awt.*;

public class DeadZombie extends Zombie{
    private int time_span;
    public Image deadZombieImage;
    public DeadZombie(GamePanel parent, int lane, int posX) {
        super(parent, lane);
        setPosX(posX);
        time_span = 350;
        deadZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/BoomDie.gif")).getImage();
        deadZombieImage.flush();
        // System.out.printf("here is a dead zombie\n");
    }
    public boolean lifespanDecrease()
    {
        time_span -= 1;
        return time_span < 0;
    }
}