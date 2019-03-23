package client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class ClientController implements Initializable {
    @FXML
    private TextField txtf_IpAddress;
    @FXML
    private TextField txtf_Port;
    @FXML
    private TextArea txta_ReceivedMessage;
    @FXML
    private TextArea txta_SendMessage;
    @FXML
    private Button btn_Send;
    @FXML
    private Label label_Status;

    private Socket socket;
    private String ipAddress;
    private String port;
    @Override
    public void initialize(URL location, ResourceBundle resources){
        ipAddress = txtf_IpAddress.getText();
        port = txtf_Port.getText();
        connectThread ct = new connectThread(ipAddress,Integer.parseInt(port));
        ct.start();
    }
    private class connectThread extends Thread{
        String ipAddress;
        int port;
        Socket c;
        connectThread(String ipAddress,int port){
            this.ipAddress = ipAddress;
            this.port = port;
        }
        @Override
        public void run(){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    label_Status.setText("Connecting...");
                }
            });
            while (c==null)
            {
                try {
                    c = new Socket(ipAddress,port);
                } catch (IOException e) {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    label_Status.setText("Ready");
                }
            });
            socket = c;
            receivedThread rt = new receivedThread(socket);
            rt.setDaemon(true);
            rt.start();
        }
    }

    public void btn_SendAction() throws IOException {
        try{
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    label_Status.setText("Sending...");
                }
            });
            OutputStream out = socket.getOutputStream();
            Writer writer = new OutputStreamWriter(out,"UTF-8");
            writer = new BufferedWriter(writer);
            writer.write(txta_SendMessage.getText()+"\n");
            writer.flush();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    label_Status.setText("Ready");
                }
            });
            System.out.println(txta_SendMessage.getText());
        }catch (IOException e){
           // connectServer();
            System.out.println("未连接，发送失败");
        }
        finally {

        }
    }
    private class receivedThread extends Thread {
        private Socket connection;
        receivedThread(Socket connection) {
            this.connection = connection;
        }
        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String s;
                while (true) {
                    s = in.readLine();
                    System.out.println("接收的信息：" + s);
                    txta_ReceivedMessage.appendText("SERVER: "+s+"\n");
                }
            } catch (IOException e) {
                //  e.printStackTrace();
                System.out.println("断开连接");
                connectThread ct = new connectThread(ipAddress,Integer.parseInt(port));
                ct.start();
            }
        }
    }

}
