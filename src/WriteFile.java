import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public class WriteFile {
    private char lastMover;
    private ChessPieces[][] pieces = new ChessPieces[10][9];
    private String address;

    public WriteFile (String address, ChessPieces[][] getPieces, char lastMover) {
        this.address = address;
        for (int i = 0 ; i < getPieces.length ; i ++) {
            for (int j =  0 ; j < getPieces[0].length ; j ++) {
                pieces[i][j] = getPieces[i][j];
            }
        }
        this.lastMover = lastMover;
    }
    public void writeChessboard() {
        try {
            FileOutputStream fos = new FileOutputStream(new File(address));
            OutputStreamWriter osr = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osr);

            // Write in last mover
            bw.write("@LAST_MOVER=" + (lastMover == '红' ? "RED" : "BLACK") + "\n");
            bw.write("@@\n");
            bw.write("\n");
            // Write chessboard
            for (int i = 0 ; i < pieces.length + 1 ; i ++) {
                for (int j = 0 ; j < pieces[0].length ; j ++) {
                    if (i == 5) {
                        bw.write("-");
                    } else if(i < 5) {
                        if (pieces[i][j] != null) {
                            switch (pieces[i][j].id) {
                                case 0:
                                    bw.write("G");
                                    break;
                                case 1:
                                    bw.write("C");
                                    break;
                                case 2:
                                    bw.write("H");
                                    break;
                                case 3:
                                    bw.write("N");
                                    break;
                                case 4:
                                    bw.write("A");
                                    break;
                                case 5:
                                    bw.write("E");
                                    break;
                                case 6:
                                    bw.write("S");
                                    break;
                                case 7:
                                    bw.write("s");
                                    break;
                                case 8:
                                    bw.write("c");
                                    break;
                                case 9:
                                    bw.write("h");
                                    break;
                                case 10:
                                    bw.write("n");
                                    break;
                                case 11:
                                    bw.write("a");
                                    break;
                                case 12:
                                    bw.write("g");
                                    break;
                                case 13:
                                    bw.write("e");
                                    break;
                                default:
                                    bw.write(".");
                                    break;
                            }
                        } else {
                            bw.write(".");
                        }
                    } else {
                        if (pieces[i - 1][j] != null) {
                            switch (pieces[i - 1][j].id) {
                                case 0:
                                    bw.write("G");
                                    break;
                                case 1:
                                    bw.write("C");
                                    break;
                                case 2:
                                    bw.write("H");
                                    break;
                                case 3:
                                    bw.write("N");
                                    break;
                                case 4:
                                    bw.write("A");
                                    break;
                                case 5:
                                    bw.write("E");
                                    break;
                                case 6:
                                    bw.write("S");
                                    break;
                                case 7:
                                    bw.write("s");
                                    break;
                                case 8:
                                    bw.write("c");
                                    break;
                                case 9:
                                    bw.write("h");
                                    break;
                                case 10:
                                    bw.write("n");
                                    break;
                                case 11:
                                    bw.write("a");
                                    break;
                                case 12:
                                    bw.write("g");
                                    break;
                                case 13:
                                    bw.write("e");
                                    break;
                                default:
                                    bw.write(".");
                                    break;
                            }
                        } else {
                            bw.write(".");
                        }
                    }
                }
                bw.write("\n");
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
