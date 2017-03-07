package com.company;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

public class View extends Application {

    Stage window;
    Scene sceneLogin, sceneMain;

    public View () {

    }

    public static void run (String[] args) {
        launch(args);
    }

    public void start (Stage mainStage) {

        window = mainStage;

        // Label and button for sceneLogin
        Label labelLogin = new Label("Please enter your login information below");
        Button buttonLogin = new Button("Login");
        buttonLogin.setOnAction(e -> window.setScene(sceneMain));

        // Set layout with label and button to create scene
        VBox layoutLogin = new VBox(20);
        layoutLogin.getChildren().addAll(labelLogin, buttonLogin);
        sceneLogin = new Scene(layoutLogin, 600, 300);


        // Label and button for sceneLogin
        Label labelMain = new Label("Please select an action");
        Button buttonLogout = new Button("Logout");
        buttonLogout.setOnAction(e -> logout());

        // Set layout with label and button to create scene
        StackPane layoutMain = new StackPane();
        layoutMain.getChildren().add(buttonLogout);
        sceneMain = new Scene(layoutMain, 600, 300);

        // Display "sceneLogin" when starting the application
        window.setScene(sceneLogin);
        window.setTitle("Bank Application");
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
        if (answer) { window.setScene(sceneLogin); }
    }
}
