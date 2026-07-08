package client;

import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SplashScreen {

    public void show(Stage stage) {
        ImageView logo = new ImageView(
                new Image(getClass().getResourceAsStream("/images/sanad_logo.png"))
        );
        logo.setFitWidth(180);
        logo.setPreserveRatio(true);

        Label loadingText = new Label("Initializing Sanad AI...");
        loadingText.setStyle("-fx-font-size: 16px; -fx-text-fill: #35527d; -fx-font-weight: bold;");

        VBox root = new VBox(25, logo, loadingText);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #f8fbff, #eef5ff);");

        Scene scene = new Scene(root, 820, 560);

        stage.setTitle("Sanad AI Support Assistant");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/sanad_logo.png")));
        stage.setScene(scene);
        stage.show();

        ScaleTransition scale = new ScaleTransition(Duration.millis(900), logo);
        scale.setFromX(0.85);
        scale.setFromY(0.85);
        scale.setToX(1.0);
        scale.setToY(1.0);
        scale.play();

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(e -> {
            MainApp mainApp = new MainApp();
            mainApp.showWelcome(stage);
        });
        pause.play();
    }
}