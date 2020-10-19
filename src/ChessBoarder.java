import java.awt.Point;

public class ChessBoarder {
    // Chess array of nine columns and 10 rows
    // 'Null' indicates that there are no pieces on the chessboard at the current position
    public ChessPieces[][] MyPieces;

    // Use p1 and p2 to indicate the route of the pieces , 'null' indicates no display
    public Point p1;
    public Point p2;
    private String [][] StrPos = {{"1","2","3","4","5","6","7","8","9"},
            {"九","八","七","六","五","四","三","二","一"}};

    public ChessBoarder() {
        // Initialize p1 and p2
        p1 = null;
        p2 = null;
        // Initialize the chess array
        MyPieces = new ChessPieces[10][9];
        for (int i = 0;i < 10;i ++) {
            for (int j = 0;j < 9;j ++) {
                MyPieces[i][j] = null;
            }
        }
        // Set default chess positions
        // Set C, blk-ju
        MyPieces[0][0] = new ChessPieces(1);
        // Set H, blk-ma
        MyPieces[0][1] = new ChessPieces(2);
        // Set E, blk-xiang
        MyPieces[0][2] = new ChessPieces(5);
        //Set A, blk-shi
        MyPieces[0][3] = new ChessPieces(4);
        //Set G, blk-jiang
        MyPieces[0][4] = new ChessPieces(0);
        //Set A, blk-shi
        MyPieces[0][5] = new ChessPieces(4);
        //Set E, blk-xiang
        MyPieces[0][6] = new ChessPieces(5);
        //Set H, blk-ma
        MyPieces[0][7] = new ChessPieces(2);
        //Set C, blk-ju
        MyPieces[0][8] = new ChessPieces(1);
        //Set S, blk-zu
        for (int i = 0;i < 9;i += 2){
            MyPieces[3][i] = new ChessPieces(6);
        }
        //Set N, blk-pao
        MyPieces[2][1] = new ChessPieces(3);
        MyPieces[2][7] = new ChessPieces(3);

        //Set c, red-ju
        MyPieces[9][0] = new ChessPieces(8);
        //Set h, red-ma
        MyPieces[9][1] = new ChessPieces(9);
        //Set e, red-xiang
        MyPieces[9][2] = new ChessPieces(13);
        //Set a, red-shi
        MyPieces[9][3] = new ChessPieces(11);
        //Set g, red-shuai
        MyPieces[9][4] = new ChessPieces(12);
        //Set a, red-shi
        MyPieces[9][5] = new ChessPieces(11);
        //Set e, red-xiang
        MyPieces[9][6] = new ChessPieces(13);
        //Set h, red-ma
        MyPieces[9][7] = new ChessPieces(9);
        //Set c, red-ju
        MyPieces[9][8] = new ChessPieces(8);
        //Set s, red-bing
        for (int i = 0;i < 9;i += 2){
            MyPieces[6][i] = new ChessPieces(7);
        }
        //Set n, red-pao
        MyPieces[7][1] = new ChessPieces(10);
        MyPieces[7][7] = new ChessPieces(10);
    }

    public ChessBoarder (ChessPieces[][] pieces) {
        MyPieces = new ChessPieces[10][9];
        for (int i = 0 ; i < pieces.length ; i ++) {
            for (int j = 0 ; j < pieces[0].length ; j ++) {
                MyPieces[i][j] = pieces[i][j];
            }
        }
    }

