import java.util.ArrayList;

public class Rules {

    private static int[] pos;
    private static char player;

    public static ArrayList<int[]> getNextMove(ChessPieces piece, int[] pos) {
        Rules.pos = pos;
        Rules.player = MainFrame.DoPlayer;
        switch (piece.name.charAt(1)) {
            case '车':
                return jRules();
            case '马':
                return mRules();
            case '炮':
                return pRules();
            case '相':
            case '象':
                return xRules();
            case '士':
                return sRules();
            case '帅':
            case '将':
                return bRules();
            case '卒':
            case '兵':
                return zRules();
            default:
                return null;
        }
    }

    private static ArrayList<int[]> mRules() {
        ArrayList<int[]> moves = new ArrayList<>();
        int[][] target = new int[][]{{1, -2}, {2, -1}, {2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}};
        int[][] obstacle = new int[][]{{0, -1}, {1, 0}, {1, 0}, {0, 1}, {0, 1}, {-1, 0}, {-1, 0}, {0, -1}};
        for (int i = 0; i < target.length; i++) {
            int[] e = new int[]{pos[0] + target[i][0], pos[1] + target[i][1]};
            int[] f = new int[]{pos[0] + obstacle[i][0], pos[1] + obstacle[i][1]};
            if (e[0] < 0 || e[0] > 9 || e[1] < 0 || e[1] > 8) continue;
            if (f[0] >= 0 && f[0] <= 8 && f[1] >= 0 && f[1] <= 9 && SearchModel.board.MyPieces[f[0]][f[1]] == null) {
                if (SearchModel.board.MyPieces[e[0]][e[1]] == null) moves.add(e);
                else if (SearchModel.board.MyPieces[e[0]][e[1]].name.charAt(0) != MainFrame.DoPlayer) moves.add(e);
            }
        }
        return moves;
    }

    private static ArrayList<int[]> jRules() {
        ArrayList<int[]> moves = new ArrayList<>();
        int[] yOffsets = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int[] xOffsets = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        for (int offset : yOffsets) {
            int[] rMove = new int[]{pos[0], pos[1] + offset};
            if (rMove[0] >= 0 && rMove[0] <= 9 && rMove[1] >= 0 && rMove[1] <= 8 && SearchModel.board.MyPieces[rMove[0]][rMove[1]] == null) moves.add(rMove);
            else if (rMove[0] >= 0 && rMove[0] <= 9 && rMove[1] >= 0 && rMove[1] <= 8 && SearchModel.board.MyPieces[rMove[0]][rMove[1]].name.charAt(0) != MainFrame.DoPlayer) {
                moves.add(rMove);
                break;
            } else break;
        }
        for (int offset : yOffsets) {
            int[] lMove = new int[]{pos[0], pos[1] - offset};
            if (lMove[0] >= 0 && lMove[0] <= 9 && lMove[1] >= 0 && lMove[1] <= 8 && SearchModel.board.MyPieces[lMove[0]][lMove[1]] == null) moves.add(lMove);
            else if (lMove[0] >= 0 && lMove[0] <= 9 && lMove[1] >= 0 && lMove[1] <= 8 && SearchModel.board.MyPieces[lMove[0]][lMove[1]].name.charAt(0) != MainFrame.DoPlayer) {
                moves.add(lMove);
                break;
            } else break;
        }
        for (int offset : xOffsets) {
            int[] uMove = new int[]{pos[0] - offset, pos[1]};
            if (uMove[0] >= 0 && uMove[0] <= 9 && uMove[1] >= 0 && uMove[1] <= 8 && SearchModel.board.MyPieces[uMove[0]][uMove[1]] == null) moves.add(uMove);
            else if (uMove[0] >= 0 && uMove[0] <= 9 && uMove[1] >= 0 && uMove[1] <= 8 && SearchModel.board.MyPieces[uMove[0]][uMove[1]].name.charAt(0) != MainFrame.DoPlayer) {
                moves.add(uMove);
                break;
            } else break;
        }
        for (int offset : xOffsets) {
            int[] dMove = new int[]{pos[0] + offset, pos[1]};
            if (dMove[0] >= 0 && dMove[0] <= 9 && dMove[1] >= 0 && dMove[1] <= 8 && SearchModel.board.MyPieces[dMove[0]][dMove[1]] == null) moves.add(dMove);
            else if (dMove[0] >= 0 && dMove[0] <= 9 && dMove[1] >= 0 && dMove[1] <= 8 && SearchModel.board.MyPieces[dMove[0]][dMove[1]].name.charAt(0) != MainFrame.DoPlayer) {
                moves.add(dMove);
                break;
            } else break;
        }
        return moves;
    }

