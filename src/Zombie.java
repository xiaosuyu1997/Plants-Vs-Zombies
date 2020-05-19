import javax.swing.*;
import javax.sound.sampled.Clip;

/**
 * Created by Armin on 6/25/2016.
 */
public class Zombie {

    private int health = 1000;
    private int speed = 1;

    private GamePanel gp;

    private int posX = 1000;
    private int myLane;
    private boolean isMoving = true;
    private boolean isAttacking = false;
    private boolean isDead = false;
    private Collider collided = null;
    
    
    private SoundEffect zombiesEating = new SoundEffect("./src/bgms/zombieEat.wav");
    private SoundEffect zombiesWin = new SoundEffect("./src/bgms/zombiegroup.wav");
    private SoundEffect gulp = new SoundEffect("./src/bgms/gulp.wav");

    public Zombie(GamePanel parent, int lane) {
        this.gp = parent;
        myLane = lane;
        
        zombiesEating.prepare();
        zombiesWin.prepare();
        gulp.prepare();
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
                GameWindow.gw = new GameWindow();
            }
        }
        if(isAttacking) {
        	zombiesEating.player.loop(Clip.LOOP_CONTINUOUSLY);
            collided.assignedPlant.setHealth(collided.assignedPlant.getHealth() - 1);
            if (collided.assignedPlant.getHealth() <= 0) {
            	zombiesEating.player.stop();
            	gulp.player.start();
                collided.removePlant();
                isAttacking = false;
                isMoving = true;
            }
        }
        if(health < 50) {
        	isDead = true;
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
            case "PoleVaultingZombie":
            	z = new PoleVaultingZombie(parent, lane);
                break;
        }
        return z;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public int getSlowInt() {
        return slowInt;
    }

    public void setSlowInt(int slowInt) {
        this.slowInt = slowInt;
    }
}
