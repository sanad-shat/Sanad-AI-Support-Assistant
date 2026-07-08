package service;

public class AIService {

    public String getResponse(String userMessage) {
        String message = userMessage.toLowerCase();

        if (message.contains("hello") || message.contains("hi")) {
            return "Hello! How can I help you today?";
        } else if (message.contains("password")) {
            return "You can reset your password from the login page.";
        } else if (message.contains("problem") || message.contains("issue")) {
            return "Please describe your issue in more detail.";
        } else if (message.contains("thank")) {
            return "You are welcome!";
        } else {
            return "I received your message. A support assistant will help you shortly.";
        }
    }
}