    private static ArrayList<int[]> pRules() {
        ArrayList<int[]> moves = new ArrayList<>();
        int[] yOffsets = new int[]{1, 2, 3, 4, 5, 6, 7, 8};
        int[] xOffsets = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        boolean rr = false, ll = false, uu = false, dd = false;
        for (int offset : yOffsets) {
            int[] rMove = new int[]{pos[0], pos[1] + offset};
            if (rMove[0] < 0 || rMove[0] > 9 || rMove[1] < 0 || rMove[1] > 8) break;
            boolean e = (SearchModel.board.MyPieces[rMove[0]][rMove[1]] == null);
            if (!rr) {
                if (e) moves.add(rMove);
                else rr = true;
            } else if (!e) {
                if (SearchModel.board.MyPieces[rMove[0]][rMove[1]].name.charAt(0) != MainFrame.DoPlayer) moves.add(rMove);
                break;
            }
        }
        for (int offset : yOffsets) {
            int[] lMove = new int[]{pos[0], pos[1] - offset};
            if (lMove[0] < 0 || lMove[0] > 9 || lMove[1] < 0 || lMove[1] > 8) break;
            boolean e = (SearchModel.board.MyPieces[lMove[0]][lMove[1]] == null);
            if (!ll) {
                if (e) moves.add(lMove);
                else ll = true;
            } else if (!e) {
                if (SearchModel.board.MyPieces[lMove[0]][lMove[1]].name.charAt(0) != MainFrame.DoPlayer) moves.add(lMove);
                break;
            }
        }
        for (int offset : xOffsets) {
            int[] uMove = new int[]{pos[0] - offset, pos[1]};
            if (uMove[0] < 0 || uMove[0] > 9 || uMove[1] < 0 || uMove[1] > 8) break;
            boolean e = (SearchModel.board.MyPieces[uMove[0]][uMove[1]] == null);
            if (!uu) {
                if (e) moves.add(uMove);
                else uu = true;
            } else if (!e) {
                if (SearchModel.board.MyPieces[uMove[0]][uMove[1]].name.charAt(0) != MainFrame.DoPlayer) moves.add(uMove);
                break;
            }
        }
        for (int offset : xOffsets) {
            int[] dMove = new int[]{pos[0] + offset, pos[1]};
            if (dMove[0] < 0 || dMove[0] > 9 || dMove[1] < 0 || dMove[1] > 8) break;
            boolean e = (SearchModel.board.MyPieces[dMove[0]][dMove[1]] == null);
            if (!dd) {
                if (e) moves.add(dMove);
                else dd = true;
            } else if (!e) {
                if (SearchModel.board.MyPieces[dMove[0]][dMove[1]].name.charAt(0) != MainFrame.DoPlayer) moves.add(dMove);
                break;
            }
        }
        return moves;
    }

    private static ArrayList<int[]> xRules() {
        ArrayList<int[]> moves = new ArrayList<>();
        int[][] target = new int[][]{{-2, -2}, {2, -2}, {-2, 2}, {2, 2}};
        int[][] obstacle = new int[][]{{-1, -1}, {1, -1}, {-1, 1}, {1, 1}};
        for (int i = 0; i < target.length; i++) {
            int[] e = new int[]{pos[0] + target[i][0], pos[1] + target[i][1]};
            int[] f = new int[]{pos[0] + obstacle[i][0], pos[1] + obstacle[i][1]};
            if (e[0] < 0 || e[0] > 8 || e[1] < 0 || e[1] > 9 || (e[0] > 4 && player == '黑') || (e[0] < 5 && player == '红')) continue;
            if (f[0] >= 0 && f[0] <= 9 && f[1] >= 0 && f[1] <= 8 && SearchModel.board.MyPieces[f[0]][f[1]] == null) {
                if (SearchModel.board.MyPieces[e[0]][e[1]] == null) moves.add(e);
                else if (SearchModel.board.MyPieces[e[0]][e[1]].name.charAt(0) != MainFrame.DoPlayer) moves.add(e);
            }
        }
        return moves;
    }

