import java.applet.Applet;
import java.applet.AudioClip;
import java.net.MalformedURLException;
import java.net.URL;

public class Audio {
    private String str;
    private AudioClip playAudio;
    private boolean isLoop;

    public Audio (String str,boolean isLoop) {
        this.str = str;
        URL url = null;
        try {
            url = new URL("file:" + str);
        } catch (MalformedURLException e) {}
        playAudio = Applet.newAudioClip(url);
        this.isLoop = isLoop;
    }

    public void play() {
        try {
            playAudio.play();
        } catch(Exception e) {
            e.printStackTrace();
        }
        if (isLoop) {
            playAudio.loop();
        }
    }
}
