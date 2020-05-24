import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class CherryBomb extends Plant{
    public Timer BombTimer;
    public Image ima;

    public CherryBomb(GamePanel parent, int x, int y,int k){
        super(parent, x, y);
        setHealth(1000);
        if(k==1){
            setImage(new ImageIcon(this.getClass().getResource("images/plants/CherryBomb.gif")).getImage());
            BombTimer = new Timer(630, (ActionEvent e) ->{
                int x1 = 60 + (x % 9) * 100;
                getGp().getColliders()[x + y * 9].removePlant();
                getGp().getColliders()[x + y * 9].setPlant(new CherryBomb(getGp(),x,y,2));

                for(int i=0;i<getGp().getLaneZombies().get(y).size(); i++) {
                    if(getGp().getLaneZombies().get(y).get(i).getPosX()>=x1-255&&
                        getGp().getLaneZombies().get(y).get(i).getPosX()<x1+255){
                            getGp().getLaneZombies().get(y).get(i).setHealth(0);
                    }
                }
                if(y!=0){
                    for(int i=0;i<getGp().getLaneZombies().get(y-1).size(); i++) {
                        if(getGp().getLaneZombies().get(y-1).get(i).getPosX()>=x1-255&&
                            getGp().getLaneZombies().get(y-1).get(i).getPosX()<x1+255){
                                getGp().getLaneZombies().get(y-1).get(i).setHealth(0);
                        }
                    }
                }
                if(y!=4){
                    for(int i=0;i<getGp().getLaneZombies().get(y+1).size(); i++) {
                        if(getGp().getLaneZombies().get(y+1).get(i).getPosX()>=x1-255&&
                            getGp().getLaneZombies().get(y+1).get(i).getPosX()<x1+255){
                                getGp().getLaneZombies().get(y+1).get(i).setHealth(0);
                        }
                    }
                }
            });
        }
        else{
            ima = new ImageIcon(this.getClass().getResource("images/plants/Boom.gif")).getImage();
            ima.flush();
            setImage(ima);
            BombTimer = new Timer(1300, (ActionEvent e) ->{
                getGp().getColliders()[x + y * 9].removePlant();
            });
        }
        BombTimer.start();
    }

    @Override
    public void stop() {
        super.stop();
        BombTimer.stop();
    }
}