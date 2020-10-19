import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.net.URL;

public class Game {
    public static void main (String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                // Set the main frame
                MainFrame frame = new MainFrame();
                frame.setVisible(true);
                // Play the background music
                Clip clip = AudioSystem.getClip();
                clip.open(AudioSystem.getAudioInputStream(new URL("file:" + MainFrame.class.getResource("/music/bgm.wav").getPath().substring(1))));
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
