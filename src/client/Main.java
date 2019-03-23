package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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

    }


    public static void main(String[] args) {
        launch(args);
    }
}
