import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by Armin on 6/28/2016.
 */
public class PlantCard extends JPanel implements MouseListener {

    private Image img;
    private Image Im;
    private ActionListener al;
    private int x,y;

    public int waittime;
    public boolean iswait;
    public Timer Time;
    public Timer WaitTime;
    public Timer redrawTimer;

    public PlantCard(Image img) {
        setSize(64, 90);
        this.img = img;
        Im=img;
        addMouseListener(this);
        // 5ms刷新一次界面（gif播放），实现动态过程
        redrawTimer = new Timer(5, (ActionEvent e) -> {
            repaint();
        });
        redrawTimer.start();
    }

    public void setAction(int kx,int ky,int wa,ActionListener al) {
        this.al = al;
        x=kx;
        y=ky;
        setLocation(x, y);
        waittime = wa;
        iswait = false;
        Time = new Timer(wa,(ActionEvent e) ->{
            iswait=false;
            Time.stop();
        });
        
        WaitTime = new Timer(wa,(ActionEvent e) ->{
            Im=img;
            WaitTime.stop();
        });
    }

    public void countwaittime(){
        iswait=true;
        Time.start();
        Im=null;
        WaitTime.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(Im, 0, 0, null);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (al != null && !iswait) {
            al.actionPerformed(new ActionEvent(this, ActionEvent.RESERVED_ID_MAX + 1, ""));
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
