import javax.swing.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.io.*;

/**
 * Created by Armin on 6/25/2016.
 */
public class GamePanel extends JLayeredPane implements MouseMotionListener {

    private Image bgImage;
    private Image pauseImage;

    private Collider[] colliders;
    private Collider[] brains;

    private ArrayList<ArrayList<Zombie>> laneZombies;
    private ArrayList<DeadZombie> deadZombies;
    private ArrayList<ArrayList<Pea>> lanePeas;
    private ArrayList<Sun> activeSuns;

    private Timer redrawTimer;
    private Timer advancerTimer;
    private Timer sunProducer;
    private Timer zombieProducer;
    private Timer zombieDier;
    private Timer loseTimer;
    private Timer winTimer;
    private JLabel sunScoreboard;

    private SoundEffect bgm = new SoundEffect("./src/bgms/pvzBG1.wav");
    private SoundEffect zombiesComing = new SoundEffect("./src/bgms/zombiecoming.wav");


    private GameWindow.PlantType activePlantingBrush = GameWindow.PlantType.None;
    private GameWindow gw;

    private int mouseX, mouseY;

    private int sunScore;

    private int zombieType;
    private int zombieProduceInterval;
    private int zombieProduceCount;
    private int zombieProduceType;

    public int getSunScore() {
        return sunScore;
    }

    public void setSunScore(int sunScore) {
        this.sunScore = sunScore;
        sunScoreboard.setText(String.valueOf(sunScore));
    }

