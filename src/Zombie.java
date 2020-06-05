import javax.swing.*;
import javax.sound.sampled.Clip;

/**
 * Created by Armin on 6/25/2016.
 */
public class Zombie extends JPanel {

    private int health = 1500;
    private int speed = 1;
    private int fullHealth;
    private GamePanel gp;

    private int posX = 1000;
    private int myLane;
    private boolean isMoving = true;
    private boolean isAttacking = false;
    private boolean isHurt = false;
    private boolean isDead = false;
    private Collider collided = null;
    
    
    public SoundEffect zombiesEating = new SoundEffect("./src/bgms/zombieEat.wav");
    public SoundEffect zombiesWin = new SoundEffect("./src/bgms/zombiegroup.wav");
    public SoundEffect gulp = new SoundEffect("./src/bgms/gulp.wav");

    public Zombie(GamePanel parent, int lane) {
        this.gp = parent;
        myLane = lane;
        fullHealth = health;
        zombiesEating.prepare();
        zombiesWin.prepare();
        gulp.prepare();
        
        zombiesEating.gainControl.setValue(-10.0f);
    }

    public void advance() {
        if (isMoving) {
            boolean isCollides = false;
            for (int i = myLane * 9; i < (myLane + 1) * 9; i++) {
                if (gp.getColliders()[i].assignedPlant != null && gp.getColliders()[i].isInsideCollider(posX)) {
                    isCollides = true;
                    collided = gp.getColliders()[i];
                }
            }
            if (!isCollides) {
            	isAttacking = false;
                if (slowInt > 0) {
                    if (slowInt % 2 == 0) {
                    	posX -= speed;
                    }
                    slowInt--;
                } else {
                    posX -= speed;
                }
            } else {
            	isMoving = false;
            	isAttacking = true;
            }
            if (posX < 0) {
            	zombiesWin.player.start();
                isMoving = false;
                JOptionPane.showMessageDialog(gp, "ZOMBIES ATE YOUR BRAIN !" + '\n' + "Starting the level again");
                GameWindow.gw.dispose();
            }
        }
        if(isAttacking) {
            zombiesEating.player.loop(Clip.LOOP_CONTINUOUSLY);
            
            if(collided.assignedPlant!=null){
                collided.assignedPlant.setHealth(collided.assignedPlant.getHealth() - 1);
                if (collided.assignedPlant.getHealth() <= 0) {
                    zombiesEating.player.stop();
                    gulp.player.start();
                    isAttacking = false;
                    isMoving = true;
                }
            }
            if(collided.assignedPlant==null){
                zombiesEating.player.stop();
                isAttacking = false;
                isMoving = true;
            }
        }
        if(health < fullHealth/2) {
        	isHurt = true;
        }
        if(health < 50) {
        	isDead = true;
            zombiesEating.player.stop();
            System.out.println("ZOMBIE DIE");
            GamePanel.setProgress(10);
            gp.getLaneZombies().get(getMyLane()).remove(this);
        }
    }

    int slowInt = 0;

    public void slow() {
        slowInt = 1000;
    }

   
    
    public static Zombie getZombie(String type, GamePanel parent, int lane) {
        Zombie z = new Zombie(parent, lane);
        switch (type) {
            case "NormalZombie":
                z = new NormalZombie(parent, lane);
                break;
            case "ConeHeadZombie":
                z = new ConeHeadZombie(parent, lane);
                break;
            case "MetalBucketZombie":
            	z = new MetalBucketZombie(parent, lane);
                break;
            case "NewspaperZombie":
            	z = new NewspaperZombie(parent, lane);
                break;
            case "PoleVaultingZombie":
            	z = new PoleVaultingZombie(parent, lane);
                break;
            case "FootballZombie":
            	z = new FootballZombie(parent, lane);
                break;
        }
        return z;
    }

    public int getHealth() {
        return health;
    }
    
    public int getFullHealth() {
        return fullHealth;
    }

    public void setHealth(int health) {
        this.health = health;
        this.fullHealth = health;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public GamePanel getGp() {
        return gp;
    }

    public void setGp(GamePanel gp) {
        this.gp = gp;
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

    public boolean isMoving() {
        return isMoving;
    }
    

    public void setMoving(boolean moving) {
        isMoving = moving;
    }
    
    public boolean isAttacking() {
        return isAttacking;
    }
    
    public void setAttacking(boolean Attacking) {
    	isAttacking = Attacking;
    }
    
    public boolean isDead() {
        return isDead;
    }
    
    public void setDead(boolean dead) {
        isDead = dead;
    }

    public boolean isHurted() {
        return isHurt;
    }
    
    public void setHurt(boolean hurt) {
        isHurt = hurt;
    }
    
    public int getSlowInt() {
        return slowInt;
    }

    public void setSlowInt(int slowInt) {
        this.slowInt = slowInt;
    }
}
