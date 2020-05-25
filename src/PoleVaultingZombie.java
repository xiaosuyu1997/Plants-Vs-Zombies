import javax.swing.JOptionPane;

public class PoleVaultingZombie  extends Zombie {
	private int collideCount = 0;
	
    public PoleVaultingZombie(GamePanel parent, int lane) {
        super(parent, lane);
        setSpeed(2);
    }
    
    public void advance() {
        if (isMoving()) {
            boolean isCollides = false;
            Collider collided = null;
            for (int i = getMyLane()  * 9; i < (getMyLane()  + 1) * 9; i++) {
                if (getGp().getColliders()[i].assignedPlant != null && getGp().getColliders()[i].isInsideCollider(getPosX())) {
                    isCollides = true;
                    collideCount++;
                    collided = getGp().getColliders()[i];
                }
            }
            if (!isCollides) {
            	setAttacking(false);
                if (slowInt > 0) {
                    if (slowInt % 2 == 0) {
                    	setPosX(getPosX() - getSpeed()) ;
                    }
                    slowInt--;
                } else {
                	setPosX(getPosX() - getSpeed());
                }
            } else {
            	if(collideCount > 1) {
            		setAttacking(true);
            		setMoving(false);
            	}
            	else {
            		setPosX(getPosX() - 100);
            		setSpeed(1);
            	}
            }
            
            if(isAttacking()) {
        		collided.assignedPlant.setHealth(collided.assignedPlant.getHealth() - 10);
        		if (collided.assignedPlant.getHealth() < 0) {
        			collided.removePlant();
            		setAttacking(false);
            		setMoving(true);

        		}
            }
            
            if (getPosX() < 0) {
            	setMoving(false);
                JOptionPane.showMessageDialog(getGp(), "ZOMBIES ATE YOUR BRAIN !" + '\n' + "Starting the level again");
                GameWindow.gw.dispose();
            }
        }
    }
}
