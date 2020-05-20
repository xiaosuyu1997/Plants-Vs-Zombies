
import javax.swing.*;
import java.awt.event.ActionEvent;


public class ThreePeashooter extends Plant{
    public Timer shootTimer;

    public ThreePeashooter(GamePanel parent, int x, int y) {
        super(parent, x, y);
        setImage(new ImageIcon(this.getClass().getResource("images/plants/threepeashooter.gif")).getImage());
        shootTimer = new Timer(2000, (ActionEvent e) -> {
            //System.out.println("SHOOT");
            if ((y!=0&&y!=4)) {
                if(getGp().getLaneZombies().get(y).size() > 0||
                    getGp().getLaneZombies().get(y-1).size() > 0||
                    getGp().getLaneZombies().get(y+1).size() > 0){
                        getGp().getLanePeas().get(y-1).add(new Pea(getGp(), y-1, 103 + this.getX() * 100));
                        getGp().getLanePeas().get(y).add(new Pea(getGp(), y, 103 + this.getX() * 100));
                        getGp().getLanePeas().get(y+1).add(new Pea(getGp(), y+1, 103 + this.getX() * 100));
                }
            }
            else if(y==0){
                if(getGp().getLaneZombies().get(y).size() > 0||getGp().getLaneZombies().get(y+1).size() > 0){
                    getGp().getLanePeas().get(y+1).add(new Pea(getGp(), y+1, 103 + this.getX() * 100));
                    getGp().getLanePeas().get(y).add(new Pea(getGp(), y, 103 + this.getX() * 100));
                }
            }
            else if(y==4){
                if(getGp().getLaneZombies().get(y-1).size() > 0||getGp().getLaneZombies().get(y).size() > 0){
                    getGp().getLanePeas().get(y-1).add(new Pea(getGp(), y-1, 103 + this.getX() * 100));
                    getGp().getLanePeas().get(y).add(new Pea(getGp(), y, 103 + this.getX() * 100));
                }
            }
        });
        shootTimer.start();
    }

    @Override
    public void stop() {
        super.stop();
        shootTimer.stop();
    }
}