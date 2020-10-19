import javax.swing.*;
import java.net.Socket;

public class Connect {
    public static final String IP_ADDR = "192.168.0.38";// 服务器地址
    public static final int PORT = 8866;// 服务器端口号

    private static Socket socket;

    public static SendMes sendMes;
    public static boolean connect(){
        try {
            socket = new Socket(IP_ADDR, PORT);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, new JLabel("<html><h1><font color='red'>"+e.getMessage()+"</font></h1></html>"), "错误", JOptionPane.ERROR_MESSAGE);
        }
        new ReadMes(socket).start();

        sendMes=new SendMes(socket);
        sendMes.setMsg("连接成功");
        sendMes.start();
        return true;
    }

    public void sendMessage(String str){
        sendMes.setMsg(str);
        sendMes.start();
    }
}