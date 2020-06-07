import javax.swing.*;

public class Brain extends Plant{
    public Brain (GamePanel parent,int i){
        super(parent, i);
        setImage(new ImageIcon(this.getClass().getResource("images/brain.png")).getImage());
    }
}