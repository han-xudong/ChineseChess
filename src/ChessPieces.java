import java.awt.*;

public class ChessPieces {
    public String name;
     // 0：G 黑将 blk-jiang
     // 1：C 黑车 blk-ju
     // 2：H 黑马 blk-ma
     // 3：N 黑炮 blk-pao
     // 4：A 黑士 blk-shi
     // 5：E 黑象 blk-xiang
     // 6：S 黑卒 blk-zu
     // 7：s 红兵 red-bing
     // 8：c 红车 red-ju
     // 9：h 红马 red-ma
     // 10：n 红炮 red-pao
     // 11：a 红士 red-shi
     // 12：g 红帅 red-shuai
     // 13：e 红相 red-xiang
    public int id;
    public Image Icon;
    private int pieceValue;

    public ChessPieces (int Id) {
        this.id = Id;
        String FileName = null;
        if (this.id == 0) {
            this.name = "黑将";
            FileName = "blk-jiang";
            pieceValue = 6000;
        } else if (this.id == 1) {
            this.name = "黑车";
            FileName = "blk-ju";
            pieceValue = 600;
        } else if (this.id == 2) {
            this.name = "黑马";
            FileName = "blk-ma";
            pieceValue = 280;
        } else if (this.id == 3) {
            this.name = "黑炮";
            FileName = "blk-pao";
            pieceValue = 290;
        } else if (this.id == 4) {
            this.name = "黑士";
            FileName = "blk-shi";
            pieceValue = 120;
        } else if (this.id == 5) {
            this.name = "黑象";
            FileName = "blk-xiang";
            pieceValue = 130;
        } else if (this.id == 6) {
            this.name = "黑卒";
            FileName = "blk-zu";
            pieceValue = 60;
        } else if (this.id == 7) {
            this.name = "红兵";
            FileName = "red-bing";
            pieceValue = 60;
        } else if (this.id == 8) {
            this.name = "红车";
            FileName = "red-ju";
            pieceValue = 600;
        } else if (this.id == 9) {
            this.name = "红马";
            FileName = "red-ma";
            pieceValue = 280;
        } else if (this.id == 10) {
            this.name = "红炮";
            FileName = "red-pao";
            pieceValue = 290;
        } else if (this.id == 11) {
            this.name = "红士";
            FileName = "red-shi";
            pieceValue = 120;
        } else if (this.id == 12) {
            this.name = "红帅";
            FileName = "red-shuai";
            pieceValue = 6000;
        } else if (this.id == 13) {
            this.name = "红相";
            FileName = "red-xiang";
            pieceValue = 130;
        }
        //Set imageIcon
        this.Icon = Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource("/imageLibrary/" + FileName + ".png"));
    }

    public int getPieceValue(){
        return pieceValue;
    }
}
