import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ChessPieceClick extends MouseAdapter {
    @Override
    public void mouseClicked(MouseEvent arg0) {
        Audio DoPieceSound = new Audio(MainFrame.class.getResource("/music/dopiece.wav").getPath().substring(1),false);
        Audio WinSound = new Audio(MainFrame.class.getResource("/music/win.wav").getPath().substring(1),false);
        for (int i = 0 ; i < MainFrame.MyBoarder.MyPieces.length ; i ++) {
            for (int j = 0 ; j < MainFrame.MyBoarder.MyPieces[0].length ; j ++) {
                MainFrame.TempBoarder.MyPieces[i][j] = MainFrame.MyBoarder.MyPieces[i][j];
            }
        }
        int x,y;
        x = arg0.getX();
        y = arg0.getY();
        x = (x - DefaultSet.ChessBoarderXX) / DefaultSet.ChessBoarderPP;
        y = (y - DefaultSet.ChessBoarderYY) / DefaultSet.ChessBoarderPP;
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
                                if(MainFrame.DoPlayer == '红'){
                                    MainFrame.record.add(new int[]{9 - (int)MainFrame.MyBoarder.p1.getX(), 10 - (int)MainFrame.MyBoarder.p1.getY()});
                                    MainFrame.record.add(new int[]{9 - (int)MainFrame.MyBoarder.p2.getX(), 10 - (int)MainFrame.MyBoarder.p2.getY()});
                                }else if(MainFrame.DoPlayer == '黑'){
                                    MainFrame.record.add(new int[]{1 + (int)MainFrame.MyBoarder.p1.getX(), 1 + (int)MainFrame.MyBoarder.p1.getY()});
                                    MainFrame.record.add(new int[]{1 + (int)MainFrame.MyBoarder.p2.getX(), 1 + (int)MainFrame.MyBoarder.p2.getY()});
                                }
                                char Winner = MainFrame.MyBoarder.Winner();
                                if (Winner == '红') {
                                    WinSound.play();
                                    ((ChessBoarderCanvas)arg0.getSource()).SendWinner('红');
                                    MainFrame.InfBoard.AddLog("红方获得胜利!");
                                    System.out.println(MainFrame.RedPlayer);
                                    RankingList.WriteList(MainFrame.RedPlayer);
                                } else if (Winner == '黑') {
                                    WinSound.play();
                                    ((ChessBoarderCanvas)arg0.getSource()).SendWinner('黑');
                                    MainFrame.InfBoard.AddLog("黑方获得胜利!");
                                    System.out.println(MainFrame.BlackPlayer);
                                    RankingList.WriteList(MainFrame.BlackPlayer);
                                } else {
                                    MainFrame.MyBoarder.p1 = null;
                                    MainFrame.MyBoarder.p2 = null;
                                    SwitchDoPlayer();
                                    if(MainFrame.AIPlay && MainFrame.DoPlayer == '黑'){
                                        MainFrame.Human_AI();
                                    }
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
                                    if(MainFrame.DoPlayer == '红'){
                                        MainFrame.record.add(new int[]{9 - (int)MainFrame.MyBoarder.p1.getX(), 10 - (int)MainFrame.MyBoarder.p1.getY()});
                                        MainFrame.record.add(new int[]{9 - (int)MainFrame.MyBoarder.p2.getX(), 10 - (int)MainFrame.MyBoarder.p2.getY()});
                                    }else if(MainFrame.DoPlayer == '黑'){
                                        MainFrame.record.add(new int[]{1 + (int)MainFrame.MyBoarder.p1.getX(), 1 + (int)MainFrame.MyBoarder.p1.getY()});
                                        MainFrame.record.add(new int[]{1 + (int)MainFrame.MyBoarder.p2.getX(), 1 + (int)MainFrame.MyBoarder.p2.getY()});
                                    }
                                    char Winner = MainFrame.MyBoarder.Winner();
                                    if (Winner == '红') {
                                        WinSound.play();
                                        ((ChessBoarderCanvas)arg0.getSource()).SendWinner('红');
                                        MainFrame.InfBoard.AddLog("红方获得胜利!");
                                        System.out.println(MainFrame.RedPlayer);
                                        RankingList.WriteList(MainFrame.RedPlayer);
                                    } else if (Winner == '黑') {
                                        WinSound.play();
                                        ((ChessBoarderCanvas)arg0.getSource()).SendWinner('黑');
                                        MainFrame.InfBoard.AddLog("黑方获得胜利!");
                                        System.out.println(MainFrame.BlackPlayer);
                                        RankingList.WriteList(MainFrame.BlackPlayer);
                                    } else {
                                        MainFrame.MyBoarder.p1 = null;
                                        MainFrame.MyBoarder.p2 = null;
                                        SwitchDoPlayer();
                                        if(MainFrame.AIPlay && MainFrame.DoPlayer == '黑'){
                                            MainFrame.Human_AI();
                                        }
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
        ((ChessBoarderCanvas)arg0.getSource()).repaint();
        ((ChessBoarderCanvas)arg0.getSource()).paintImmediately(0, 0, ((ChessBoarderCanvas)arg0.getSource()).getWidth(), ((ChessBoarderCanvas)arg0.getSource()).getHeight());
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
