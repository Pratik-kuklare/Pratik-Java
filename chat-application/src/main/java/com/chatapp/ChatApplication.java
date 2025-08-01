package com.chatapp;

import com.chatapp.client.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChatApplication extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(loader.load(), 450, 400);
        scene.getStylesheets().add(getClass().getResource("/css/style.css").toExternalForm());
        
        primaryStage.setTitle("Chat Application - Login");
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(450);
        primaryStage.setMinHeight(400);
        primaryStage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}