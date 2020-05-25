import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import java.io.*;


public class SeedChoose extends JLayeredPane implements MouseMotionListener {
    private Image bgImage;

    private Image cardplaceImage;
    private Image seedchooseImage;
    private Image beginImage;

    public SeedCardpre[] place;
    public SeedCardpre[] preplace;
    public SeedCardpre beginbutton;

    public int k=0;

    private Timer redrawTimer;
    
    public SeedChoose(){
        addMouseMotionListener(this);
        setSize(1400, 600);
        setLayout(null);
    //    addMouseMotionListener(this);
        setVisible(true);

        bgImage = new ImageIcon(this.getClass().getResource("images/background1.png")).getImage();

        cardplaceImage  = new ImageIcon(this.getClass().getResource("images/cardplace1.png")).getImage();
        seedchooseImage = new ImageIcon(this.getClass().getResource("images/SeedChooser.png")).getImage();
        beginImage = new ImageIcon(this.getClass().getResource("images/begingame.png")).getImage();

        beginbutton = new SeedCardpre();
        beginbutton.setAction(0, 0, 0, "images/begingame.png","", (ActionEvent e) ->{
            if(k==9){
                GameWindow.begingame();
            }
        });
        beginbutton.setSize(113, 41);
        beginbutton.setLocation(0,0);
        add(beginbutton,new Integer(0));

        place = new SeedCardpre[9];
        preplace = new SeedCardpre[40];
        

        for(int i=0;i<9;i++) place[i]=new SeedCardpre();
        for(int i=0;i<40;i++) preplace[i]=new SeedCardpre();


        for(int i=0;i<9;i++){
            SeedCardpre a = new SeedCardpre();
            a.setAction(i,418+65*i, 8,null,null, (ActionEvent e) -> {
                if(a.getImage()!=null){
                    preplace[a.reid].setImage(a.getImage());
                    preplace[a.reid].setname(a.getname());
                    a.setImage(null);
                    a.setname(null);
                    for(int j=a.id;j<k;++j){
                        if(j==8){
                            place[j].setImage(null);
                            place[j].setname(null);
                            place[j].reid=0;
                            break;
                        }
                        place[j].setImage(place[j+1].getImage());
                        place[j].setname(place[j+1].getname());
                        place[j].reid=place[j+1].reid;
                        place[j+1].setImage(null);
                        place[j+1].setname(null);
                    }
                    k--;
                }
            });
            a.setLocation(418+65*i, 8);
            place[i] = a;
            add(place[i],new Integer(0));
        }

        
        for(int i=0;i<40;i++){
            SeedCardpre a = new SeedCardpre();
            if(i==0)
            a.setAction(i,337+65*(i%10),135+(i/10)*90, "images/cards/card_sunflower.png","sunflower", (ActionEvent e) -> {
                if(a.getImage()!=null&&k<9){
                    place[k].setImage(a.getImage());
                    place[k].setname(a.getname());
                    place[k].reid=a.id;
                    a.setImage(null);
                    a.setname(null);
                    k++;
                }
            });
            
            if(i==1)
            a.setAction(i,337+65*(i%10),135+(i/10)*90, "images/cards/card_peashooter.png","peashooter", (ActionEvent e) -> {
                if(a.getImage()!=null&&k<9){
                    place[k].setImage(a.getImage());
                    place[k].setname(a.getname());
                    place[k].reid=a.id;
                    a.setImage(null);
                    a.setname(null);
                    k++;
                }
            });

            if(i==2)
            a.setAction(i,337+65*(i%10),135+(i/10)*90, "images/cards/card_freezepeashooter.png","freezepeashooter", (ActionEvent e) -> {
                if(a.getImage()!=null&&k<9){
                    place[k].setImage(a.getImage());
                    place[k].setname(a.getname());
                    place[k].reid=a.id;
                    a.setImage(null);
                    a.setname(null);
                    k++;
                }
            });

            if(i==3)
            a.setAction(i,337+65*(i%10),135+(i/10)*90, "images/cards/card_twicepeashooter.png","twicepeashooter", (ActionEvent e) -> {
                if(a.getImage()!=null&&k<9){
                    place[k].setImage(a.getImage());
                    place[k].setname(a.getname());
                    place[k].reid=a.id;
                    a.setImage(null);
                    a.setname(null);
                    k++;
                }
            });

            if(i==4)
            a.setAction(i,337+65*(i%10),135+(i/10)*90, "images/cards/card_threepeashooter.png","threepeashooter", (ActionEvent e) -> {
                if(a.getImage()!=null&&k<9){
                    place[k].setImage(a.getImage());
                    place[k].setname(a.getname());
                    place[k].reid=a.id;
                    a.setImage(null);
                    a.setname(null);
                    k++;
                }
            });

            if(i==5)
            a.setAction(i,337+65*(i%10),135+(i/10)*90, "images/cards/card_torchwood.png","torchwood", (ActionEvent e) -> {
                if(a.getImage()!=null&&k<9){
                    place[k].setImage(a.getImage());
                    place[k].setname(a.getname());
                    place[k].reid=a.id;
                    a.setImage(null);
                    a.setname(null);
                    k++;
                }
            });

            if(i==6)
            a.setAction(i,337+65*(i%10),135+(i/10)*90, "images/cards/card_wallnut.png","wallnut", (ActionEvent e) -> {
                if(a.getImage()!=null&&k<9){
                    place[k].setImage(a.getImage());
                    place[k].setname(a.getname());
                    place[k].reid=a.id;
                    a.setImage(null);
                    a.setname(null);
                    k++;
                }
            });

            if(i==7)
            a.setAction(i,337+65*(i%10),135+(i/10)*90, "images/cards/card_chomper.png","chomper", (ActionEvent e) -> {
                if(a.getImage()!=null&&k<9){
                    place[k].setImage(a.getImage());
                    place[k].setname(a.getname());
                    place[k].reid=a.id;
                    a.setImage(null);
                    a.setname(null);
                    k++;
                }
            });

            if(i==8)
            a.setAction(i,337+65*(i%10),135+(i/10)*90, "images/cards/card_potatomine.png","potatomine", (ActionEvent e) -> {
                if(a.getImage()!=null&&k<9){
                    place[k].setImage(a.getImage());
                    place[k].setname(a.getname());
                    place[k].reid=a.id;
                    a.setImage(null);
                    a.setname(null);
                    k++;
                }
            });

            if(i==9)
            a.setAction(i,337+65*(i%10),135+(i/10)*90, "images/cards/card_gatling.png","gatling", (ActionEvent e) -> {
                if(a.getImage()!=null&&k<9){
                    place[k].setImage(a.getImage());
                    place[k].setname(a.getname());
                    place[k].reid=a.id;
                    a.setImage(null);
                    a.setname(null);
                    k++;
                }
            });
            
            if(i==10)
            a.setAction(i,337+65*(i%10),135+(i/10)*90, "images/cards/card_cherrybomb.png","cherrybomb", (ActionEvent e) -> {
                if(a.getImage()!=null&&k<9){
                    place[k].setImage(a.getImage());
                    place[k].setname(a.getname());
                    place[k].reid=a.id;
                    a.setImage(null);
                    a.setname(null);
                    k++;
                }
            });

            if(i==11)
            a.setAction(i,337+65*(i%10),135+(i/10)*90, "images/cards/card_tallwallnut.png","tallwallnut", (ActionEvent e) -> {
                if(a.getImage()!=null&&k<9){
                    place[k].setImage(a.getImage());
                    place[k].setname(a.getname());
                    place[k].reid=a.id;
                    a.setImage(null);
                    a.setname(null);
                    k++;
                }
            });

            if(i>12)
            a.setAction(i,337+65*(i%10),135+(i/10)*90,null,null, (ActionEvent e) -> {
                if(a.getImage()!=null&&k<9){
                    place[k].setImage(a.getImage());
                    place[k].setname(a.getname());
                    place[k].reid=a.id;
                    a.setImage(null);
                    a.setname(null);
                    k++;
                }
            });

            a.setLocation(337+65*(i%10),135+(i/10)*90);
            preplace[i]=a;
            add(preplace[i],new Integer(0));
        }


        redrawTimer = new Timer(5, (ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();

    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null);

        g.drawImage(cardplaceImage, 322, 0, null);
        g.drawImage(seedchooseImage, 322, 110, null);

        for(int i=0;i<9;i++) {
            g.drawImage(place[i].getImage(), place[i].getX(), place[i].getY(), null);
        }

        for(int i=0;i<40;i++) {
            g.drawImage(preplace[i].getImage(), preplace[i].getX(), preplace[i].getY(), null);
        }
        
        g.drawImage(beginbutton.getImage(), beginbutton.getX(), beginbutton.getY(), null);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    public String[] getplace(){
        String[] na = new String[9];
        for(int i=0;i<9;++i){
            na[i]=place[i].getname();
        }
        return na;
    }
}