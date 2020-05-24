import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Armin on 6/25/2016.
 */
public class GameWindow extends JFrame {

    enum PlantType {
        None,
        Sunflower,
        Peashooter,
        FreezePeashooter,
        Torchwood,
        TwicePeashooter,
        ThreePeashooter,
        Chomper,
        Wallnut,
        GatlingPea,
        PotatoMine,
        CherryBomb,
        Sholve
    }
    
    public PlantCard Sunflower,Peashooter,FreezePeashooter,Torchwood,
        TwicePeashooter,ThreePeashooter,Chomper,Wallnut,GatlingPea,PotatoMine,CherryBomb,Sholve;
    //PlantType activePlantingBrush = PlantType.None;

    public SeedChoose aSeedChoose;

    public GameWindow(String[] name) {
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel sun = new JLabel("SUN");
        sun.setLocation(37, 80);
        sun.setSize(60, 20);

        GamePanel gp = new GamePanel(this,sun);
        gp.setLocation(0, 0);
        getLayeredPane().add(gp, new Integer(0));

        for(int i=0;i<9;++i){
            if(name[i]=="sunflower"){
                Sunflower = new PlantCard("images/cards/card_sunflower.png");
                Sunflower.setAction(110+65*i, 8,7500,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Sunflower);
                });
                getLayeredPane().add(Sunflower, new Integer(3));
            }
            if(name[i]=="peashooter"){
                Peashooter = new PlantCard("images/cards/card_peashooter.png");
                Peashooter.setAction(110+65*i, 8,7500,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Peashooter);
                });
                getLayeredPane().add(Peashooter, new Integer(3));
            }
            if(name[i]=="freezepeashooter"){
                FreezePeashooter = new PlantCard("images/cards/card_freezepeashooter.png");
                FreezePeashooter.setAction(110+65*i, 8,7500,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.FreezePeashooter);
                });
                getLayeredPane().add(FreezePeashooter, new Integer(3));
            }
            if(name[i]=="twicepeashooter"){
                TwicePeashooter = new PlantCard("images/cards/card_twicepeashooter.png");
                TwicePeashooter.setAction(110+65*i, 8,7500,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.TwicePeashooter);
                });
                getLayeredPane().add(TwicePeashooter, new Integer(3));
            }
            if(name[i]=="threepeashooter"){
                ThreePeashooter = new PlantCard("images/cards/card_threepeashooter.png");
                ThreePeashooter.setAction(110+65*i,8,7500,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.ThreePeashooter);
                });
                getLayeredPane().add(ThreePeashooter, new Integer(3));
            }
            if(name[i]=="torchwood"){
                Torchwood = new PlantCard("images/cards/card_torchwood.png");
                Torchwood.setAction(110+65*i, 8,7500,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Torchwood);
                });
                getLayeredPane().add(Torchwood, new Integer(3));
            }
            if(name[i]=="wallnut"){
                Wallnut = new PlantCard("images/cards/card_wallnut.png");
                Wallnut.setAction(110+65*i, 8,30000,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Wallnut);
                });
                getLayeredPane().add(Wallnut, new Integer(3));
            }
            if(name[i]=="chomper"){
                Chomper = new PlantCard("images/cards/card_chomper.png");
                Chomper.setAction(110+65*i, 8,7500,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.Chomper);
                });
                getLayeredPane().add(Chomper, new Integer(3));
            }
            if(name[i]=="potatomine"){
                PotatoMine = new PlantCard("images/cards/card_potatomine.png");
                PotatoMine.setAction(110+65*i, 8,30000,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.PotatoMine);
                });
                getLayeredPane().add(PotatoMine, new Integer(3));
            }
            if(name[i]=="gatling"){
                GatlingPea = new PlantCard("images/cards/card_gatling.png");
                GatlingPea.setAction(110+65*i, 8,50000,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.GatlingPea);
                });
                getLayeredPane().add(GatlingPea, new Integer(3));
            }

            if(name[i]=="cherrybomb"){
                CherryBomb = new PlantCard("images/cards/card_cherrybomb.png");
                CherryBomb.setAction(110+65*i, 8,30000,(ActionEvent e) -> {
                    gp.setActivePlantingBrush(PlantType.CherryBomb);
                });
                getLayeredPane().add(CherryBomb, new Integer(3));
            }
        }

        Sholve = new PlantCard("images/cards/ShovelBack.png");
        Sholve.setSize(100,50);
        Sholve.setAction(760, 0,0,(ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Sholve);
        });
        getLayeredPane().add(Sholve, new Integer(3));
        
        getLayeredPane().add(sun, new Integer(2));
        setResizable(false);
        setVisible(true);
    }

    public GameWindow(int ii){
        setSize(1400, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        aSeedChoose = new SeedChoose();
        getLayeredPane().add(aSeedChoose, new Integer(0));
    }

    public GameWindow(boolean b) {
        Menu menu = new Menu();
        menu.setLocation(0, 0);
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getLayeredPane().add(menu, new Integer(0));
        menu.repaint();
        setResizable(false);
        setVisible(true);
    }

    static GameWindow gw;
    static GameWindow gw1;

    public static void begin() {
        gw.dispose();
        gw = new GameWindow(1);
    }

    public static void begingame(){
        gw.dispose();
        gw = new GameWindow(gw.aSeedChoose.getplace());
    }

    public static void main(String[] args) {
        gw = new GameWindow(true);
    }
}
