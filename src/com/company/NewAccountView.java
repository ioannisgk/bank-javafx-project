package com.company;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileInputStream;

public class NewAccountView extends Application {

    Stage window;
    Scene sceneNewAccount;

    public NewAccountView () {

    }

    public static void run (String[] args) {
        launch(args);
    }
    //@Override
    public void start (Stage mainStage) throws Exception {

        window = mainStage;
        window.setTitle("Bank Application");

        ////////////////////////////////////////
        //////// CREATE SCENENEWACCOUNT ////////
        ////////////////////////////////////////

        // GridPane layout with 10px padding
        GridPane gridOpenAccount = new GridPane();
        gridOpenAccount.setPadding(new Insets(10, 20, 10, 30));
        gridOpenAccount.setVgap(8);
        gridOpenAccount.setHgap(10);

        Label labelTitle = new Label("CUSTOMER DATA");
        GridPane.setConstraints(labelTitle, 0, 0);

        // Label and text field for firstname
        Label labelFirstname = new Label("Firstname:");
        GridPane.setConstraints(labelFirstname, 0, 1);
        TextField firstname = new TextField();
        firstname.setPromptText("firstname");
        GridPane.setConstraints(firstname, 1, 1);

        // Label and text field for surname
        Label labelSurname = new Label("Surname:");
        GridPane.setConstraints(labelSurname, 0, 2);
        TextField surname = new TextField();
        surname.setPromptText("surname");
        GridPane.setConstraints(surname, 1, 2);

        // Label and text field for date of birth
        Label labelDate = new Label("Birth Date:");
        GridPane.setConstraints(labelDate, 0, 3);
        TextField dob = new TextField();
        dob.setPromptText("DDMMYYYY");
        GridPane.setConstraints(dob, 1, 3);

        // Label and text field for phone
        Label labelPhone = new Label("Phone:");
        GridPane.setConstraints(labelPhone, 0, 4);
        TextField phone = new TextField();
        phone.setPromptText("phone");
        GridPane.setConstraints(phone, 1, 4);

        // Label and text field for email
        Label labelEmail = new Label("Email:");
        GridPane.setConstraints(labelEmail, 0, 5);
        TextField email = new TextField();
        email.setPromptText("email");
        GridPane.setConstraints(email, 1, 5);

        // Add elements to layout and create scene
        gridOpenAccount.getChildren().addAll(labelTitle, labelFirstname, firstname, labelSurname, surname, labelDate, dob, labelPhone, phone, labelEmail, email);

        ////////////////////////////////////////
        //////// CREATE SCENENEWACCOUNT ////////
        ////////////////////////////////////////

        // Create radio buttons to select account type
        Label labelTitle2 = new Label("Select account type:");
        ToggleGroup group = new ToggleGroup();
        RadioButton rbCurrent = new RadioButton("Current");
        rbCurrent.setUserData("Current");
        rbCurrent.setToggleGroup(group);
        rbCurrent.setSelected(true);
        RadioButton rbDeposit = new RadioButton("Deposit");
        rbDeposit.setUserData("Deposit");
        rbDeposit.setToggleGroup(group);
        RadioButton rbSavings = new RadioButton("Savings");
        rbSavings.setUserData("Savings");
        rbSavings.setToggleGroup(group);

        // Create open a new account and back to main button
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnAction(e -> {
            try {
                backToMain();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        Button buttonNewAccount = new Button("Open a New Account");
        Label label2 = new Label("Copyright 2017, Ioannis Gkourtzounis");

        // Set a Column of Buttons to the Same Width
        // http://docs.oracle.com/javafx/2/layout/size_align.htm
        buttonCancel.setMaxWidth(Double.MAX_VALUE);
        buttonNewAccount.setMaxWidth(Double.MAX_VALUE);

        // Set layout: vbox1 for Center, vbox2 for Right, hbox1 for Bottom
        VBox vbox1 = new VBox(10);
        vbox1.setPadding(new Insets(20, 80, 10, 80));
        VBox vbox2 = new VBox(10);
        vbox2.setPadding(new Insets(44, 80, 10, 0));
        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(0, 50, 10, 50));

        // Add elements to layouts
        vbox1.getChildren().addAll(labelTitle2, rbCurrent, rbDeposit, rbSavings);
        vbox2.getChildren().addAll(buttonCancel, buttonNewAccount);
        hbox1.getChildren().addAll(label2);

        // Add layouts to borderpane
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(gridOpenAccount);
        borderPane.setCenter(vbox1);
        borderPane.setRight(vbox2);
        borderPane.setBottom(hbox1);

        // Create "sceneNewAccount"
        sceneNewAccount = new Scene(borderPane, 600, 400);

        /////////////////////////////////////////
        //////// DISPLAY SCENENEWACCOUNT ////////
        /////////////////////////////////////////

        // Display "sceneEnterInfo" when starting the application
        window.setScene(sceneNewAccount);
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

    private void backToMain() throws Exception {
        MainView mainView = new MainView();
        mainView.setSession(true);
        mainView.start(window);
    }
}