import java.awt.Image;

import javax.swing.ImageIcon;

/**
 * Created by Armin on 6/29/2016.
 */
public class ConeHeadZombie extends Zombie {
	
    private Image coneHeadZombieImage;
    private Image coneHeadZombieAttackImage;
    private Image coneHeadZombieHurtAttackImage;
    private Image currentImage;
    
    public ConeHeadZombie(GamePanel parent, int lane) {
        super(parent, lane);
        setHealth(2500);
        
        coneHeadZombieImage = new ImageIcon(this.getClass().getResource("images/zombies/coneheadzombie/ConeheadZombie.gif")).getImage();
        coneHeadZombieAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/coneheadzombie/ConeheadZombieAttack.gif")).getImage();
        coneHeadZombieHurtAttackImage = new ImageIcon(this.getClass().getResource("images/zombies/coneheadzombie/ConeheadZombieAttack2.gif")).getImage();
    }
}
