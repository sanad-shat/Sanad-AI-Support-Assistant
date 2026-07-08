package client;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        Label title = new Label("Sanad AI Support Assistant");
        title.setStyle("-fx-font-size: 26px; -fx-font-weight: bold;");

        Label subtitle = new Label("Real-Time Customer Support System");
        subtitle.setStyle("-fx-font-size: 15px;");

        Button startButton = new Button("Start Chat");
        startButton.setStyle("-fx-font-size: 14px; -fx-padding: 10 25 10 25;");

        VBox root = new VBox(20, title, subtitle, startButton);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f4f6f8;");

        Scene scene = new Scene(root, 700, 450);

        stage.setTitle("Sanad AI Support Assistant");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}