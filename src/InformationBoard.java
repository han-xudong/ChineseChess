import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;


public class InformationBoard extends JPanel {
    //Record data
    private StringBuffer log = new StringBuffer();
    //Save for the last 10 lines
    private String[] logString = new String[10];
    //Add position for Add()
    private int Address;

    public InformationBoard() {
        super();
        //Set board transparent
        this.setOpaque(false);
        //Initialize the first 10 lines
        for (int i = 0;i < 10; i ++) {
            logString[i] = "";
        }
        //Set add position is 0
        Address = 0;
    }
    public void paintComponent (Graphics g) {
        //Define a BufferedImage
        BufferedImage BImage = new BufferedImage(this.getWidth(),this.getHeight(),BufferedImage.TYPE_INT_ARGB);
        //Create graphics for the BImage
        Graphics2D g2d = BImage.createGraphics();
        //Set information board
        g2d.drawImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/InfBoard.png")), 0, 0,this);
        g2d.setColor(Color.white);
        g2d.setFont(new Font("楷体",Font.BOLD,28));
        //Turn on font anti-aliasing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //Draw the string
        for (int i = 0;i < 10;i ++) {
            g2d.drawString(logString[i], 100, 60 + i * 40);
        }
        //Refresh picture to g
        g.drawImage(BImage, 0, 0, null);
    }

    public String AddLog (String s) {
        //Process the data
        log.append(s + "\n");
        if (Address < 10) {
            logString[Address] = s;
            Address ++;
        } else {
            for (int i = 0;i < 9;i ++) {
                logString[i] = logString[i + 1];
            }
            logString[9] = s;
        }
        //Refresh the board
        this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
        return new String(log);
    }

    public void Clear() {
        this.log = new StringBuffer();
        for (int i = 0;i < 10; i ++) {
            logString[i] = "";
        }
        Address = 0;
    }
}