    //Chess move
    //src is the origin position
    //des is the target position
    //if it can be move, return true; else, return false
    public boolean PieceMove (Point src,Point des) {
        if (MyPieces[src.y][src.x] == null) {
            // If there is no pieces in the start position, return false
            return false;
        } else {
            // There exists a piece in the start position
            switch (MyPieces[src.y][src.x].name.charAt(1)) {
                case '将':
                    // Judge if still in the 3x3 board
                    if (des.x >= 3 && des.x <= 5 && des.y >= 0 && des.y <= 2) {
                        if (Distance(src,des) == 1) {
                            // Walk 1 step
                            if (src.y == des.y) {
                                MainFrame.InfBoard.AddLog("将" + StrPos[0][src.x] + "平" + StrPos[0][des.x]);
                            } else {
                                MainFrame.InfBoard.AddLog("将" + StrPos[0][src.x] + (des.y > src.y ? "进" : "退") + "一");
                            }
                            MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                            MyPieces[src.y][src.x] = null;
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                case '帅':
                    // Judge if still in the 3x3 board
                    if (des.x >= 3 && des.x <= 5 && des.y >= 7 && des.y <= 9) {
                        if (Distance(src,des) == 1) {
                            // Walk 1 step
                            if (src.y == des.y) {
                                MainFrame.InfBoard.AddLog("帅" + StrPos[1][src.x] + "平" + StrPos[1][des.x]);
                            } else {
                                MainFrame.InfBoard.AddLog("帅" + StrPos[1][src.x] + (des.y < src.y ? "进" : "退") + "一");
                            }
                            MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                            MyPieces[src.y][src.x] = null;
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                case '士':
                    // Judge if red
                    if (MyPieces[src.y][src.x].name.charAt(0) == '红') {
                        // Judge if still in the 3x3 board
                        if (des.x >= 3 && des.x <= 5 && des.y >= 7 && des.y <= 9) {
                            // Judge if going sideways
                            if (Distance(src,des) > 1.4 && Distance(src,des) < 1.5) {
                                MainFrame.InfBoard.AddLog("士" + StrPos[1][src.x] + (des.y < src.y ? "进" : "退") +StrPos[1][des.x]);
                                MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                                MyPieces[src.y][src.x] = null;
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        // Judge if still in the 3x3 board
                        if (des.x >= 3 && des.x <= 5 && des.y >= 0 && des.y <= 2) {
                            // Judge if going sideways
                            if (Distance(src,des) > 1.4 && Distance(src,des) < 1.5) {
                                MainFrame.InfBoard.AddLog("士" + StrPos[1][src.x] + (des.y > src.y ? "进" : "退") +StrPos[1][des.x]);
                                MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                                MyPieces[src.y][src.x] = null;
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    }
                case '象':
                    // Judge whether crossing the river
                    if (des.y <= 4 ){
                        // Judge if going sideways for two grids
                        if (Distance(src,des) > 2.8 && Distance(src,des) < 2.9) {
                            // Judge if can move to des
                            if (MyPieces[(des.y + src.y)/2][(des.x + src.x)/2] == null) {
                                MainFrame.InfBoard.AddLog("象" + StrPos[0][src.x] + (des.y > src.y ? "进" : "退") +StrPos[0][des.x]);
                                MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                                MyPieces[src.y][src.x] = null;
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                case '相':
                    // Judge whether crossing the river
                    if (des.y >= 5 ){
                        // Judge if going sideways for two grids
                        if (Distance(src,des) > 2.8 && Distance(src,des) < 2.9) {
                            // Judge if can move to des
                            if (MyPieces[(des.y + src.y)/2][(des.x + src.x)/2] == null) {
                                MainFrame.InfBoard.AddLog("相" + StrPos[1][src.x] + (des.y < src.y ? "进" : "退") +StrPos[1][des.x]);
                                MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                                MyPieces[src.y][src.x] = null;
                                return true;
                            }
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                case '马':
                    // Judge if moving for the right distance
                    if (Distance(src,des) > 2.2 && Distance(src,des) < 2.3) {
                        // Judge whether obstructed the horse foot
                        if (Math.abs(src.x - des.x) == 1) {
                            if (MyPieces[(src.y+des.y)/2][src.x] == null) {
                                if (MyPieces[src.y][src.x].name.charAt(0) == '黑' ) {
                                    MainFrame.InfBoard.AddLog("马" + StrPos[0][src.x] + (des.y > src.y ? "进" : "退") +StrPos[0][des.x]);
                                } else {
                                    MainFrame.InfBoard.AddLog("马" + StrPos[1][src.x] + (des.y < src.y ? "进" : "退") +StrPos[1][des.x]);
                                }
                                MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                                MyPieces[src.y][src.x] = null;
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            if (MyPieces[src.y][(src.x+des.x)/2] == null) {
                                if (MyPieces[src.y][src.x].name.charAt(0) == '黑' ) {
                                    MainFrame.InfBoard.AddLog("马" + StrPos[0][src.x] + (des.y > src.y ? "进" : "退") +StrPos[0][des.x]);
                                } else {
                                    MainFrame.InfBoard.AddLog("马" + StrPos[1][src.x] + (des.y < src.y ? "进" : "退") +StrPos[1][des.x]);
                                }
                                MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                                MyPieces[src.y][src.x] = null;
                                return true;
                            } else {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                case '车':
                    if (src.y == des.y || src.x == des.x) {
                        // There is no pieces block on the its line
                        if (IsBlock(src,des) == 0) {
                            if (MyPieces[src.y][src.x].name.charAt(0) == '红' ) {
                                if (src.y == des.y) {
                                    MainFrame.InfBoard.AddLog("车" + StrPos[1][src.x] + "平" + StrPos[1][des.x]);
                                } else {
                                    MainFrame.InfBoard.AddLog("车" + StrPos[1][src.x] + (des.y < src.y ? "进" : "退") + StrPos[0][Math.abs(des.y - src.y) - 1]);
                                }
                            } else {
                                if (src.y == des.y) {
                                    MainFrame.InfBoard.AddLog("车" + StrPos[0][src.x] + "平" + StrPos[0][des.x]);
                                } else {
                                    MainFrame.InfBoard.AddLog("车" + StrPos[0][src.x] + (des.y > src.y ? "进" : "退") + StrPos[0][Math.abs(des.y - src.y) - 1]);
                                }
                            }
                            MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                            MyPieces[src.y][src.x] = null;
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                case '炮':
                    if (src.y == des.y || src.x == des.x) {
                        // There is no pieces block on the its line
                        if (IsBlock(src,des) == 0) {
                            if (MyPieces[src.y][src.x].name.charAt(0) == '红' ) {
                                if (src.y == des.y) {
                                    MainFrame.InfBoard.AddLog("炮" + StrPos[1][src.x] + "平" + StrPos[1][des.x]);
                                } else {
                                    MainFrame.InfBoard.AddLog("炮" + StrPos[1][src.x] + (des.y < src.y ? "进" : "退") + StrPos[0][Math.abs(des.y - src.y) - 1]);
                                }
                            } else {
                                if (src.y == des.y) {
                                    MainFrame.InfBoard.AddLog("炮" + StrPos[0][src.x] + "平" + StrPos[0][des.x]);
                                } else {
                                    MainFrame.InfBoard.AddLog("炮" + StrPos[0][src.x] + (des.y > src.y ? "进" : "退") + StrPos[0][Math.abs(des.y - src.y) - 1]);
                                }
                            }
                            MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                            MyPieces[src.y][src.x] = null;
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                case '卒':
                    // Judge if step ahead
                    if (des.y >= src.y) {
                        // Judge if crossing the river
                        if (des.y >= 5) {
                            if (Distance(src,des) == 1) {
                                if (src.y == des.y) {
                                    MainFrame.InfBoard.AddLog("卒" + StrPos[0][src.x] + "平" + StrPos[0][des.x]);
                                } else {
                                    MainFrame.InfBoard.AddLog("卒" + StrPos[0][src.x] +"进" + "一") ;
                                }
                                MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                                MyPieces[src.y][src.x] = null;
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            if (des.x == src.x && Math.abs(des.y - src.y) == 1){
                                MainFrame.InfBoard.AddLog("卒" + StrPos[0][src.x] + "进" + "一") ;
                                MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                                MyPieces[src.y][src.x] = null;
                                return true;
                            } else {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
                case '兵':
                    // Judge if step ahead
                    if (des.y <= src.y){
                        // Judge if crossing the river
                        if (des.y <= 4){
                            if (Distance(src,des) == 1){
                                if (src.y == des.y){
                                    MainFrame.InfBoard.AddLog("兵" + StrPos[1][src.x] + "平" + StrPos[1][des.x]);
                                } else {
                                    MainFrame.InfBoard.AddLog("兵" + StrPos[1][src.x] +"进" + "一") ;
                                }
                                MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                                MyPieces[src.y][src.x] = null;
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            if (des.x == src.x && Math.abs(des.y - src.y) == 1){
                                MainFrame.InfBoard.AddLog("兵" + StrPos[1][src.x] + "进" + "一") ;
                                MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                                MyPieces[src.y][src.x] = null;
                                return true;
                            } else {
                                return false;
                            }
                        }
                    } else {
                        return false;
                    }
            }
        }
        return true;
    }


    public boolean PieceEat (Point src,Point des) {
        if (MyPieces[src.y][src.x] == null) {
            // If there is no chess in the source position, return false
            return false;
        } else {
            // there exits a chess
            switch (MyPieces[src.y][src.x].name.charAt(1)) {
                // Cannon is special
                case '炮':
                    if (src.y == des.y || src.x == des.x) {
                        // There's only one piece in the line
                        if (IsBlock(src,des) == 1) {
                            if (MyPieces[src.y][src.x].name.charAt(0) == '红' ) {
                                if (src.y == des.y) {
                                    MainFrame.InfBoard.AddLog("炮" + StrPos[1][src.x] + "平" + StrPos[1][des.x]);
                                } else {
                                    MainFrame.InfBoard.AddLog("炮" + StrPos[1][src.x] + (des.y < src.y ? "进" : "退") + StrPos[0][Math.abs(des.y - src.y) - 1]);
                                }
                            } else {
                                if (src.y == des.y) {
                                    MainFrame.InfBoard.AddLog("炮" + StrPos[0][src.x] + "平" + StrPos[0][des.x]);
                                } else {
                                    MainFrame.InfBoard.AddLog("炮" + StrPos[0][src.x] + (des.y > src.y ? "进" : "退") + StrPos[0][Math.abs(des.y - src.y) - 1]);
                                }
                            }
                            MyPieces[des.y][des.x] = MyPieces[src.y][src.x];
                            MyPieces[src.y][src.x] = null;
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return false;
                    }
                default:
                    return PieceMove(src,des);
            }
        }
    }

    public float Distance(Point a,Point b){
        return (float)Math.sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
    }

    public int IsBlock(Point a,Point b){
        int n = 0;
        if (a.x == b.x) {
            int min,max;
            if (a.y > b.y) {
                min = b.y;
                max = a.y;
            } else {
                min = a.y;
                max = b.y;
            }
            for (int i = min + 1;i < max;i ++) {
                if (MyPieces[i][a.x] != null) {
                    n ++;
                }
            }
        } else {
            int min,max;
            if (a.x > b.x) {
                min = b.x;
                max = a.x;
            } else {
                min = a.x;
                max = b.x;
            }
            for (int i = min + 1;i < max;i ++) {
                if (MyPieces[a.y][i] != null) {
                    n ++;
                }
            }
        }
        return n;
    }


    public char Winner() {
        // Judge if G, blk-jiang exists
        Point pp1 = null;
        boolean ExJ = false;
        for (int i = 0;i <= 8;i ++) {
            for (int j = 0;j <=9;j ++) {
                if (MyPieces[j][i] != null) {
                    if (MyPieces[j][i].name.charAt(1) == '将') {
                        pp1 = new Point(i,j);
                        ExJ = true;
                    }
                }
            }
        }
        if (!ExJ) {
            MainFrame.DoPlayer = '无';
            return '红';
        }
        // Judge if g, red-shuai exists
        Point pp2 = null;
        boolean ExS = false;
        for (int i = 0;i <= 8;i ++) {
            for (int j = 0;j <=9;j ++) {
                if (MyPieces[j][i] != null) {
                    if (MyPieces[j][i].name.charAt(1) == '帅') {
                        pp2 = new Point(i,j);
                        ExS = true;
                    }
                }
            }
        }
        if (!ExS) {
            MainFrame.DoPlayer = '无';
            return '黑';
        }
        // Both exists
        // Judge if face to face
        if (pp1.x == pp2.x && IsBlock(pp1,pp2) == 0) {
            char TempC;
            TempC = MainFrame.DoPlayer;
            if (TempC == '红') {
                TempC = '黑';
            } else if (TempC == '黑') {
                TempC = '红';
            }
            MainFrame.DoPlayer = '无';
            return TempC;
        }
        return '无';
    }
}
