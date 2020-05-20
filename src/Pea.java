import java.awt.*;
import javax.swing.*;
/**
 * Created by Armin on 6/25/2016.
 */
public class Pea {

    private int posX;
    protected GamePanel gp;
    private int myLane;
    private Image Ima=null;

    private SoundEffect pea = new SoundEffect("./src/bgms/Pea.wav");
    
    
    public Pea(GamePanel parent, int lane, int startX) {
        this.gp = parent;
        this.myLane = lane;
        posX = startX;
        Ima = new ImageIcon(this.getClass().getResource("images/pea.png")).getImage();
        
        pea.prepare();
    }

    public void advance() {
        Rectangle pRect = new Rectangle(posX, 130 + myLane * 120, 28, 28);
        for (int i = 0; i < gp.getLaneZombies().get(myLane).size(); i++) {
            Zombie z = gp.getLaneZombies().get(myLane).get(i);
            Rectangle zRect = new Rectangle(z.getPosX(), 109 + myLane * 120, 400, 120);
            boolean exit = false;
            if (pRect.intersects(zRect)) {
            	pea.player.start();
                z.setHealth(z.getHealth() - 200);
                if (z.getHealth() < 0) {
                    System.out.println("ZOMBIE DIE");
                    GamePanel.setProgress(10);
                    gp.getLaneZombies().get(getMyLane()).remove(i);
                }
                exit = true;
                gp.getLanePeas().get(myLane).remove(this);
                if (exit) break;
            }
        }
        /*if(posX > 2000){
            gp.lanePeas.get(myLane).remove(this);
        }*/
        posX += 15;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getMyLane() {
        return myLane;
    }

    public void setMyLane(int myLane) {
        this.myLane = myLane;
    }

    public Image getImage(){
        return Ima;
    }
    
    public void setImage(Image im){
        Ima=im;
    }
}
