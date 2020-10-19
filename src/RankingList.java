import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class RankingList {
    public static String[][] ranking;

    public static void WriteList(String str){
        String[][] temp = new String[ranking.length][ranking[0].length];
        boolean existedPlayer = false;
        for (int i = 0 ; i < ranking.length ; i ++) {
            if (ranking[i][1].equals(str)) {
                existedPlayer = true;
                ranking[i][2] = String.format("%d", Integer.parseInt(ranking[i][2]) + 1);
            }
        }
        for (int i = 0 ; i < temp.length ; i ++) {
            for (int j = 0 ; j < temp[0].length ; j ++) {
                temp[i][j] = ranking[i][j];
            }
        }
        if (!existedPlayer) {
            ranking = new String[temp.length + 1][temp[0].length];
            for (int i = 0 ; i < temp.length ; i ++) {
                for (int j = 0 ; j < temp[0].length ; j ++) {
                    ranking[i][j] = temp[i][j];
                }
            }
            ranking[ranking.length - 1][0] = "第" + ranking.length + "名";
            ranking[ranking.length - 1][1] = str;
            ranking[ranking.length - 1][2] = "1";
        }
        String tempGrade;
        String tempName;
        for (int i = 0 ; i < ranking.length ; i ++) {
            for (int j = i ; j < ranking.length - 1 ; j ++) {
                if (Integer.parseInt(ranking[j][2]) < Integer.parseInt(ranking[j + 1][2])) {
                    tempName = ranking[j + 1][1];
                    tempGrade = ranking[j + 1][2];
                    ranking[j + 1][1] = ranking[j][1];
                    ranking[j + 1][2] = ranking[j][2];
                    ranking[j][1] = tempName;
                    ranking[j][2] = tempGrade;
                }
            }
        }
        File file = new File("RankingList.txt");
        if (file.delete()) {
        }
        try {
            FileOutputStream fos = new FileOutputStream(new File("RankingList.txt"));
            OutputStreamWriter osr = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            BufferedWriter bw = new BufferedWriter(osr);

            for(int i = 0 ; i < ranking.length ; i ++){
                bw.write(ranking[i][0] + " " + ranking[i][1] + " " + ranking[i][2]);
                bw.write("\n");
            }
            bw.flush();
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void ReadList(){
       try {
           FileInputStream fis = new FileInputStream(new File("RankingList.txt"));
           InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
           BufferedReader br = new BufferedReader(isr);

           ArrayList<String> list = new ArrayList<>();
           while(true){
               String str = br.readLine();
               if (null == str) {
                   break;
               }
               list.add(str);
           }
           String[] temp = list.toArray(new String[0]);
           ranking = new String[temp.length][3];
           for (int i = 0 ; i < ranking.length ; i ++) {
               String[] line = temp[i].split(" ");
               ranking[i][0] = line[0];
               ranking[i][1] = line[1];
               ranking[i][2] = line[2];
           }
       }catch (Exception e) {
           e.printStackTrace();
       }
    }
    public static String[][] getRankingList(){
        ReadList();
        String[][] rankingList = new String[ranking.length + 1][3];
        rankingList[0][0] = "排名";
        rankingList[0][1] = "玩家";
        rankingList[0][2] = "积分";
        for(int i = 0 ; i < ranking.length ; i ++){
            for(int j = 0 ; j < ranking[0].length ; j ++){
                rankingList[i + 1][j] = ranking[i][j];
            }
        }
        return rankingList;
    }
}