    public GamePanel(GameWindow gamewin, JLabel sunScoreboard) {
        gw = gamewin;

        setSize(1000, 752);
        setLayout(null);
        addMouseMotionListener(this);
        this.sunScoreboard = sunScoreboard;
        setSunScore(5000);  //pool avalie

        bgImage = new ImageIcon(this.getClass().getResource("images/mainB.png")).getImage();
        pauseImage = new ImageIcon(this.getClass().getResource(
                "images\\Button0.png")).getImage();

        zombieType = 5;

        zombieProduceInterval = 7000;
        zombieProduceCount = 0;
        zombieProduceType = 1;

        laneZombies = new ArrayList<>();
        laneZombies.add(new ArrayList<>()); //line 1
        laneZombies.add(new ArrayList<>()); //line 2
        laneZombies.add(new ArrayList<>()); //line 3
        laneZombies.add(new ArrayList<>()); //line 4
        laneZombies.add(new ArrayList<>()); //line 5

        lanePeas = new ArrayList<>();
        lanePeas.add(new ArrayList<>()); //line 1
        lanePeas.add(new ArrayList<>()); //line 2
        lanePeas.add(new ArrayList<>()); //line 3
        lanePeas.add(new ArrayList<>()); //line 4
        lanePeas.add(new ArrayList<>()); //line 5

        deadZombies = new ArrayList<>();
        // 灏忔帹杞�
        for (int i = 0; i < 5; i++) {
            lanePeas.get(i).add(new LawnCleaner(this, i, 0, 0));
        }

        // 妞嶇墿缃戞牸
        colliders = new Collider[45];
        for (int i = 0; i < 45; i++) {
            Collider a = new Collider();
            a.setLocation(44 + (i % 9) * 100, 109 + (i / 9) * 120);
            a.setAction(new PlantActionListener((i % 9), (i / 9)));
            colliders[i] = a;
            add(a, new Integer(0));
        }

        //colliders[0].setPlant(new FreezePeashooter(this,0,0));
/*
        colliders[9].setPlant(new Peashooter(this,0,1));
        laneZombies.get(1).add(new NormalZombie(this,1));*/

        activeSuns = new ArrayList<>();

        // 5ms鍒锋柊涓�娆＄晫闈紙gif鎾斁锛夛紝瀹炵幇鍔ㄦ�佽繃绋�
        redrawTimer = new Timer(10, (ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();

        // 姣�60s鍒锋柊涓�娆￠�昏緫
        advancerTimer = new Timer(50, (ActionEvent e) -> advance());
        advancerTimer.start();

        sunProducer = new Timer(5000, (ActionEvent e) -> {
            Random rnd = new Random();
            Sun sta = new Sun(this, rnd.nextInt(800) + 100, 0, rnd.nextInt(300) + 200);
            activeSuns.add(sta);
            add(sta, new Integer(2));
        });
        sunProducer.start();

        zombieProducer = new Timer(zombieProduceInterval, (ActionEvent e) -> {
            Random rnd = new Random();
            LevelData lvl = new LevelData();
            //String[] Level = lvl.LEVEL_CONTENT[Integer.parseInt(lvl.LEVEL_NUMBER) - 1];
            //int[][] LevelValue = lvl.LEVEL_VALUE[Integer.parseInt(lvl.LEVEL_NUMBER) - 1];
            int l = rnd.nextInt(5);
            int t = rnd.nextInt(zombieProduceType);
            Zombie z = Zombie.getZombie(lvl.LEVEL_CONTENT[t], this, l);
            ;
            //for (int i = 0; i < LevelValue.length; i++) {
            //  if (t >= LevelValue[i][0] && t <= LevelValue[i][1]) {
            //    z = Zombie.getZombie(Level[i], this, l);
            //}
            //}
            laneZombies.get(l).add(z);
            add(z, new Integer(2));
            zombieProduceCount++;
            if (zombieProduceCount % 2 == 0) {
                if (zombieProduceType < 5) {
                    zombieProduceType++;
                }
            }
            if (zombieProduceInterval > 1000) {
                zombieProduceInterval -= 200;
            }
        });
        zombieProducer.start();

        zombieDier = new Timer(1000, (ActionEvent e) -> {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < laneZombies.get(i).size(); j++) {
                    if (laneZombies.get(i).get(j).isDead()) {
                        System.out.println("ZOMBIE DIED");
                        deadZombies.add(new DeadZombie(this, i, laneZombies.get(i).get(j).getPosX()));
                        // System.out.printf("dead zombies%d", deadZombies.size());
                        this.remove(laneZombies.get(i).get(j));
                        laneZombies.get(i).remove(laneZombies.get(i).get(j));

                        j--;
                        GamePanel.setProgress(10);
                    }
                }
            }
        });
        //zombieDier.start();
        bgm.prepare();
        zombiesComing.prepare();

        
        loseTimer = new Timer(0, (ActionEvent e) -> {
            boolean haslose=false;
            for(int i=0;i<5;i++){
                for(int j=0;j<laneZombies.get(i).size();++j){
                    if(laneZombies.get(i).get(j).getPosX()<-50){
                        haslose=true;
                        break;
                    }
                }
            }
            if(haslose){
                SoundEffect zombiesWin = new SoundEffect("./src/bgms/zombiegroup.wav");
                zombiesWin.prepare();
                zombiesWin.player.start();
                loseTimer.stop();
                JOptionPane.showMessageDialog(GamePanel.this, "ZOMBIES ATE YOUR BRAIN !" + '\n' + "Starting the level again");
                GameWindow.gw.dispose();
                System.exit(0);
            }
        });
        loseTimer.start();
        
        winTimer = new Timer(0, (ActionEvent e) -> {
            if (progress >= 150) {
                if ("1".equals(LevelData.LEVEL_NUMBER)) {
                    JOptionPane.showMessageDialog(null, "LEVEL_CONTENT Completed !!!" + '\n' + "Starting next LEVEL_CONTENT");
                    GameWindow.gw.dispose();
                    LevelData.write("2");
                    System.exit(0);
                } else {
                    JOptionPane.showMessageDialog(null, "LEVEL_CONTENT Completed !!!" + '\n' + "More Levels will come soon !!!" + '\n' + "Resetting data");
                    LevelData.write("1");
                    System.exit(0);
                }
                progress = 0;
                winTimer.stop();
            }
        });
        winTimer.start();


        bgm.player.start();
        zombiesComing.player.start();
    }

