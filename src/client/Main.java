package client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL location = getClass().getResource("client.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        //Parent root = FXMLLoader.load(getClass().getResource("client.fxml"));
        primaryStage.setTitle("client");
        primaryStage.setScene(new Scene(root, 531, 366));
        primaryStage.show();
        ClientController controller = fxmlLoader.getController();
        controller.connectServer();


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.print("监听到窗口关闭");
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
