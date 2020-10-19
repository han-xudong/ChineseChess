import java.awt.*;
import java.util.ArrayList;

public class SearchModel {
    private static int DEPTH = 2;
    public static ChessBoarder board = new ChessBoarder();

    public AlphaBetaNode search() {
        for(int i = 0 ; i < board.MyPieces.length ; i ++){
            for(int j = 0 ; j < board.MyPieces[0].length ; j ++){
                board.MyPieces[i][j] = MainFrame.MyBoarder.MyPieces[i][j];
            }
        }
        int isEmpty = 0;
        for(int i = 0 ; i < board.MyPieces.length ; i ++){
            for(int j = 0 ; j < board.MyPieces[0].length ; j ++){
                if(board.MyPieces[i][j] == null){
                    isEmpty++;
                }
            }
        }
        if (isEmpty > 4)
            DEPTH = 3;
        if (isEmpty > 16)
            DEPTH = 4;
        if (isEmpty > 26)
            DEPTH = 5;
        if (isEmpty > 28)
            DEPTH = 6;
        long startTime = System.currentTimeMillis();
        AlphaBetaNode best = null;
        ArrayList<AlphaBetaNode> moves = generateMovesForAll(true);
        for (AlphaBetaNode n : moves) {
            if(n.to[0] < 0 || n.to[0] > 9 || n.to[1] < 0 || n.to[1] > 8) continue;
            ChessPieces eaten = board.MyPieces[n.to[0]][n.to[1]];
            if(eaten != null && eaten.name.charAt(0) == '黑')continue;
            board.PieceMove(new Point(n.from[0], n.from[1]), new Point(n.to[0], n.to[1]));
            n.value = alphaBeta(DEPTH, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
            /* Select a best move during searching to save time*/
            if (best == null || n.value >= best.value)
                best = n;
            board.MyPieces[n.from[0]][n.from[1]] = n.piece;
            if (eaten != null) {
                board.MyPieces[n.to[0]][n.to[1]] = eaten;
            }
        }
        long finishTime = System.currentTimeMillis();
        System.out.println(finishTime - startTime);
        return best;
    }


    private int alphaBeta(int depth, int alpha, int beta, boolean isMax) {
        if (depth == 0 || board.Winner() != '无')
            return new EvalModel().eval(board, '黑');
        ArrayList<AlphaBetaNode> moves = generateMovesForAll(isMax);
        synchronized (this) {
            for (final AlphaBetaNode n : moves) {
                if(n.from[0] < 0 || n.from[0] > 8 || n.from[1] < 0 || n.from[1] > 9 || n.to[0] < 0 || n.to[0] > 8 || n.to[1] < 0 || n.to[1] > 9)continue;
                ChessPieces eaten = board.MyPieces[n.to[0]][n.to[1]];
                board.PieceMove(new Point(n.from[0], n.from[1]), new Point(n.to[0], n.to[1]));
                // Judge if maximizing player
                final int finalBeta = beta;
                final int finalAlpha = alpha;
                final int finalDepth = depth;
                final int[] temp = new int[1];
                // Only adopt multi threading strategy in depth 2. To avoid conjunction
                if (depth == 2) {
                    if (isMax) {
                        new Thread(() -> temp[0] = Math.max(finalAlpha, alphaBeta(finalDepth - 1, finalAlpha, finalBeta, false))).run();
                        alpha = temp[0];
                    } else {
                        new Thread(() -> temp[0] = Math.min(finalBeta, alphaBeta(finalDepth - 1, finalAlpha, finalBeta, true))).run();
                        beta = temp[0];
                    }
                }
                else {
                    if (isMax) alpha = Math.max(alpha, alphaBeta(depth - 1, alpha, beta, false));
                    else beta = Math.min(beta, alphaBeta(depth - 1, alpha, beta, true));
                }
                board.MyPieces[n.from[0]][n.from[1]] = n.piece;
                if (eaten != null) {
                    board.MyPieces[n.to[0]][n.to[1]] = eaten;
                }
                if (beta <= alpha) break;
            }
        }
        return isMax ? alpha : beta;
    }

    private ArrayList<AlphaBetaNode> generateMovesForAll(boolean isMax) {
        ArrayList<AlphaBetaNode> moves = new ArrayList<>();
        for (int i = 0 ; i < board.MyPieces.length ; i ++) {
            for(int j = 0 ; j < board.MyPieces[0].length ; j ++){
                if(board.MyPieces[i][j] == null) continue;
                if (board.MyPieces[i][j].name.charAt(0) == '红') continue;
                if (!isMax && board.MyPieces[i][j].name.charAt(0) == '黑') continue;
                for (int[] nxt : Rules.getNextMove(board.MyPieces[i][j], new int[]{i, j}))
                    moves.add(new AlphaBetaNode(board.MyPieces[i][j], new int[]{i, j}, nxt));
            }

        }
        return moves;
    }

}