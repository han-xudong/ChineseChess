import java.awt.*;

public class ChessBoardDemo {

    public void movePieces(int x, int y){
        Audio DoPieceSound = new Audio(MainFrame.class.getResource("/music/dopiece.wav").getPath().substring(1),false);
        Audio WinSound = new Audio(MainFrame.class.getResource("/music/win.wav").getPath().substring(1),false);
        if (x < 0 || x > 8 || y < 0 || y > 9) {
            // Click out of chessboard
            return;
        } else {
            if (MainFrame.MyBoarder.p1 == null) {
                // No chess chosen
                MainFrame.MyBoarder.p1 = new Point(x,y);
            } else {
                if (MainFrame.MyBoarder.MyPieces[MainFrame.MyBoarder.p1.y][MainFrame.MyBoarder.p1.x] == null) {
                    // Chess chosen is empty
                    MainFrame.MyBoarder.p1 = new Point(x,y);
                } else {
                    // Chess chosen is not empty
                    if (MainFrame.MyBoarder.MyPieces[MainFrame.MyBoarder.p1.y][MainFrame.MyBoarder.p1.x].name.charAt(0) != MainFrame.DoPlayer) {
                        //Chess chosen is not self
                        MainFrame.MyBoarder.p1 = new Point(x,y);
                    } else {
                        // Chess chosen is self
                        if (MainFrame.MyBoarder.MyPieces[y][x] == null) {
                            // Second chess chosen is empty
                            MainFrame.MyBoarder.p2 = new Point(x,y);
                            if (MainFrame.MyBoarder.PieceMove(MainFrame.MyBoarder.p1, MainFrame.MyBoarder.p2)) {
                                // Chess chosen can be moved
                                DoPieceSound.play();
                                char Winner = MainFrame.MyBoarder.Winner();
                                if (Winner == '红') {
                                    WinSound.play();
                                    MainFrame.MyCanvas.SendWinner('红');
                                    MainFrame.InfBoard.AddLog("红方获得胜利!");
                                } else if (Winner == '黑') {
                                    WinSound.play();
                                    MainFrame.MyCanvas.SendWinner('黑');
                                    MainFrame.InfBoard.AddLog("黑方获得胜利!");
                                } else {
                                    MainFrame.MyBoarder.p1 = null;
                                    MainFrame.MyBoarder.p2 = null;
                                    SwitchDoPlayer();
                                }
                            } else {
                                // Chess chosen can not be moved
                                MainFrame.MyBoarder.p1 = new Point(x,y);
                                MainFrame.MyBoarder.p2 = null;
                            }
                        } else {
                            // Second chess chosen is not empty
                            if (MainFrame.MyBoarder.MyPieces[y][x].name.charAt(0) == MainFrame.DoPlayer) {
                                // Second chess chosen is self
                                if (MainFrame.MyBoarder.p1.getX() == x && MainFrame.MyBoarder.p1.getY() == y) {
                                    MainFrame.MyBoarder.p1 = null;
                                } else {
                                    MainFrame.MyBoarder.p1 = new Point(x,y);
                                }
                            } else {
                                // Second chess chosen is not self
                                MainFrame.MyBoarder.p2 = new Point(x,y);
                                if (MainFrame.MyBoarder.PieceEat(MainFrame.MyBoarder.p1, MainFrame.MyBoarder.p2)) {
                                    // Chess chosen can be eaten
                                    DoPieceSound.play();
                                    char Winner = MainFrame.MyBoarder.Winner();
                                    if (Winner == '红') {
                                        WinSound.play();
                                        MainFrame.MyCanvas.SendWinner('红');
                                        MainFrame.InfBoard.AddLog("红方获得胜利!");
                                    } else if (Winner == '黑') {
                                        WinSound.play();
                                        MainFrame.MyCanvas.SendWinner('黑');
                                        MainFrame.InfBoard.AddLog("黑方获得胜利!");
                                    } else {
                                        MainFrame.MyBoarder.p1 = null;
                                        MainFrame.MyBoarder.p2 = null;
                                        SwitchDoPlayer();
                                    }
                                } else {
                                    // Chess chosen can not be eaten
                                    MainFrame.MyBoarder.p1 = new Point(x,y);
                                    MainFrame.MyBoarder.p2 = null;
                                }
                            }
                        }
                    }
                }
            }
        }
        MainFrame.MyCanvas.repaint();
        MainFrame.MyCanvas.paintImmediately(0, 0, MainFrame.MyCanvas.getWidth(), MainFrame.MyCanvas.getHeight());
    }
    static public void SwitchDoPlayer() {
        if (MainFrame.DoPlayer == '红') {
            MainFrame.DoPlayer = '黑';
            MainFrame.InfBoard.AddLog("黑方执子");
        } else if (MainFrame.DoPlayer == '黑') {
            MainFrame.DoPlayer = '红';
            MainFrame.InfBoard.AddLog("红方执子");
        }
    }
}
