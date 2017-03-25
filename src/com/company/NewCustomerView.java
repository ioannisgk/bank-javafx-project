package com.company;
import java.text.SimpleDateFormat;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import static com.company.Main.saveToFile;

/**
 * NewCustomerView class
 * Task 1: Create GridPane layout for use with sceneNewCustomer (it is the upper half of this scene)
 * Task 2: Create sceneNewCustomer, the window for saving a new Customer
 * Task 3: Display sceneNewCustomer window
 * Task 4: Method createNewCustomer to save the new customer to object file
 **/

public class NewCustomerView extends Application {

    Stage window;
    Scene sceneNewCustomer;
    Customer customer;

    public NewCustomerView (Customer customer) {
        this.customer = customer;
    }

    public static void run (String[] args) {
        launch(args);
    }
    //@Override
    public void start (Stage mainStage) throws Exception {

        window = mainStage;
        window.setTitle("Bank Application");

        ///////////////////////////////////////////
        //////// 1. Create GridPane layout ////////
        ///////////////////////////////////////////

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
        firstname.setText(customer.getFirstname());
        GridPane.setConstraints(firstname, 1, 1);

        // Label and text field for surname
        Label labelSurname = new Label("Surname:");
        GridPane.setConstraints(labelSurname, 0, 2);
        TextField surname = new TextField();
        surname.setPromptText("surname");
        surname.setText(customer.getSurname());
        GridPane.setConstraints(surname, 1, 2);

        // Label and text field for date of birth
        Label labelDate = new Label("Birth Date:");
        GridPane.setConstraints(labelDate, 0, 3);
        TextField dob = new TextField();
        dob.setPromptText("DD-MM-YYYY");
        SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
        dob.setText(fmt.format(customer.getDateOfBirth()));
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

        // Label and text field for email
        Label labelUsername = new Label("Username:");
        GridPane.setConstraints(labelUsername, 0, 6);
        TextField username = new TextField();
        username.setPromptText("username");
        GridPane.setConstraints(username, 1, 6);

        // Label and text field for email
        Label labelPassword = new Label("Password:");
        GridPane.setConstraints(labelPassword, 0, 7);
        TextField password = new TextField();
        password.setPromptText("password");
        GridPane.setConstraints(password, 1, 7);

        // Add elements to layout and create scene
        gridOpenAccount.getChildren().addAll(labelTitle, labelFirstname, firstname, labelSurname,
                surname, labelDate, dob, labelPhone, phone, labelEmail, email, labelUsername, username, labelPassword, password);

        ////////////////////////////////////////////
        //////// 2. Create sceneNewCustomer ////////
        ////////////////////////////////////////////

        // Create radio buttons to select account type
        Label label1 = new Label("A new record will be created\nAll information will be saved");

        // Create save customer and back to main buttons
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnAction(e -> {
            try {
                backToMain();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        Button buttonNewCustomer = new Button("Save Customer");
        buttonNewCustomer.setOnAction(e -> {
            createNewCustomer(firstname.getText(), surname.getText(),email.getText(),
                    "", username.getText(), password.getText(), dob.getText(), phone.getText());
        });
        Label label2 = new Label("Copyright 2017, Ioannis Gkourtzounis");

        // Set a Column of Buttons to the Same Width
        // http://docs.oracle.com/javafx/2/layout/size_align.htm
        buttonCancel.setMaxWidth(Double.MAX_VALUE);
        buttonNewCustomer.setMaxWidth(Double.MAX_VALUE);

        // Set layout: vbox1 for Center, vbox2 for Right, hbox1 for Bottom
        VBox vbox1 = new VBox(10);
        vbox1.setPadding(new Insets(20, 80, 10, 80));
        VBox vbox2 = new VBox(10);
        vbox2.setPadding(new Insets(22, 80, 10, 0));
        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(-20, 50, 10, 50));

        // Add elements to layouts
        vbox1.getChildren().addAll(label1);
        vbox2.getChildren().addAll(buttonCancel, buttonNewCustomer);
        hbox1.getChildren().addAll(label2);

        // Add layouts to borderpane
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(gridOpenAccount);
        borderPane.setCenter(vbox1);
        borderPane.setRight(vbox2);
        borderPane.setBottom(hbox1);

        // Create "sceneNewAccount"
        sceneNewCustomer = new Scene(borderPane, 600, 400);

        /////////////////////////////////////////////
        //////// 3. Display sceneNewCustomer ////////
        /////////////////////////////////////////////

        // Display "sceneEnterInfo" when starting the application
        window.setScene(sceneNewCustomer);
        window.setOnCloseRequest(e -> {
            // Prevent default close action with consume()
            e.consume();
            closeApplication();
        });
        window.show();
    }

    //////////////////////////////////////////////
    //////// 4. Method: createNewCustomer ////////
    //////////////////////////////////////////////

    // Get data from input fields, create newcustomer object and save to file
    private void createNewCustomer(String firstname, String surname, String email, String address, String username, String password, String dob, String phone) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        try {
            Customer newcustomer = new Customer(firstname, surname, email, address, username, password, formatter.parse(dob));
            newcustomer.setPhone(phone);

            // Save a Customer object to a file
            saveToFile(newcustomer);

            backToMain();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
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