package com.company;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileInputStream;

public class MainView extends Application {

    Stage window;
    Scene sceneLogin, sceneMain;

    public MainView () {

    }

    public static void run (String[] args) {
        launch(args);
    }
    @Override
    public void start (Stage mainStage) throws Exception {

        window = mainStage;
        window.setTitle("Bank Application");

        ///////////////////////////////////
        //////// CREATE SCENELOGIN ////////
        ///////////////////////////////////

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

        //////////////////////////////////
        //////// CREATE SCENEMAIN ////////
        //////////////////////////////////

        // http://tutorials.jenkov.com/javafx/imageview.html
        FileInputStream imageFile = new FileInputStream("bank_logo.png");
        Image image = new Image(imageFile);
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(500);
        imageView.setFitHeight(144);

        // Create different elements
        Label label1 = new Label("Welcome to our Bank Application.\nClick one of the available actions:");
        Button buttonNewAccount = new Button("Open a New Account");
        Button buttonProcessAccount = new Button("Process an Account");
        Button buttonSearchAccounts = new Button("Search Current Accounts");
        Button buttonLogout = new Button("Logout");
        buttonLogout.setOnAction(e -> logout());
        Label label2 = new Label("Copyright 2017, Ioannis Gkourtzounis");

        // Set a Column of Buttons to the Same Width
        // http://docs.oracle.com/javafx/2/layout/size_align.htm
        buttonNewAccount.setMaxWidth(Double.MAX_VALUE);
        buttonProcessAccount.setMaxWidth(Double.MAX_VALUE);
        buttonSearchAccounts.setMaxWidth(Double.MAX_VALUE);
        buttonLogout.setMaxWidth(Double.MAX_VALUE);

        // Set layout: vbox1 for Top, hbox1 for Center, hbox2 for Bottom
        VBox vbox1 = new VBox();
        vbox1.setPadding(new Insets(50, 50, 20, 50));
        VBox vbox2 = new VBox(10);
        vbox2.setPadding(new Insets(0, 80, 10, 0));
        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(0, 0, 10, 50));
        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(0, 50, 10, 50));

        // Add elements to layouts
        vbox1.getChildren().addAll(imageView);
        vbox2.getChildren().addAll(buttonNewAccount, buttonProcessAccount, buttonSearchAccounts, buttonLogout);
        hbox1.getChildren().addAll(label1);
        hbox2.getChildren().addAll(label2);

        // Add layouts to borderpane
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(vbox1);
        borderPane.setRight(vbox2);
        borderPane.setCenter(hbox1);
        borderPane.setBottom(hbox2);

        // Create "sceneMain"
        sceneMain = new Scene(borderPane, 600, 400);

        ////////////////////////////////////
        //////// DISPLAY SCENELOGIN ////////
        ////////////////////////////////////

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