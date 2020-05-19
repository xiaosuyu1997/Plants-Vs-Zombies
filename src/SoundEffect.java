import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.media. * ;

/**
public class SoundEffect{ 
	public Player player;
    private String bgmPath;
    
    public SoundEffect(String path) {
    	bgmPath = path;
    }

    public void prepare() {
        try {
			File playFile = new File(bgmPath);
			player = Manager.createRealizedPlayer(playFile.toURI().toURL());
        } catch(javax.media.CannotRealizeException ex) {
            System.out.println("不能创建播放器");
        } catch(NoPlayerException ex) {
            System.out.println("不能播放文件");
        } catch(IOException ex) {
        }
        if (player == null) {
            return;
        }
        player.prefetch();
    }
    
     
}*/

import java.io.File;
import java.io.IOException;
 
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
 
/**
 * This is an example program that demonstrates how to play back an audio file
 * using the Clip in Java Sound API.
 * @author www.codejava.net
 *
 */
public class SoundEffect{
	public Clip player;
	private String audioFilePath;
    public SoundEffect(String path) {
    	audioFilePath = path;
    }
	
    void prepare() {
        File audioFile = new File(audioFilePath);
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
 
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            player = (Clip) AudioSystem.getLine(info);
 
            player.open(audioStream);
             
        } catch (UnsupportedAudioFileException ex) {
            System.out.println("The specified audio file is not supported.");
            ex.printStackTrace();
        } catch (LineUnavailableException ex) {
            System.out.println("Audio line for playing back is unavailable.");
            ex.printStackTrace();
        } catch (IOException ex) {
            System.out.println("Error playing the audio file.");
            ex.printStackTrace();
        }
         
    }
    
 
 
}