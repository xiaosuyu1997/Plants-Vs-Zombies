import javax.swing.*;
import java.awt.*;
import java.io.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.Image;
import java.awt.Toolkit;

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
    private BufferedImage image;
    private ActionListener al;
    private int x,y;

    public int changetime=10;
    public int waittime;
    public boolean iswait;
    public Timer Time;
    public Timer WaitTime;
    public Timer redrawTimer;

    public PlantCard(String imagePath){
        setSize(64, 90);
        //图形的两种存储方式
        img = new ImageIcon(this.getClass().getResource(imagePath)).getImage();
        Im = img;
        try{
            image = toBufferedImage(img);
        }catch (Exception e){
        }
        if(image==null){
            System.out.println(imagePath);
        }
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
        
        WaitTime = new Timer(wa/10,(ActionEvent e) ->{
            changetime=changetime+1;
            Im=grayImage(changetime);
            if(changetime==10){
                Im = Toolkit.getDefaultToolkit().createImage(image.getSource());
                WaitTime.stop();
            }
        });
    }

    public void countwaittime(){
        iswait=true;
        Time.start();
        changetime=0;
        Im=grayImage(changetime);
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

    public Image grayImage(int status){
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(),  image.getType());
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int color = image.getRGB(i, j);
                final int r = (color >> 16) & 0xff;
                final int g = (color >> 8) & 0xff;
                final int b = color & 0xff;
                int gray=0;
                if(9*status>image.getHeight()-j){
                    gray = getBigger(r, g, b);
                }
                else{
                    gray = getAvg(r, g, b);//加权法灰度化
                }
                // if(status==1){
                //     gray=getBigger(r, g, b);//最大值法灰度化
                // }else if(status==2){
                //     gray=getSmall(r, g, b);//最小值法灰度化
                // }else if(status==3){
                //     gray=getAvg(r, g, b);//均值法灰度化
                // }else if(status==4){
                //     gray = (int) (0.3 * r + 0.59 * g + 0.11 * b);//加权法灰度化
                // }
                grayImage.setRGB(i, j, colorToRGB(0, gray, gray, gray));
            }
        }
        return toImage(grayImage);
    }
    /**
     *  颜色分量转换为RGB值
     */
    private int colorToRGB(int alpha, int red, int green, int blue) {
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;
 
        return newPixel;
    }
    //比较三个大小取最大数
    public static int getBigger(int x,int y,int z){
        if(x>=y&&x>=z){
            return x;
        }else if(y>=x&&y>=z){
            return y;
        }else if(z>=x&&z>=y){
            return z;
        }else{
            return 0;
        }
    }
     
    //比较三个大小取最小数
    public static int getSmall(int x,int y,int z){
        if(x<=y&&x<=z){
            return x;
        }else if(y>=x&&y>=z){
            return y;
        }else if(z>=x&&z>=y){
            return z;
        }else{
            return 0;
        }
    }
     
    //均值法
    public static int getAvg(int x,int y,int z){
        int avg=(x+y+z)/3;
        return avg;
    }

    //BufferedImage to Image
    public static Image toImage(BufferedImage bufferedImage) {
        return Toolkit.getDefaultToolkit().createImage(bufferedImage.getSource());
    }
    /**
     * 将image对象 转成 BufferedImage
     */
    private BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
        }
        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }
}