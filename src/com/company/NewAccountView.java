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

public class NewAccountView extends Application {

    Stage window;
    Scene sceneEnterInfo, sceneMain;

    public NewAccountView () {

    }

    public static void run (String[] args) {
        launch(args);
    }
    @Override
    public void start (Stage mainStage) throws Exception {

        window = mainStage;
        window.setTitle("Bank Application");

        ///////////////////////////////////
        //////// CREATE SCENEENTERINFO ////////
        ///////////////////////////////////

        // GridPane layout with 10px padding
        GridPane gridEnterInfo = new GridPane();
        gridEnterInfo.setPadding(new Insets(10, 10, 10, 10));
        gridEnterInfo.setVgap(8);
        gridEnterInfo.setHgap(10);

        // Label and text field for firstname
        Label labelFirstname = new Label("Firstname:");
        GridPane.setConstraints(labelFirstname, 0, 0);
        TextField firstname = new TextField();
        firstname.setPromptText("firstname");
        GridPane.setConstraints(firstname, 1, 0);

        // Label and text field for surname
        Label labelSurname = new Label("Surname:");
        GridPane.setConstraints(labelSurname, 0, 1);
        TextField surname = new TextField();
        surname.setPromptText("surname");
        GridPane.setConstraints(surname, 1, 1);

        // Label and text field for date of birth
        Label labelDate = new Label("Date of birth:");
        GridPane.setConstraints(labelDate, 0, 2);
        TextField dob = new TextField();
        dob.setPromptText("DDMMYYYY");
        GridPane.setConstraints(dob, 1, 2);

        // Button for login
        Button buttonCheck = new Button("Check if customer is registered");
        GridPane.setConstraints(buttonCheck, 1, 3);
        buttonCheck.setOnAction(e -> window.setScene(sceneMain));

        // Add elements to layout and create "sceneLogin"
        gridEnterInfo.getChildren().addAll(labelFirstname, firstname, labelSurname, surname, labelDate, dob, buttonCheck);
        sceneEnterInfo = new Scene(gridEnterInfo, 300, 200);

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
        window.setScene(sceneEnterInfo);
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
            //window.setScene(sceneLogin);
            // Refresh windows size
            window.setResizable(!window.isResizable());
            window.setResizable(window.isResizable());
        }
    }
}