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
        Sholve
    }
    public PlantCard sunflower,peashooter,freezepeashooter,Torchwood,
        TwicePeashooter,ThreePeashooter,Chomper,Wallnut,GatlingPea,PotatoMine,Sholve;
    //PlantType activePlantingBrush = PlantType.None;

    public GameWindow() {
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel sun = new JLabel("SUN");
        sun.setLocation(37, 80);
        sun.setSize(60, 20);

        GamePanel gp = new GamePanel(this,sun);
        gp.setLocation(0, 0);
        getLayeredPane().add(gp, new Integer(0));

        sunflower = new PlantCard("images/cards/card_sunflower.png");
        sunflower.setAction(110, 8,7500,(ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Sunflower);
        });
        getLayeredPane().add(sunflower, new Integer(3));

        peashooter = new PlantCard("images/cards/card_peashooter.png");
        peashooter.setAction(175, 8,7500,(ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Peashooter);
        });
        getLayeredPane().add(peashooter, new Integer(3));

        freezepeashooter = new PlantCard("images/cards/card_freezepeashooter.png");
        freezepeashooter.setAction(240, 8,7500,(ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.FreezePeashooter);
        });
        getLayeredPane().add(freezepeashooter, new Integer(3));

        Torchwood = new PlantCard("images/cards/card_torchwood.png");
        Torchwood.setAction(305, 8,7500,(ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Torchwood);
        });
        getLayeredPane().add(Torchwood, new Integer(3));

        TwicePeashooter = new PlantCard("images/cards/card_twicepeashooter.png");
        TwicePeashooter.setAction(370, 8,7500,(ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.TwicePeashooter);
        });
        getLayeredPane().add(TwicePeashooter, new Integer(3));
        
        ThreePeashooter = new PlantCard("images/cards/card_threepeashooter.png");
        ThreePeashooter.setAction(435,8,7500,(ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.ThreePeashooter);
        });
        getLayeredPane().add(ThreePeashooter, new Integer(3));

        Chomper = new PlantCard("images/cards/card_chomper.png");
        Chomper.setAction(500, 8,7500,(ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Chomper);
        });
        getLayeredPane().add(Chomper, new Integer(3));

        Wallnut = new PlantCard("images/cards/card_wallnut.png");
        Wallnut.setAction(565, 8,30000,(ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Wallnut);
        });
        getLayeredPane().add(Wallnut, new Integer(3));

        GatlingPea = new PlantCard("images/cards/card_gatling.png");
        GatlingPea.setAction(630, 8,50000,(ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.GatlingPea);
        });
        getLayeredPane().add(GatlingPea, new Integer(3));

        PotatoMine = new PlantCard("images/cards/card_potatomine.png");
        PotatoMine.setAction(695, 8,30000,(ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.PotatoMine);
        });
        getLayeredPane().add(PotatoMine, new Integer(3));

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

    public static void begin() {
        gw.dispose();
        gw = new GameWindow();
    }

    public static void main(String[] args) {
        gw = new GameWindow(true);
    }
}
