import java.awt.Graphics;
import java.awt.Image;

import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.util.Timer;
import java.util.TimerTask;

public class FootballZombie  extends Zombie {
    public FootballZombie(GamePanel parent, int lane) {
        super(parent, lane);
        setHealth(5400);
        setSpeed(3);
    }
}
