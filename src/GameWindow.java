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
        PotatoMine
    }

    //PlantType activePlantingBrush = PlantType.None;

    public GameWindow() {
        setSize(1012, 785);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel sun = new JLabel("SUN");
        sun.setLocation(37, 80);
        sun.setSize(60, 20);

        GamePanel gp = new GamePanel(sun);
        gp.setLocation(0, 0);
        getLayeredPane().add(gp, new Integer(0));

        PlantCard sunflower = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_sunflower.png")).getImage());
        sunflower.setLocation(110, 8);
        sunflower.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Sunflower);
        });
        getLayeredPane().add(sunflower, new Integer(3));

        PlantCard peashooter = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_peashooter.png")).getImage());
        peashooter.setLocation(175, 8);
        peashooter.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Peashooter);
        });
        getLayeredPane().add(peashooter, new Integer(3));

        PlantCard freezepeashooter = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_freezepeashooter.png")).getImage());
        freezepeashooter.setLocation(240, 8);
        freezepeashooter.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.FreezePeashooter);
        });
        getLayeredPane().add(freezepeashooter, new Integer(3));

        PlantCard Torchwood = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_torchwood.png")).getImage());
        Torchwood.setLocation(305, 8);
        Torchwood.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Torchwood);
        });
        getLayeredPane().add(Torchwood, new Integer(3));

        PlantCard TwicePeashooter = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_twicepeashooter.png")).getImage());
        TwicePeashooter.setLocation(370, 8);
        TwicePeashooter.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.TwicePeashooter);
        });
        getLayeredPane().add(TwicePeashooter, new Integer(3));
        
        PlantCard ThreePeashooter = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_threepeashooter.png")).getImage());
        ThreePeashooter.setLocation(435, 8);
        ThreePeashooter.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.ThreePeashooter);
        });
        getLayeredPane().add(ThreePeashooter, new Integer(3));

        PlantCard Chomper = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_chomper.png")).getImage());
        Chomper.setLocation(500, 8);
        Chomper.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Chomper);
        });
        getLayeredPane().add(Chomper, new Integer(3));

        PlantCard Wallnut = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_wallnut.png")).getImage());
        Wallnut.setLocation(565, 8);
        Wallnut.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.Wallnut);
        });
        getLayeredPane().add(Wallnut, new Integer(3));

        PlantCard GatlingPea = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_gatling.png")).getImage());
        GatlingPea.setLocation(630, 8);
        GatlingPea.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.GatlingPea);
        });
        getLayeredPane().add(GatlingPea, new Integer(3));

        PlantCard PotatoMine = new PlantCard(new ImageIcon(this.getClass().getResource("images/cards/card_potatomine.png")).getImage());
        PotatoMine.setLocation(695, 8);
        PotatoMine.setAction((ActionEvent e) -> {
            gp.setActivePlantingBrush(PlantType.PotatoMine);
        });
        getLayeredPane().add(PotatoMine, new Integer(3));

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
