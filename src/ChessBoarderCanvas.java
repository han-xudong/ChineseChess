import javax.swing.*;
import java.awt.*;

public class ChessBoarderCanvas extends JPanel {
    ChessBoarder MyChessBoarder;
    //Canvas' background
    private Image BgImage;
    //Up-left coordinate
    private int Mysx1;
    private int Mysy1;
    //Down-right coordinate
    private int Mysx2;
    private int Mysy2;
    private char Winner = '无';

    public int SendData (ChessBoarder srcChess,Image srcImage,int sx1,int sy1,int sx2,int sy2) {
        MyChessBoarder = srcChess;
        BgImage = srcImage;
        Mysx1 = sx1;
        Mysy1 = sy1;
        Mysx2 = sx2;
        Mysy2 = sy2;
        return 0;
    }

    public void SendWinner (char a) {
        this.Winner = a;
    }

    @Override
    public void paintComponent (Graphics g) {
        super.paintComponent(g);

        //Draw canvas background
        g.drawImage(BgImage, 0, 0, this.getWidth(), this.getHeight(), Mysx1, Mysy1, Mysx2, Mysy2,this);

        //Draw chessboard
        g.drawImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/chessboardafter.png")), 0, 0,
                Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/chessboardafter.png")).getWidth(null),
                Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/chessboardafter.png")).getHeight(null), this);

        //Draw chess
        int xx = DefaultSet.ChessBoarderXX;
        int yy = DefaultSet.ChessBoarderYY;
        int pp = DefaultSet.ChessBoarderPP;
        for (int i = 0;i < 10;i ++) {
            for (int j = 0;j < 9;j ++) {
                if (MainFrame.MyBoarder.MyPieces[i][j] != null) {
                    g.drawImage(MainFrame.MyBoarder.MyPieces[i][j].Icon,
                            xx + j  * pp, yy + i * pp,
                            MainFrame.MyBoarder.MyPieces[i][j].Icon.getWidth(null) , MainFrame.MyBoarder.MyPieces[i][j].Icon.getHeight(null), this);
                }
            }
        }
        //绘制2个框
        if (MainFrame.MyBoarder.p1 != null) {
            g.drawImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/kuang.png")),
                    xx +  MainFrame.MyBoarder.p1.x * pp, yy + MainFrame.MyBoarder.p1.y * pp,
                    Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/kuang.png")).getWidth(null) , Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/kuang.png")).getHeight(null), this);
        }
        if (MainFrame.MyBoarder.p2 != null) {
            g.drawImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/kuang.png")),
                    xx +  MainFrame.MyBoarder.p2.x * pp, yy + MainFrame.MyBoarder.p2.y * pp,
                    Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/kuang.png")).getWidth(null) , Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/kuang.png")).getHeight(null), this);
        }
        //Draw winner picture
        if (this.Winner == '红') {
            g.drawImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/red-win.png")), 50, 270,559, 132, this);
        } else if (this.Winner == '黑') {
            g.drawImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/black-win.png")), 50, 270,559, 132, this);
        }
    }
}
