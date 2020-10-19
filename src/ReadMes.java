import javax.swing.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ReadMes extends Thread {
    private Socket socket;

    public ReadMes(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        DataInputStream objInput = null;
        try {
            objInput = new DataInputStream(socket.getInputStream());
            while (true) {

            }
        } catch (Exception e) {
            e.printStackTrace();
            if(e.getMessage()!=null)
                JOptionPane.showMessageDialog(null, new JLabel("<html><h1><font color='red'>"+e.getMessage()+"</font></h1></html>"), "错误", JOptionPane.ERROR_MESSAGE);

        } finally {
            try {
                if(objInput!=null)
                    objInput.close();
            } catch (IOException e) {
            }
        }
    }

}