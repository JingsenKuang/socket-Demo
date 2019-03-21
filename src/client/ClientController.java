package client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.Socket;

public class ClientController {
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
    public void btn_SendAction() throws IOException {
        String ipAddress = txtf_IpAddress.getText();
        String port = txtf_Port.getText();
        try (Socket socket = new Socket(ipAddress,Integer.parseInt(port))){
            socket.setSoTimeout(15000);
            OutputStream out = socket.getOutputStream();
            Writer writer = new OutputStreamWriter(out,"UTF-8");
            writer = new BufferedWriter(writer);
            writer.write(txta_SendMessage.getText());
            writer.flush();
            System.out.println(txta_SendMessage.getText());
        }
    }

}