    public GamePanel(GameWindow gamewin,JLabel sunScoreboard,int ii) {
        gw = gamewin;

        setSize(1000, 752);
        setLayout(null);
        addMouseMotionListener(this);
        this.sunScoreboard = sunScoreboard;
        setSunScore(5000);  //pool avalie

        laneZombies = new ArrayList<>();
        laneZombies.add(new ArrayList<>()); //line 1
        laneZombies.add(new ArrayList<>()); //line 2
        laneZombies.add(new ArrayList<>()); //line 3
        laneZombies.add(new ArrayList<>()); //line 4
        laneZombies.add(new ArrayList<>()); //line 5

        lanePeas = new ArrayList<>();
        lanePeas.add(new ArrayList<>()); //line 1
        lanePeas.add(new ArrayList<>()); //line 2
        lanePeas.add(new ArrayList<>()); //line 3
        lanePeas.add(new ArrayList<>()); //line 4
        lanePeas.add(new ArrayList<>()); //line 5

        bgImage = new ImageIcon(this.getClass().getResource("images/mainN.png")).getImage();
        brains = new Collider[5];
        for (int i = 0; i < 5; i++) {
            Collider a = new Collider();
            a.setLocation(44 + (i % 9) * 100, 109 + (i / 9) * 120);
            a.setPlant(new Brain(GamePanel.this, i) );
            a.setSize(32,31);
            brains[i] = a;
            add(a, new Integer(0));
        }

        // 妞嶇墿缃戞牸
        colliders = new Collider[45];
        for (int i = 0; i < 45; i++) {
            Collider a = new Collider();
            a.setLocation(44 + (i % 9) * 100, 109 + (i / 9) * 120);
            if(i%9>3) a.setAction(new PlantActionListener((i % 9), (i / 9)));
            colliders[i] = a;
            add(a, new Integer(0));
        }

        for (int x = 0; x < 4 ;x++) {
            for (int y = 0; y < 5; y++) {
                int c = (int)(Math.random()*10+1);
                if(c==1) colliders[x + y * 9].setPlant(new Sunflower(GamePanel.this, x, y,1));
                if(c==2) colliders[x + y * 9].setPlant(new Peashooter(GamePanel.this, x, y));
                if(c==3) colliders[x + y * 9].setPlant(new FreezePeashooter(GamePanel.this, x, y));
                if(c==4) colliders[x + y * 9].setPlant(new Torchwood(GamePanel.this, x, y));
                if(c==5) colliders[x + y * 9].setPlant(new TwicePeashooter(GamePanel.this, x, y));
                if(c==6) colliders[x + y * 9].setPlant(new ThreePeashooter(GamePanel.this, x, y));
                if(c==7) colliders[x + y * 9].setPlant(new Chomper(GamePanel.this, x, y,1,0));
                if(c==8) colliders[x + y * 9].setPlant(new Wallnut(GamePanel.this, x, y,1));
                if(c==9) colliders[x + y * 9].setPlant(new PotatoMine(GamePanel.this, x, y,2));
                if(c==10) colliders[x + y * 9].setPlant(new Spikeweed(GamePanel.this, x, y));
            }
        }
        redrawTimer = new Timer(10, (ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();

        advancerTimer = new Timer(50, (ActionEvent e) -> advance());
        advancerTimer.start();

        
        loseTimer = new Timer(0, (ActionEvent e) ->{
            if(getSunScore()<75){
                loseTimer.stop();
                JOptionPane.showMessageDialog(GamePanel.this, "have no enough sun, you lose");
                GameWindow.gw.dispose();
                System.exit(0);
            }
        });
        loseTimer.start();


        winTimer = new Timer(0,(ActionEvent e) ->{
            boolean nobrain = true;
            for(int i=0;i<5;i++){
                if(brains[i].assignedPlant!=null) nobrain=false;
            }
            if(nobrain){
                winTimer.stop();
                JOptionPane.showMessageDialog(null,"you win");
                GameWindow.gw.dispose();
                System.exit(0);
            }
        });
        winTimer.start();
    }

    private void advance() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < laneZombies.get(i).size(); j++) {
                Zombie z = laneZombies.get(i).get(j);
                z.advance();
            }

            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea p = lanePeas.get(i).get(j);
                p.advance();
            }
            /**
             for(int j = 0;j < laneZombies.get(i).size();j++) {
             if(laneZombies.get(i).get(j).isDead()) {
             System.out.println("ZOMBIE DIED");
             deadZombies.add(new DeadZombie(this, i, laneZombies.get(i).get(j).getPosX()));
             // System.out.printf("dead zombies%d", deadZombies.size());
             //remove(laneZombies.get(i).get(j));
             laneZombies.get(i).remove(laneZombies.get(i).get(j));

             j--;
             // 姣忓彧鍍靛案10鍒嗭紝鍒嗘暟杈惧埌150涔嬪悗缁堟姝ゅ叧鍗�
             GamePanel.setProgress(10);
             }
             }*/
        }
        if(activeSuns!=null){
            for (int i = 0; i < activeSuns.size(); i++) {
                activeSuns.get(i).advance();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImage, 0, 0, null);
        g.drawImage(pauseImage, 860, 0, 140, 50, null);

        //Draw brains
        if(brains !=null){
            for (int i = 0; i < 5 ; i++) {
                Collider c = brains[i];
                if (c.assignedPlant != null){
                    Plant p = c.assignedPlant;
                    g.drawImage(p.getImage(), 0, 129 + p.getY() * 120, null);
                }
            }
        }
        //Draw Plants
        for (int i = 0; i < 45; i++) {
            Collider c = colliders[i];
            if (c.assignedPlant != null) {
                Plant p = c.assignedPlant;
                if (p instanceof Tallnut) {
                    g.drawImage(p.getImage(), 60 + p.getX() * 100, 60 + p.getY() * 120, null);
                } else if (p instanceof Spikeweed || p instanceof Spikerock) {
                    g.drawImage(p.getImage(), 60 + p.getX() * 100, 180 + p.getY() * 120, null);
                } else if (p instanceof Chomper) {
                    g.drawImage(p.getImage(), 60 + p.getX() * 100, 90 + p.getY() * 120, null);
                } else {
                    g.drawImage(p.getImage(), 60 + p.getX() * 100, 129 + p.getY() * 120, null);
                }
            }
        }

        //Draw Zombies and Peas

        for (int i = 0; i < 5; i++) {
            /**
             for (Zombie z : laneZombies.get(i)) {
             if (z instanceof NormalZombie && !z.isDead()) {
             g.drawImage(normalZombieImage, z.getPosX(), 69 + (i * 120), null);
             }

             else if (z instanceof ConeHeadZombie && !z.isDead()) {
             if(z.isAttacking()) {
             if(z.isHurted()) {
             g.drawImage(coneHeadZombieHurtAttackImage, z.getPosX(), (i * 120) + 40, null);
             }
             else {
             g.drawImage(coneHeadZombieAttackImage, z.getPosX()-110, 69 + (i * 120), null);
             }

             }
             else if(z.isMoving()) {
             g.drawImage(coneHeadZombieImage, z.getPosX(), 69 + (i * 120),null);
             }
             }

             else if (z instanceof MetalBucketZombie && !z.isDead()) {
             if(z.isAttacking()) {
             g.drawImage(metalBucketZombieAttackImage, z.getPosX(), 69 + (i * 120)-10, null);
             }
             else if(z.isMoving()) {
             g.drawImage(metalBucketZombieImage, z.getPosX(), 69 + (i * 120)-10, null);
             }
             }

             else if (z instanceof NewspaperZombie && !z.isDead()) {
             if(!z.isHurted()) {
             if(z.isAttacking()) {
             g.drawImage(newspaperZombieAttackImage, z.getPosX(), 69 + (i * 120)-10, null);
             }
             else if(z.isMoving()) {
             g.drawImage(newspaperZombieImage, z.getPosX(), 69 + (i * 120)-10, null);
             }
             }
             else {

             }
             }

             else if (z instanceof PoleVaultingZombie && !z.isDead()) {
             g.drawImage(poleVaultingZombieImage, z.getPosX(), (i * 120)-20, null);
             }


             else if (z instanceof FootballZombie && !z.isDead()) {
             if(z.isAttacking()) {
             if(z.isHurted()) {
             g.drawImage(footballZombieHurtAttackImage, z.getPosX(), (i * 120) + 40, null);
             }
             else {
             g.drawImage(footballZombieAttackImage, z.getPosX(), (i * 120) + 40, null);
             }
             }
             else if(z.isMoving()) {
             if(z.isHurted()) {
             g.drawImage(footballZombieHurtImage, z.getPosX(), (i * 120) + 40, null);
             }
             else {
             g.drawImage(footballZombieImage, z.getPosX(), (i * 120) + 40, null);
             }
             }

             }
             }*/

            for (int j = 0; j < lanePeas.get(i).size(); j++) {
                Pea pea = lanePeas.get(i).get(j);
                g.drawImage(pea.getImage(), pea.getPosX(), 130 + (i * 120), null);
            }

        }

        /**
         for (Iterator<DeadZombie> ite = deadZombies.iterator(); ite.hasNext();) {
         DeadZombie i = ite.next();
         g.drawImage(i.deadZombieImage, i.getPosX(), 69 + (i.getMyLane() * 120), null);
         if(i.lifespanDecrease()) ite.remove();
         }
         */
        //
    }

    // 绉嶆鐗�
    private class PlantActionListener implements ActionListener {

        int x, y;
        private SoundEffect plant = new SoundEffect("./src/bgms/plant.wav");
        private SoundEffect shovel = new SoundEffect("./src/bgms/shovel.wav");

        public PlantActionListener(int x, int y) {
            this.x = x;
            this.y = y;
            plant.prepare();
        }

        @Override
        public void actionPerformed(ActionEvent e) {


            if (activePlantingBrush == GameWindow.PlantType.ConeHeadZombie){
            	plant.prepare();
            	plant.player.start();
                if (getSunScore() >= 75) {
                    ConeHeadZombie z = new ConeHeadZombie(GamePanel.this,  y);
                    z.setPosX(44 + x * 100);
                    laneZombies.get(y).add(z);
                    add(z, new Integer(2));
                    setSunScore(getSunScore() - 75);
                }
            }
            if (activePlantingBrush == GameWindow.PlantType.PoleVaultingZombie){
            	plant.prepare();
            	plant.player.start();
                if (getSunScore() >= 75) {
                    PoleVaultingZombie z = new PoleVaultingZombie(GamePanel.this,  y);
                    z.setPosX(44 + x * 100);
                    laneZombies.get(y).add(z);
                    add(z, new Integer(2));
                    setSunScore(getSunScore() - 75);
                }
            }
            if (activePlantingBrush == GameWindow.PlantType.MetalBucketZombie){
            	plant.prepare();
            	plant.player.start();
                if (getSunScore() >= 125) {
                    MetalBucketZombie z = new MetalBucketZombie(GamePanel.this,  y);
                    z.setPosX(44 + x * 100);
                    laneZombies.get(y).add(z);
                    add(z, new Integer(2));
                    setSunScore(getSunScore() - 125);
                }
            }
            if (activePlantingBrush == GameWindow.PlantType.FootballZombie){
            	plant.prepare();
            	plant.player.start();
                if (getSunScore() >= 175) {
                    FootballZombie z = new FootballZombie(GamePanel.this,  y);
                    z.setPosX(44 + x * 100);
                    laneZombies.get(y).add(z);
                    add(z, new Integer(2));
                    setSunScore(getSunScore() - 175);
                }
            }
            

            if (activePlantingBrush == GameWindow.PlantType.GatlingPea) {
                plant.prepare();
                plant.player.start();
                if (getSunScore() >= 250 && colliders[x + y * 9].assignedPlant instanceof TwicePeashooter) {

                    colliders[x + y * 9].removePlant();
                    colliders[x + y * 9].setPlant(new GatlingPea(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 250);
                    gw.GatlingPea.countwaittime();
                }
            }

            if (activePlantingBrush == GameWindow.PlantType.Twinsunflower) {
                plant.prepare();
                plant.player.start();
                if (getSunScore() >= 150 && colliders[x + y * 9].assignedPlant instanceof Sunflower) {
                    colliders[x + y * 9].removePlant();
                    colliders[x + y * 9].setPlant(new Twinsunflower(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 150);
                    gw.Twinsunflower.countwaittime();
                }
            }

            if (activePlantingBrush == GameWindow.PlantType.Spikerock && colliders[x + y * 9].assignedPlant instanceof Spikeweed) {
                plant.player.start();
                if (getSunScore() >= 125) {
                    colliders[x + y * 9].removePlant();
                    colliders[x + y * 9].setPlant(new Spikerock(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 100);
                    gw.Spikerock.countwaittime();
                }
            }

            if (activePlantingBrush == GameWindow.PlantType.Sholve) {
                plant.prepare();
                plant.player.start();
                if (colliders[x + y * 9].assignedPlant != null) {
                    colliders[x + y * 9].removePlant();
                    activePlantingBrush = GameWindow.PlantType.None;
                }
            }

            if (colliders[x + y * 9].assignedPlant != null) {
                activePlantingBrush = GameWindow.PlantType.None;
            }

            if (activePlantingBrush == GameWindow.PlantType.Sunflower) {
                plant.player.start();
                if (getSunScore() >= 50) {
                    colliders[x + y * 9].setPlant(new Sunflower(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 50);
                    gw.Sunflower.countwaittime();
                }
            }
            
            if (activePlantingBrush == GameWindow.PlantType.Peashooter) {
                plant.player.start();
                if (getSunScore() >= 100) {
                    colliders[x + y * 9].setPlant(new Peashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 100);
                    gw.Peashooter.countwaittime();
                }
            }

            if (activePlantingBrush == GameWindow.PlantType.FreezePeashooter) {
                plant.player.start();
                if (getSunScore() >= 175) {
                    colliders[x + y * 9].setPlant(new FreezePeashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 175);
                    gw.FreezePeashooter.countwaittime();
                }
            }

            if (activePlantingBrush == GameWindow.PlantType.Torchwood) {
                plant.player.start();
                if (getSunScore() >= 175) {
                    colliders[x + y * 9].setPlant(new Torchwood(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 175);
                    gw.Torchwood.countwaittime();
                }
            }

            if (activePlantingBrush == GameWindow.PlantType.TwicePeashooter) {
                plant.player.start();
                if (getSunScore() >= 200) {
                    colliders[x + y * 9].setPlant(new TwicePeashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 200);
                    gw.TwicePeashooter.countwaittime();
                }
            }

            if (activePlantingBrush == GameWindow.PlantType.ThreePeashooter) {
                plant.player.start();
                if (getSunScore() >= 325) {
                    colliders[x + y * 9].setPlant(new ThreePeashooter(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 325);
                    gw.ThreePeashooter.countwaittime();
                }
            }

            if (activePlantingBrush == GameWindow.PlantType.Chomper) {
                plant.player.start();
                if (getSunScore() >= 150) {
                    colliders[x + y * 9].setPlant(new Chomper(GamePanel.this, x, y, 1, 0));
                    setSunScore(getSunScore() - 150);
                    gw.Chomper.countwaittime();
                }
            }

            if (activePlantingBrush == GameWindow.PlantType.Wallnut) {
                plant.player.start();
                if (getSunScore() >= 50) {
                    colliders[x + y * 9].setPlant(new Wallnut(GamePanel.this, x, y, 1));
                    setSunScore(getSunScore() - 50);
                    gw.Wallnut.countwaittime();
                }
            }

            if (activePlantingBrush == GameWindow.PlantType.PotatoMine) {
                plant.player.start();
                if (getSunScore() >= 25) {
                    colliders[x + y * 9].setPlant(new PotatoMine(GamePanel.this, x, y, 1));
                    setSunScore(getSunScore() - 25);
                    gw.PotatoMine.countwaittime();
                }
            }

            if (activePlantingBrush == GameWindow.PlantType.CherryBomb) {
                plant.player.start();
                if (getSunScore() >= 150) {
                    colliders[x + y * 9].setPlant(new CherryBomb(GamePanel.this, x, y, 1));
                    setSunScore(getSunScore() - 150);
                    gw.CherryBomb.countwaittime();
                }
            }

            if (activePlantingBrush == GameWindow.PlantType.Tallnut) {
                plant.player.start();
                if (getSunScore() >= 125) {
                    colliders[x + y * 9].setPlant(new Tallnut(GamePanel.this, x, y, 1));
                    setSunScore(getSunScore() - 125);
                    gw.Tallnut.countwaittime();
                }
            }

            if (activePlantingBrush == GameWindow.PlantType.Jalapeno) {
                plant.player.start();
                if (getSunScore() >= 125) {
                    colliders[x + y * 9].setPlant(new Jalapeno(GamePanel.this, x, y, 1));
                    setSunScore(getSunScore() - 125);
                    gw.Jalapeno.countwaittime();
                }
            }

            if (activePlantingBrush == GameWindow.PlantType.Spikeweed) {
                plant.player.start();
                if (getSunScore() >= 100) {
                    colliders[x + y * 9].setPlant(new Spikeweed(GamePanel.this, x, y));
                    setSunScore(getSunScore() - 100);
                    gw.Spikeweed.countwaittime();
                }
            }
            activePlantingBrush = GameWindow.PlantType.None;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    static int progress = 0;

    public static void setProgress(int num) {
        progress = progress + num;
        System.out.println(progress);
    }

    public GameWindow.PlantType getActivePlantingBrush() {
        return activePlantingBrush;
    }

    public void setActivePlantingBrush(GameWindow.PlantType activePlantingBrush) {
        this.activePlantingBrush = activePlantingBrush;
    }

    public ArrayList<ArrayList<Zombie>> getLaneZombies() {
        return laneZombies;
    }

    public void setLaneZombies(ArrayList<ArrayList<Zombie>> laneZombies) {
        this.laneZombies = laneZombies;
    }

    public ArrayList<ArrayList<Pea>> getLanePeas() {
        return lanePeas;
    }

    public void setLanePeas(ArrayList<ArrayList<Pea>> lanePeas) {
        this.lanePeas = lanePeas;
    }

    public ArrayList<Sun> getActiveSuns() {
        return activeSuns;
    }

    public void setActiveSuns(ArrayList<Sun> activeSuns) {
        this.activeSuns = activeSuns;
    }

    public Collider[] getColliders() {
        return colliders;
    }

    public void setColliders(Collider[] colliders) {
        this.colliders = colliders;
    }

    public Collider[] getBrain() {
        return brains;
    }
}
