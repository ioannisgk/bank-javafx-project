package com.company;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;

import static javafx.application.Application.launch;

public class View extends Application {

    Stage window;
    Scene sceneLogin, sceneMain;

    public View () {

    }

    public static void run (String[] args) {
        launch(args);
    }
    @Override
    public void start (Stage mainStage) throws Exception {

        window = mainStage;
        window.setTitle("Bank Application");

        // GridPane layout with 10px padding
        GridPane gridLogin = new GridPane();
        gridLogin.setPadding(new Insets(10, 10, 10, 10));
        gridLogin.setVgap(8);
        gridLogin.setHgap(10);

        // Label and text field for username
        Label labelUsername = new Label("Username:");
        GridPane.setConstraints(labelUsername, 0, 0);
        TextField username = new TextField();
        username.setPromptText("username");
        GridPane.setConstraints(username, 1, 0);

        // Label and text field for password
        Label labelPassword = new Label("Password:");
        GridPane.setConstraints(labelPassword, 0, 1);
        TextField password = new TextField();
        password.setPromptText("password");
        GridPane.setConstraints(password, 1, 1);

        // Button for login
        Button buttonLogin = new Button("Log In");
        GridPane.setConstraints(buttonLogin, 1, 2);
        buttonLogin.setOnAction(e -> window.setScene(sceneMain));

        // Add elements to layout and create "sceneLogin"
        gridLogin.getChildren().addAll(labelUsername, username, labelPassword, password, buttonLogin);
        sceneLogin = new Scene(gridLogin, 300, 200);

        //////////////////////////////////////////////////////

        // http://tutorials.jenkov.com/javafx/imageview.html
        FileInputStream imageFile = new FileInputStream("bank_logo.png");
        Image image = new Image(imageFile);
        ImageView iv = new ImageView(image);
        iv.setFitWidth(500);
        iv.setFitHeight(144);
        HBox hbox = new HBox(iv);



        sceneMain = new Scene(hbox, 600, 300);



        ////

        // Label and button for sceneLogin
        //Label labelMain = new Label("Please select an action");
        //Button buttonLogout = new Button("Logout");
        //buttonLogout.setOnAction(e -> logout());

        // Set layout with label and button to create scene
        //StackPane layoutMain = new StackPane();
        //layoutMain.getChildren().add(buttonLogout);
        //sceneMain = new Scene(layoutMain, 600, 300);

        ///////////////////////////////////////////////////////

        // Display "sceneLogin" when starting the application

        window.setScene(sceneLogin);
        window.setOnCloseRequest(e -> {
            // Prevent default close action with consume()
            e.consume();
            closeApplication();
        });
        window.show();
    }

    private void closeApplication() {
        boolean answer;
        answer = ConfirmBox.display("Confirmation", "Are you sure you want to exit?");
        if (answer) { window.close(); }
    }

    private void logout() {
        boolean answer;
        answer = ConfirmBox.display("Confirmation", "Are you sure you want to logout?");
        if (answer) {
            window.setScene(sceneLogin);
            // Refresh windows size
            window.setResizable(!window.isResizable());
            window.setResizable(window.isResizable());
        }
    }
}