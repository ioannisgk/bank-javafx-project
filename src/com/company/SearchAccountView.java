package com.company;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class SearchAccountView extends Application {

    Stage window;
    Scene sceneSearchAccount;
    TableView<Account> table;

    public SearchAccountView () {

    }

    public static void run (String[] args) {
        launch(args);
    }
    //@Override
    public void start (Stage mainStage) throws Exception {

        window = mainStage;
        window.setTitle("Bank Application");

        /////////////////////////////////////////////
        //////// DISPLAY SCENESEARCHACCOUNT /////////
        /////////////////////////////////////////////

        // GridPane layout with 10px padding
        GridPane gridOpenAccount = new GridPane();
        gridOpenAccount.setPadding(new Insets(15, 20, 10, 30));
        gridOpenAccount.setVgap(8);
        gridOpenAccount.setHgap(10);

        // Account ID column
        TableColumn<Account, String> AccountID = new TableColumn<>("ID");
        AccountID.setMinWidth(97);
        AccountID.setCellValueFactory(new PropertyValueFactory<>("accountID"));
        // Firstname column
        TableColumn<Account, String> firstname = new TableColumn<>("Firstname");
        firstname.setMinWidth(90);
        firstname.setCellValueFactory(new PropertyValueFactory<>("sortcode"));
        // Surname column
        TableColumn<Account, String> surname = new TableColumn<>("Surname");
        surname.setMinWidth(90);
        surname.setCellValueFactory(new PropertyValueFactory<>("type"));
        // Email column
        TableColumn<Account, String> email = new TableColumn<>("Email");
        email.setMinWidth(90);
        email.setCellValueFactory(new PropertyValueFactory<>("type"));
        // Balance column
        TableColumn<Account, Double> balance = new TableColumn<>("Balance");
        balance.setMinWidth(110);
        balance.setCellValueFactory(new PropertyValueFactory<>("balance"));

        // Adjust number of visible rows and create table
        // http://stackoverflow.com/questions/26298337/tableview-adjust-number-of-visible-rows
        table = new TableView<>();
        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(254));
        table.setItems(getAccount());
        table.getColumns().addAll(AccountID, firstname, surname, email, balance);

        // Add elements to layout and create scene
        gridOpenAccount.getChildren().addAll(table);

        /////////////////////////////////////////////
        //////// DISPLAY SCENESEARCHACCOUNT /////////
        /////////////////////////////////////////////

        // Create radio buttons to select account type
        Label labelTitle2 = new Label("Accounts listed with balance over 15,240\nThank you for using the Bank Application!");

        // Create open a new account and back to main button
        Button buttonExport = new Button("Export Results");
        Button buttonCancel = new Button("Back to Main Screen");
        buttonCancel.setOnAction(e -> {
            try {
                backToMain();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        Label label2 = new Label("Copyright 2017, Ioannis Gkourtzounis");

        // Set a Column of Buttons to the Same Width
        // http://docs.oracle.com/javafx/2/layout/size_align.htm
        buttonExport.setMaxWidth(Double.MAX_VALUE);
        buttonCancel.setMaxWidth(Double.MAX_VALUE);

        // Set layout: vbox1 for Center, vbox2 for Right, hbox1 for Bottom
        VBox vbox1 = new VBox(10);
        vbox1.setPadding(new Insets(20, 0, 10, 50));
        VBox vbox2 = new VBox(8);
        vbox2.setPadding(new Insets(20, 80, 10, 0));
        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(0, 50, 10, 50));

        // Add elements to layouts
        vbox1.getChildren().addAll(labelTitle2);
        vbox2.getChildren().addAll(buttonCancel, buttonExport);
        hbox1.getChildren().addAll(label2);

        // Add layouts to borderpane
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(gridOpenAccount);
        borderPane.setCenter(vbox1);
        borderPane.setRight(vbox2);
        borderPane.setBottom(hbox1);

        // Create "sceneSearchAccount"
        sceneSearchAccount = new Scene(borderPane, 600, 400);

        /////////////////////////////////////////////
        //////// DISPLAY SCENESEARCHACCOUNT /////////
        /////////////////////////////////////////////

        // Display "sceneEnterInfo" when starting the application
        window.setScene(sceneSearchAccount);
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

    //Get all of the products
    public ObservableList<Account> getAccount(){
        Customer newcustomer = new Customer();
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        accounts.add(new CurrentAccount(newcustomer, 20.00));
        accounts.add(new CurrentAccount(newcustomer, 15.00));
        accounts.add(new DepositAccount(newcustomer, 120.00));
        accounts.add(new SavingsAccount(newcustomer, 500.00, 1));
        return accounts;
    }
}