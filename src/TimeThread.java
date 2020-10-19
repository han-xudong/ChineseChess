import javax.swing.*;
import java.awt.*;

public class TimeThread extends Thread{
    JLabel desLabel;

    public TimeThread(JLabel src) {
        desLabel = src;
    }

    public void run() {
        char initPlayer = MainFrame.DoPlayer;
        long oldTime = System.currentTimeMillis();
        desLabel.setText("120");
        while (!Thread.interrupted()) {
            if (MainFrame.DoPlayer != '无') {
                if (MainFrame.DoPlayer == initPlayer) {
                    if (System.currentTimeMillis() > oldTime + 120000) {
                        ChessPieceClick.SwitchDoPlayer();
                        initPlayer = MainFrame.DoPlayer;
                        oldTime = System.currentTimeMillis();
                        desLabel.setText("120");
                    }
                } else {
                    initPlayer = MainFrame.DoPlayer;
                    oldTime = System.currentTimeMillis();
                    desLabel.setText("120");
                }
                if (120 - (System.currentTimeMillis() - oldTime) / 1000 <= 20) {
                    desLabel.setForeground(Color.red);
                } else {
                    desLabel.setForeground(Color.black);
                }
                desLabel.setText(String.valueOf(120 - (System.currentTimeMillis() - oldTime) / 1000));
            } else {
                desLabel.setText("");
            }
        }
    }
}
