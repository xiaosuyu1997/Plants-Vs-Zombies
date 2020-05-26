import java.awt.*;
import javax.swing.*;

/**
 * Created by Armin on 6/28/2016.
 */
public class FreezePea extends Pea {
	private SoundEffect frozenpea = new SoundEffect("./src/bgms/FrozenPea.wav");
    public FreezePea(GamePanel parent, int lane, int startX) {
        super(parent, lane, startX);
        setImage(new ImageIcon(this.getClass().getResource("images/freezepea.png")).getImage());
        frozenpea.prepare();
    }
    
    @Override
    public void advance() {
        Rectangle pRect = new Rectangle(getPosX(), 130 + getMyLane() * 120, 28, 28);
        for (int i = 0; i < gp.getLaneZombies().get(getMyLane()).size(); i++) {
            Zombie z = gp.getLaneZombies().get(getMyLane()).get(i);
            Rectangle zRect = new Rectangle(z.getPosX(), 109 + getMyLane() * 120, 400, 120);
            if (pRect.intersects(zRect)) {
            	frozenpea.player.start();
                z.setHealth(z.getHealth() - 250);
                z.slow();
                boolean exit = false;
                if (z.getHealth() < 0) {
                    System.out.println("ZOMBIE DIE");
                    GamePanel.setProgress(10);
                    gp.getLaneZombies().get(getMyLane()).remove(i);
                }
                exit = true;
                gp.getLanePeas().get(getMyLane()).remove(this);
                if (exit) break;
            }
        }
        if(getPosX() > 2000){
            gp.getLanePeas().get(getMyLane()).remove(this);
        }
        setPosX(getPosX() + 15);
    }

}
