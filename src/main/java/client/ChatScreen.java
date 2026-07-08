package client;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.AIService;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChatScreen {

    private VBox messagesBox;
    private TextField messageField;
    private ScrollPane scrollPane;
    private AIService aiService;

    public ChatScreen() {
        aiService = new AIService();
    }

    public void show(Stage stage) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("chat-root");

        root.setLeft(createSidebar());
        root.setCenter(createMainArea());

        Scene scene = new Scene(root, 1120, 700);

        if (getClass().getResource("/css/style.css") != null) {
            scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        }

        stage.setTitle("Sanad AI Support Assistant");
        stage.setScene(scene);
        stage.show();

        addWelcomeCard();
    }

    private VBox createSidebar() {
        Image logoSource = new Image(getClass().getResourceAsStream("/images/sanad_logo.png"));

        ImageView logoImage = new ImageView(logoSource);
        logoImage.setFitWidth(58);
        logoImage.setFitHeight(58);
        logoImage.setPreserveRatio(true);

        StackPane logoCircle = new StackPane(logoImage);
        logoCircle.getStyleClass().add("sidebar-logo-circle");

        Label logo = new Label("Sanad AI");
        logo.getStyleClass().add("sidebar-logo");

        Label subtitle = new Label("AI Support Platform");
        subtitle.getStyleClass().add("sidebar-subtitle");

        VBox brandBox = new VBox(8, logoCircle, logo, subtitle);
        brandBox.setAlignment(Pos.CENTER);
        brandBox.getStyleClass().add("brand-box");

        Button newChat = createMenuButton("💬  New Chat", true);
        Button history = createMenuButton("🕘  History", false);
        Button tickets = createMenuButton("🎫  Tickets", false);
        Button requests = createMenuButton("📁  My Requests", false);
        Button dashboard = createMenuButton("📊  Dashboard", false);
        Button settings = createMenuButton("⚙  Settings", false);
        Button help = createMenuButton("❓  Help Center", false);

        Label status = new Label("● Online");
        status.getStyleClass().add("online-status");

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label user = new Label("SA\nSanad User");
        user.getStyleClass().add("user-box");

        VBox sidebar = new VBox(
                15,
                brandBox,
                newChat,
                history,
                tickets,
                requests,
                dashboard,
                settings,
                help,
                spacer,
                status,
                user
        );

        sidebar.setPadding(new Insets(25, 18, 25, 18));
        sidebar.setPrefWidth(245);
        sidebar.getStyleClass().add("sidebar");

        return sidebar;
    }

    private Button createMenuButton(String text, boolean active) {
        Button button = new Button(text);
        button.getStyleClass().add("sidebar-button");

        if (active) {
            button.getStyleClass().add("sidebar-button-active");
        }

        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    private VBox createMainArea() {
        ImageView headerLogo = new ImageView(
                new Image(getClass().getResourceAsStream("/images/sanad_logo.png"))
        );
        headerLogo.setFitWidth(42);
        headerLogo.setFitHeight(42);
        headerLogo.setPreserveRatio(true);

        StackPane headerLogoCircle = new StackPane(headerLogo);
        headerLogoCircle.getStyleClass().add("header-logo-circle");

        Label title = new Label("Sanad AI");
        title.getStyleClass().add("chat-title");

        Label subtitle = new Label("AI Customer Support • Online");
        subtitle.getStyleClass().add("chat-subtitle");

        VBox titleBox = new VBox(4, title, subtitle);
        HBox titleArea = new HBox(12, headerLogoCircle, titleBox);
        titleArea.setAlignment(Pos.CENTER_LEFT);

        Label time = new Label(currentTime());
        time.getStyleClass().add("header-time");

        Label notification = createHeaderIcon("🔔");
        Label settings = createHeaderIcon("⚙");
        Label profile = createHeaderIcon("👤");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox header = new HBox(14, titleArea, spacer, time, notification, settings, profile);
        header.setPadding(new Insets(18, 28, 18, 28));
        header.setAlignment(Pos.CENTER_LEFT);
        header.getStyleClass().add("chat-header");

        messagesBox = new VBox(14);
        messagesBox.setPadding(new Insets(22, 30, 22, 30));

        scrollPane = new ScrollPane(messagesBox);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("chat-scroll");

        Button attachButton = new Button("＋");
        attachButton.getStyleClass().add("small-input-button");

        messageField = new TextField();
        messageField.setPromptText("Ask Sanad AI anything...");
        messageField.getStyleClass().add("message-field");
        messageField.setOnAction(e -> sendMessage());

        Button sendButton = new Button("➤");
        sendButton.getStyleClass().add("send-button");
        sendButton.setOnAction(e -> sendMessage());

        HBox inputBox = new HBox(10, attachButton, messageField, sendButton);
        inputBox.setPadding(new Insets(16, 28, 22, 28));
        inputBox.setAlignment(Pos.CENTER);
        HBox.setHgrow(messageField, Priority.ALWAYS);
        inputBox.getStyleClass().add("input-wrapper");

        VBox mainArea = new VBox(header, scrollPane, inputBox);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        mainArea.getStyleClass().add("main-area");

        return mainArea;
    }

    private Label createHeaderIcon(String text) {
        Label icon = new Label(text);
        icon.getStyleClass().add("header-icon");
        return icon;
    }

    private void addWelcomeCard() {
        Label title = new Label("👋 Welcome to Sanad AI");
        title.getStyleClass().add("welcome-title");

        Label text = new Label("Your intelligent customer support assistant. Choose a quick action or type your question below.");
        text.getStyleClass().add("welcome-text");
        text.setWrapText(true);

        GridPane actionsGrid = new GridPane();
        actionsGrid.setHgap(12);
        actionsGrid.setVgap(12);

        Button technical = createQuickButton("🛠 Technical Issue");
        Button password = createQuickButton("🔐 Reset Password");
        Button ticket = createQuickButton("🎫 Create Ticket");
        Button track = createQuickButton("📦 Track Request");
        Button billing = createQuickButton("💳 Billing Issue");
        Button human = createQuickButton("👨‍💼 Contact Human");

        technical.setOnAction(e -> quickSend("I have a technical issue"));
        password.setOnAction(e -> quickSend("I forgot my password"));
        ticket.setOnAction(e -> quickSend("I want to create a support ticket"));
        track.setOnAction(e -> quickSend("I want to track my request"));
        billing.setOnAction(e -> quickSend("I have a billing issue"));
        human.setOnAction(e -> quickSend("I want to contact a human support agent"));

        actionsGrid.add(technical, 0, 0);
        actionsGrid.add(password, 1, 0);
        actionsGrid.add(ticket, 2, 0);
        actionsGrid.add(track, 0, 1);
        actionsGrid.add(billing, 1, 1);
        actionsGrid.add(human, 2, 1);

        VBox card = new VBox(14, title, text, actionsGrid);
        card.getStyleClass().add("welcome-card");

        messagesBox.getChildren().add(card);
        fadeIn(card);
    }

    private Button createQuickButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("quick-button");
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    private void quickSend(String message) {
        messageField.setText(message);
        sendMessage();
    }

    private void sendMessage() {
        String message = messageField.getText().trim();

        if (message.isEmpty()) {
            return;
        }

        addUserMessage(message);
        messageField.clear();

        HBox typingRow = addTypingMessage();

        PauseTransition pause = new PauseTransition(Duration.seconds(0.9));
        pause.setOnFinished(e -> {
            messagesBox.getChildren().remove(typingRow);
            String response = aiService.getResponse(message);
            addAIMessage(response);
        });
        pause.play();
    }

    private void addUserMessage(String message) {
        addMessage("You", "SA", message, "user-message", Pos.CENTER_RIGHT);
    }

    private void addAIMessage(String message) {
        addMessage("Sanad AI", "AI", message, "ai-message", Pos.CENTER_LEFT);
    }

    private HBox addTypingMessage() {
        Label avatar = new Label("AI");
        avatar.getStyleClass().add("ai-avatar");

        Label dots = new Label("●  ●  ●");
        dots.getStyleClass().add("typing-dots");

        VBox bubble = new VBox(dots);
        bubble.getStyleClass().add("typing-bubble");

        HBox row = new HBox(10, avatar, bubble);
        row.setAlignment(Pos.CENTER_LEFT);

        messagesBox.getChildren().add(row);
        fadeIn(row);
        scrollToBottom();

        return row;
    }

    private void addMessage(String senderName, String avatarText, String message, String styleClass, Pos alignment) {
        Label avatar = new Label(avatarText);
        avatar.getStyleClass().add(alignment == Pos.CENTER_RIGHT ? "user-avatar" : "ai-avatar");

        Label sender = new Label(senderName + " • " + currentTime());
        sender.getStyleClass().add("message-sender");

        Label msg = new Label(message);
        msg.getStyleClass().add(styleClass);
        msg.setWrapText(true);

        VBox bubble = new VBox(5, sender, msg);

        HBox row;

        if (alignment == Pos.CENTER_RIGHT) {
            row = new HBox(10, bubble, avatar);
        } else {
            row = new HBox(10, avatar, bubble);
        }

        row.setAlignment(alignment);
        messagesBox.getChildren().add(row);
        fadeIn(row);
        scrollToBottom();
    }

    private String currentTime() {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    private void fadeIn(javafx.scene.Node node) {
        FadeTransition fade = new FadeTransition(Duration.millis(250), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    private void scrollToBottom() {
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }
}