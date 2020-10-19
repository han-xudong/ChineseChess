public class AlphaBetaNode {
    public ChessPieces piece;
    public int[] from;
    public int[] to;
    public int value;

    public AlphaBetaNode(ChessPieces piece, int[] from, int[] to) {
        this.piece = piece;
        this.from = from;
        this.to = to;
    }
}
