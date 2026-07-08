package client;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) {
        SplashScreen splashScreen = new SplashScreen();
        splashScreen.show(stage);
    }

    public void showWelcome(Stage stage) {
        ImageView logo = new ImageView(
                new Image(getClass().getResourceAsStream("/images/sanad_logo.png"))
        );
        logo.setFitWidth(200);
        logo.setPreserveRatio(true);

        Label title = new Label("Welcome to Sanad AI");
        title.getStyleClass().add("title");

        Label subtitle = new Label(
                "Your intelligent AI customer support platform.\n" +
                "Solve technical issues, create tickets, and track requests in real time."
        );
        subtitle.getStyleClass().add("subtitle");
        subtitle.setAlignment(Pos.CENTER);
        subtitle.setWrapText(true);
        subtitle.setMaxWidth(620);

        Label features = new Label("⚡ Fast Response    🔒 Secure    🤖 AI Powered    24/7 Support");
        features.getStyleClass().add("features");

        Button startButton = new Button("Start Chat  →");
        startButton.getStyleClass().add("primary-button");
        startButton.setPrefWidth(320);
        startButton.setPrefHeight(58);

        startButton.setOnAction(e -> {
            ChatScreen chatScreen = new ChatScreen();
            chatScreen.show(stage);
        });

        Label version = new Label("Version 1.0");
        version.getStyleClass().add("version-text");

        VBox root = new VBox(22, logo, title, subtitle, features, startButton, version);
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("welcome-root");

        Scene scene = new Scene(root, 820, 560);

        if (getClass().getResource("/css/style.css") != null) {
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        }

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/sanad_logo.png")));
        stage.setTitle("Sanad AI Support Assistant");
        stage.setScene(scene);
        stage.show();

        FadeTransition fade = new FadeTransition(Duration.millis(800), root);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}