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
    //@Override
    public void start (Stage mainStage) throws Exception {

        window = mainStage;
        window.setTitle("Bank Application222");

        ///////////////////////////////////////
        //////// CREATE SCENEENTERINFO ////////
        ///////////////////////////////////////

        // GridPane layout with 10px padding
        GridPane gridOpenAccount = new GridPane();
        gridOpenAccount.setPadding(new Insets(10, 20, 0, 30));
        gridOpenAccount.setVgap(20);
        gridOpenAccount.setHgap(0);
        //gridOpenAccount.setMinHeight(200);

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
/*
        // Label and text field for phone
        Label labelPhone = new Label("Phone");
        GridPane.setConstraints(labelDate, 0, 4);
        TextField phone = new TextField();
        dob.setPromptText("phone");
        GridPane.setConstraints(dob, 1, 4);

        // Label and text field for email
        Label labelEmail = new Label("Email");
        GridPane.setConstraints(labelDate, 0, 5);
        TextField email = new TextField();
        dob.setPromptText("email");
        GridPane.setConstraints(dob, 1, 5);
*/
        // Add elements to layout and create scene
        gridOpenAccount.getChildren().addAll(labelFirstname, firstname, labelSurname, surname, labelDate, dob);

        //////////////////////////////////
        //////// CREATE SCENEMAIN ////////
        //////////////////////////////////

        // Create different elements
        Label label1 = new Label("Welcome to our Bank Application.\nClick one of the available actions:");
        Button buttonNewAccount = new Button("Open a New Account");
        Button buttonProcessAccount = new Button("Process an Account");
        Button buttonSearchAccounts = new Button("Search Current Accounts");
        Button buttonLogout = new Button("Logout");
        // buttonLogout.setOnAction(e -> logout());
        Label label2 = new Label("Copyright 2017, Ioannis Gkourtzounis");

        // Set a Column of Buttons to the Same Width
        // http://docs.oracle.com/javafx/2/layout/size_align.htm
        buttonNewAccount.setMaxWidth(Double.MAX_VALUE);
        buttonProcessAccount.setMaxWidth(Double.MAX_VALUE);
        buttonSearchAccounts.setMaxWidth(Double.MAX_VALUE);
        buttonLogout.setMaxWidth(Double.MAX_VALUE);

        // Set layout: vbox1 for Top, hbox1 for Center, hbox2 for Bottom
        //VBox vbox1 = new VBox();
        //vbox1.setPadding(new Insets(50, 50, 20, 50));
        VBox vbox2 = new VBox(10);
        vbox2.setPadding(new Insets(0, 80, 10, 0));
        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(0, 0, 10, 50));
        HBox hbox2 = new HBox();
        hbox2.setPadding(new Insets(0, 50, 10, 50));

        // Add elements to layouts
        //vbox1.getChildren().addAll(hboxFirstname);
        vbox2.getChildren().addAll(buttonNewAccount, buttonProcessAccount, buttonSearchAccounts, buttonLogout);
        hbox1.getChildren().addAll(label1);
        hbox2.getChildren().addAll(label2);

        // Add layouts to borderpane
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(gridOpenAccount);
        borderPane.setRight(vbox2);
        borderPane.setCenter(hbox1);
        borderPane.setBottom(hbox2);

        // Create "sceneMain"
        sceneMain = new Scene(borderPane, 600, 400);

        ////////////////////////////////////////
        //////// DISPLAY SCENEENTERINFO ////////
        ////////////////////////////////////////

        // Display "sceneEnterInfo" when starting the application
        window.setScene(sceneMain);
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
}