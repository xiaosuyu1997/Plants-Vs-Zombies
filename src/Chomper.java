import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.imageio.*;
import java.awt.image.BufferedImage;

public class Chomper extends Plant{
    public Timer shootTimer;
    private Image Ima;
    private Image chomperImage;
    private Image chomperattackImage;
    private Image chomperdigestImage;

    public Chomper(GamePanel parent, int x, int y,int k,int Zombiex){
        super(parent, x, y);
        setHealth(100);
        chomperImage = new ImageIcon(this.getClass().getResource("images/plants/Chomper.gif")).getImage();
        chomperattackImage = new ImageIcon(this.getClass().getResource("images/plants/ChomperAttack.gif")).getImage();
        chomperdigestImage = new ImageIcon(this.getClass().getResource("images/plants/ChomperDigest.gif")).getImage();
        
        if(k==1){
            Ima=chomperImage;
            shootTimer = new Timer(0, (ActionEvent e) ->{
                if (getGp().getLaneZombies().get(y).size() > 0){
                    int x1 = 60 + (x % 9) * 100;
                    int idnow=0;
                    int zx=1000;
                    boolean has=false;
                    for (int i = 0; i < getGp().getLaneZombies().get(y).size(); i++){
                        if(getGp().getLaneZombies().get(y).get(i).getPosX()>=x1&&
                        getGp().getLaneZombies().get(y).get(i).getPosX()<x1+155){
                            has=true;
                            int nowx=getGp().getLaneZombies().get(y).get(i).getPosX()-x1;
                            if(nowx<zx){
                                zx=nowx;
                                idnow=i;
                            }
                        }
                    }
                    
                    if(has){
                        getGp().getColliders()[x + y * 9].removePlant();
                        getGp().getColliders()[x + y * 9].setPlant(new Chomper(getGp(),x,y,2,idnow));
                    }
                }
            });
        }
        if(k==2){
            Ima=chomperattackImage;
            shootTimer = new Timer(810, (ActionEvent e)->{
                Zombie z = getGp().getLaneZombies().get(y).get(Zombiex);
                getGp().getColliders()[x + y * 9].removePlant();
                getGp().getColliders()[x + y * 9].setPlant(new Chomper(getGp(),x,y,3,0));
                z.setHealth(0);
            });
        }
        if(k==3){
            Ima=chomperdigestImage;
            shootTimer = new Timer(30000, (ActionEvent e)->{
                getGp().getColliders()[x + y * 9].removePlant();
                getGp().getColliders()[x + y * 9].setPlant(new Chomper(getGp(),x,y,1,0));
            });
        }
        shootTimer.start();
    }

    @Override
    public Image getImage(){
        return Ima;
    }

    @Override
    public void stop() {
        shootTimer.stop();
    }
}