    private static ArrayList<int[]> sRules() {
        ArrayList<int[]> moves = new ArrayList<>();
        int[][] target = new int[][]{{-1, -1}, {1, 1}, {-1, 1}, {1, -1}};
        for (int[] aTarget : target) {
            int[] e = new int[]{pos[0] + aTarget[0], pos[1] + aTarget[1]};
            if (e[0] < 0 || e[0] > 9 || e[1] < 0 || e[1] > 8 || ((e[0] > 2 || e[1] < 3 || e[1] > 5) && player == '黑') || ((e[0] < 7 || e[1] < 3 || e[1] > 5) && player == '红'))
                continue;
            if (SearchModel.board.MyPieces[e[0]][e[1]] == null) moves.add(e);
            else if (SearchModel.board.MyPieces[e[0]][e[1]].name.charAt(0) != MainFrame.DoPlayer) moves.add(e);
        }
        return moves;
    }

    private static ArrayList<int[]> bRules() {
        ArrayList<int[]> moves = new ArrayList<>();
        /* 3*3 block */
        int[][] target = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        for (int[] aTarget : target) {
            int[] e = new int[]{pos[0] + aTarget[0], pos[1] + aTarget[1]};
            if (e[0] < 0 || e[0] > 9 || e[1] < 0 || e[1] > 8 || ((e[0] > 2 || e[1] < 3 || e[1] > 5) && player == '黑') || ((e[0] < 7 || e[1] < 3 || e[1] > 5) && player == '红'))
                continue;
            if (SearchModel.board.MyPieces[e[0]][e[1]] == null) moves.add(e);
            else if (SearchModel.board.MyPieces[e[0]][e[1]].name.charAt(0) != MainFrame.DoPlayer) moves.add(e);
        }
        boolean flag = true;
        int[] oppoBoss = new int[2];
        for(int i = 0 ; i < SearchModel.board.MyPieces.length ; i ++){
            for(int j = 0 ; j < SearchModel.board.MyPieces[0].length ; j ++){
                if(SearchModel.board.MyPieces[i][j] != null && (SearchModel.board.MyPieces[i][j].name.charAt(1) == '将' || SearchModel.board.MyPieces[i][j].name.charAt(1) == '帅')){
                    oppoBoss = new int[]{j, i};
                }
            }
        }
        if (oppoBoss[1] == pos[1]) {
            for (int i = Math.min(oppoBoss[0], pos[0]) + 1; i < Math.max(oppoBoss[0], pos[0]); i++) {
                if (SearchModel.board.MyPieces[i][pos[1]] != null) {
                    flag = false;
                    break;
                }
            }
            if (flag) moves.add(oppoBoss);
        }
        return moves;
    }

    private static ArrayList<int[]> zRules() {
        ArrayList<int[]> moves = new ArrayList<>();
        int[][] targetU = new int[][]{{0, 1}, {0, -1}, {-1, 0}};
        int[][] targetD = new int[][]{{0, 1}, {0, -1}, {1, 0}};
        if (player == '红') {
            if (pos[0] > 4) {
                int[] e = new int[]{pos[0] - 1, pos[1]};
                if (SearchModel.board.MyPieces[e[0]][e[1]] == null) moves.add(e);
                else if (SearchModel.board.MyPieces[e[0]][e[1]].name.charAt(0) != MainFrame.DoPlayer) moves.add(e);
            } else {
                for (int[] aTarget : targetU) {
                    int[] e = new int[]{pos[0] + aTarget[0], pos[1] + aTarget[1]};
                    if (e[0] < 0 || e[0] > 9 || e[1] < 0 || e[1] > 8) continue;
                    if (SearchModel.board.MyPieces[e[0]][e[1]] == null) moves.add(e);
                    else if (SearchModel.board.MyPieces[e[0]][e[1]].name.charAt(0) != MainFrame.DoPlayer) moves.add(e);
                }
            }
        }
        if (player == '黑') {
            if (pos[0] < 5) {
                int[] e = new int[]{pos[0] + 1, pos[1]};
                if (SearchModel.board.MyPieces[e[0]][e[1]] == null) moves.add(e);
                else if (SearchModel.board.MyPieces[e[0]][e[1]].name.charAt(0) != MainFrame.DoPlayer) moves.add(e);
            } else {
                for (int[] aTarget : targetD) {
                    int[] e = new int[]{pos[0] + aTarget[0], pos[1] + aTarget[1]};
                    if (e[0] < 0 || e[0] > 9 || e[1] < 0 || e[1] > 8) continue;
                    if (SearchModel.board.MyPieces[e[0]][e[1]] == null) moves.add(e);
                    else if (SearchModel.board.MyPieces[e[0]][e[1]].name.charAt(0) != MainFrame.DoPlayer) moves.add(e);
                }
            }
        }

        return moves;
    }
}
