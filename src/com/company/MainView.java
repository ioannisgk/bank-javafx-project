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

    boolean session = false;
    boolean process = false;
    Stage window;
    Scene sceneLogin, sceneMain, sceneEnterInfo;

    public MainView () {

    }

    public void setSession (boolean session) {
        this.session = session;
    }

    public boolean getSession () {
        return session;
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
        Label label1 = new Label("Welcome to our Bank Application\nClick one of the available actions:");
        Button buttonNewAccount = new Button("Open a New Account");
        buttonNewAccount.setOnAction(e -> {
            process = false;
            window.setScene(sceneEnterInfo);
            window.setResizable(!window.isResizable());
            window.setResizable(window.isResizable());
        });
        Button buttonProcessAccount = new Button("Process an Account");
        buttonProcessAccount.setOnAction(e -> {
            process = true;
            window.setScene(sceneEnterInfo);
            window.setResizable(!window.isResizable());
            window.setResizable(window.isResizable());
        });
        Button buttonSearchAccounts = new Button("Search Current Accounts");
        buttonSearchAccounts.setOnAction(e -> {
            try {
                searchAccounts();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
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

        ///////////////////////////////////////
        //////// CREATE SCENEENTERINFO ////////
        ///////////////////////////////////////

        // GridPane layout with 10px padding
        GridPane gridEnterInfo = new GridPane();
        gridEnterInfo.setPadding(new Insets(10, 20, 10, 30));
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

        // Button for returning back to main
        Button buttonCancel = new Button("Cancel");
        GridPane.setConstraints(buttonCancel, 0, 4);
        buttonCancel.setOnAction(e -> window.setScene(sceneMain));
        buttonCancel.setMinWidth(70.0);

        // Button for next screen
        // A new view object will be created and we will access next scene
        Button buttonCheck = new Button("Next");
        GridPane.setConstraints(buttonCheck, 1, 4);
        buttonCheck.setOnAction(e -> {
            try {
                if (!process) {
                    openNewAccount();
                } else {
                    processAccount();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        buttonCheck.setMinWidth(70.0);

        // Add elements to layout and create "sceneLogin"
        gridEnterInfo.getChildren().addAll(labelFirstname, firstname, labelSurname, surname, labelDate, dob, buttonCancel, buttonCheck);
        sceneEnterInfo = new Scene(gridEnterInfo, 320, 180);

        ////////////////////////////////////
        //////// DISPLAY SCENELOGIN ////////
        ////////////////////////////////////

        // Display "sceneLogin" when starting the application
        //
        if (!session) {
            window.setScene(sceneLogin);
        } else {
            window.setScene(sceneMain);
        }

        // Prevent default close action with consume()
        window.setOnCloseRequest(e -> {
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

    private void openNewAccount() throws Exception {
        boolean customerFound = true;
        boolean answer;
        if (customerFound) {
            NewAccountView newAccountview = new NewAccountView();
            newAccountview.start(window);
        } else {
            answer = ConfirmBox.display("Confirmation", "Customer was not found!!!\nCreate new customer now?");
            if (answer) {
                NewCustomerView newCustomerview = new NewCustomerView();
                newCustomerview.start(window);
                window.setResizable(!window.isResizable());
                window.setResizable(window.isResizable());
            } else {
                window.setScene(sceneMain);
                window.setResizable(!window.isResizable());
                window.setResizable(window.isResizable());
            }
        }
    }

    private void processAccount() throws Exception {
        boolean customerFound = true;
        boolean answer;
        if (customerFound) {
            ProcessAccountView processAccountView = new ProcessAccountView();
            processAccountView.start(window);
        } else {
            answer = ConfirmBox.display("Confirmation", "Customer was not found!!!\nCreate new customer now?");
            if (answer) {
                NewCustomerView newCustomerview = new NewCustomerView();
                newCustomerview.start(window);
                window.setResizable(!window.isResizable());
                window.setResizable(window.isResizable());
            } else {
                window.setScene(sceneMain);
                window.setResizable(!window.isResizable());
                window.setResizable(window.isResizable());
            }
        }
    }

    private void searchAccounts() throws Exception {
        SearchAccountView earchAccountView = new SearchAccountView();
        earchAccountView.start(window);
        window.setResizable(!window.isResizable());
        window.setResizable(window.isResizable());
    }
}