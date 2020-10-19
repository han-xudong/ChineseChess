import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DiyButton extends JLabel {
    private ImageIcon p0;
    private ImageIcon p1;
    public DiyButton (String pic0,String pic1) {
        p0 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource(pic0)));
        p1 = new ImageIcon(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource(pic1)));
        //Default set is not entered
        this.setIcon(p0);
        //Add mouse listener
        this.addMouseListener (new MouseAdapter() {
            @Override
            public void mouseEntered (MouseEvent arg0) {
                //Turn to be entered
                ((DiyButton)arg0.getSource()).setIcon(p1);
            }
            public void mouseExited (MouseEvent arg0) {
                //Turn to not be entered
                ((DiyButton)arg0.getSource()).setIcon(p0);
            }
        });
    }
}
