import javax.swing.JOptionPane;

public class PoleVaultingZombie  extends Zombie {
    public PoleVaultingZombie(GamePanel parent, int lane) {
        super(parent, lane);
        setSpeed(2);
    }
    
    public void advance() {
        if (isMoving()) {
            boolean isCollides = false;
            Collider collided = null;
            int collideCount = 0;
            for (int i = getMyLane()  * 9; i < (getMyLane()  + 1) * 9; i++) {
                if (getGp().getColliders()[i].assignedPlant != null && getGp().getColliders()[i].isInsideCollider(getPosX())) {
                    isCollides = true;
                    collideCount++;
                    collided = getGp().getColliders()[i];
                }
            }
            if (!isCollides) {
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
            		collided.assignedPlant.setHealth(collided.assignedPlant.getHealth() - 10);
            		if (collided.assignedPlant.getHealth() < 0) {
            			collided.removePlant();
            			}
            	}
            	else {
            		setPosX(getPosX() - 20);
            		setSpeed(getSpeed()/2);
            	}
            }
            if (getPosX() < 0) {
            	setMoving(false);
                JOptionPane.showMessageDialog(getGp(), "ZOMBIES ATE YOUR BRAIN !" + '\n' + "Starting the level again");
                GameWindow.gw.dispose();
                GameWindow.gw = new GameWindow();
            }
        }
    }
}
