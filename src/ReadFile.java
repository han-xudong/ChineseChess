import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
    private char lastMover;
    private ChessPieces[][] setPieces = new ChessPieces[10][9];
    private boolean FailedLoading = false;
    private int step = 0;
    private int[][] move;

    public ReadFile (String str) {
        for(int i = 0 ; i < setPieces.length ; i ++){
            for(int j = 0 ; j < setPieces[0].length ; j ++){
                setPieces[i][j] = MainFrame.MyBoarder.MyPieces[i][j];
            }
        }
        if (str.contains(".chessboard")) {
            readChessBoard(str);
        } else if (str.contains(".chessmoveseq")) {
            readChessMoveSeq(str);
        }
    }

    public ReadFile() {
        readChessBoard("File/FailedLoading.chessboard");
    }

    public void readChessBoard (String address) {
        try {
            FileInputStream fis = new FileInputStream(new File(address));
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            int counter_G = 0,
                counter_C = 0,
                counter_H = 0,
                counter_N = 0,
                counter_A = 0,
                counter_E = 0,
                counter_S = 0,
                counter_s = 0,
                counter_c = 0,
                counter_h = 0,
                counter_n = 0,
                counter_a = 0,
                counter_g = 0,
                counter_e = 0;
            while (true) {
                String str = br.readLine();
                if (str.contains("@@")) {
                    break;
                }
                if (str.contains("LAST_MOVER")) {
                    if (str.contains("BLACK")) {
                        lastMover = '黑';
                    } else if (str.contains("RED")) {
                        lastMover = '红';
                    }
                }
            }
            br.readLine();
            boolean haveRiver = false;
            boolean lessList = false;
            char[][] tempBox = new char[10][9];
            int counter = 0;
            while (true) {
                String str = br.readLine();
                if (null == str) {
                    break;
                }
                if (str.equals("")){
                    FailedLoading = true;
                }
                if(str.equals("---------") && counter == 5){
                    haveRiver = true;
                }
                if(str.length() < 9){
                    lessList = true;
                }
                if (!str.contains("-") && !str.equals("")) {
                    char[] line = str.toCharArray();
                    System.arraycopy(line, 0, tempBox[counter], 0, line.length);
                    counter ++;
                    if (counter >= 10) {
                        break;
                    }
                }
            }
            br.close();
            if (counter < 10) {
                FailedLoading = true;
            }
            if (!haveRiver) {
                FailedLoading = true;
            }
            if (lessList){
                FailedLoading = true;
            }
            for (int i = 0 ; i < tempBox.length ; i ++) {
                for (int j = 0 ; j < tempBox[0].length ; j ++) {
                    switch (tempBox[i][j]) {
                        case 'G':
                            setPieces[i][j] = new ChessPieces(0);
                            counter_G++;
                            if(counter_G > 1){
                            FailedLoading = true;
                            }
                            break;
                        case 'C':
                            setPieces[i][j] = new ChessPieces(1);
                            counter_C++;
                            if(counter_C > 2){
                                FailedLoading = true;
                            }
                            break;
                        case 'H':
                            setPieces[i][j] = new ChessPieces(2);
                            counter_H++;
                            if(counter_H > 2){
                                FailedLoading = true;
                            }
                            break;
                        case 'N':
                            setPieces[i][j] = new ChessPieces(3);
                            counter_N++;
                            if(counter_N > 2){
                                FailedLoading = true;
                            }
                            break;
                        case 'A':
                            setPieces[i][j] = new ChessPieces(4);
                            counter_A++;
                            if(counter_A > 2){
                                FailedLoading = true;
                            }
                            break;
                        case 'E':
                            setPieces[i][j] = new ChessPieces(5);
                            counter_E++;
                            if(counter_E > 2){
                                FailedLoading = true;
                            }
                            break;
                        case 'S':
                            setPieces[i][j] = new ChessPieces(6);
                            counter_S++;
                            if(counter_S > 5){
                                FailedLoading = true;
                            }
                            break;
                        case 's':
                            setPieces[i][j] = new ChessPieces(7);
                            counter_s++;
                            if(counter_s > 5){
                                FailedLoading = true;
                            }
                            break;
                        case 'c':
                            setPieces[i][j] = new ChessPieces(8);
                            counter_c++;
                            if(counter_c > 2){
                                FailedLoading = true;
                            }
                            break;
                        case 'h':
                            setPieces[i][j] = new ChessPieces(9);
                            counter_h++;
                            if(counter_h > 2){
                                FailedLoading = true;
                            }
                            break;
                        case 'n':
                            setPieces[i][j] = new ChessPieces(10);
                            counter_n++;
                            if(counter_n > 2){
                                FailedLoading = true;
                            }
                            break;
                        case 'a':
                            setPieces[i][j] = new ChessPieces(11);
                            counter_a++;
                            if(counter_a > 2){
                                FailedLoading = true;
                            }
                            break;
                        case 'g':
                            setPieces[i][j] = new ChessPieces(12);
                            counter_g++;
                            if(counter_g > 1){
                                FailedLoading = true;
                            }
                            break;
                        case 'e':
                            setPieces[i][j] = new ChessPieces(13);
                            counter_e++;
                            if(counter_e > 2){
                                FailedLoading = true;
                            }
                            break;
                        default:
                            setPieces[i][j] = null;
                    }
                }
            }
            if (counter_G == 0 || counter_g == 0){
                FailedLoading = true;
            }
            if(FailedLoading){
                for(int i = 0 ; i < MainFrame.MyBoarder.MyPieces.length ; i ++){
                    for(int j = 0 ; j < MainFrame.MyBoarder.MyPieces[0].length ; j ++){
                        setPieces[i][j] = MainFrame.MyBoarder.MyPieces[i][j];
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readChessMoveSeq (String address) {
        try {
            FileInputStream fis = new FileInputStream(new File(address));
            InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            // Read last mover
            while (true) {
                String str = br.readLine();
                if (str.contains("@@")) {
                    break;
                }
                if (str.contains("TOTAL_STEP")) {
                    step = Integer.parseInt(str.substring(12));
                }
            }
            // Read chessboard
            move = new int[getStep()][4];
            int counter = 0;
            while (true) {
                String str = br.readLine();
                if (str == null){
                    break;
                }
                if (str.equals("")){
                    continue;
                }
                List<String> list = new ArrayList<>();
                for(String sss:str.replaceAll("[^0-9]", ",").split(",")){
                    if (sss.length()>0)
                        list.add(sss);
                }
                for (int j = 0 ; j < 4 ; j ++) {
                    move[counter][j] = Integer.parseInt(list.get(j));
                }
                counter++;
                if(counter >= getStep()){
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public char getLastMover() {
        return lastMover;
    }

    public ChessPieces[][] getSetPieces() {
        return setPieces;
    }

    public boolean isFailedLoading() {
        return FailedLoading;
    }

    public int getStep() {
        return step;
    }

    public int[][] getMove() {
        return move;
    }
}
