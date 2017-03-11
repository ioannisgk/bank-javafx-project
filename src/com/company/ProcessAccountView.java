package com.company;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.FileInputStream;
import java.util.Date;

public class ProcessAccountView extends Application {

    Stage window;
    Scene sceneProcessAccount;
    TableView<Account> table;

    public ProcessAccountView () {

    }

    public static void run (String[] args) {
        launch(args);
    }
    //@Override
    public void start (Stage mainStage) throws Exception {

        window = mainStage;
        window.setTitle("Bank Application33");

        /////////////////////////////////////////////
        //////// DISPLAY SCENEPROCESSACCOUNT ////////
        /////////////////////////////////////////////

        // GridPane layout with 10px padding
        GridPane gridOpenAccount = new GridPane();
        gridOpenAccount.setPadding(new Insets(15, 20, 10, 30));
        gridOpenAccount.setVgap(8);
        gridOpenAccount.setHgap(10);

        Label labelTitle = new Label("CUSTOMER DATA");
        GridPane.setConstraints(labelTitle, 0, 0);

        // Account ID column
        TableColumn<Account, String> AccountID = new TableColumn<>("ID");
        AccountID.setMinWidth(97);
        AccountID.setCellValueFactory(new PropertyValueFactory<>("accountID"));
        //Sortcode column
        TableColumn<Account, String> sortcode = new TableColumn<>("Sortcode");
        sortcode.setMinWidth(90);
        sortcode.setCellValueFactory(new PropertyValueFactory<>("sortcode"));
        // Type column
        TableColumn<Account, String> type = new TableColumn<>("Type");
        type.setMinWidth(90);
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        // Date column
        TableColumn<Account, Date> date = new TableColumn<>("Date");
        date.setMinWidth(90);
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        // Interest column
        TableColumn<Account, Double> interest = new TableColumn<>("Interest");
        interest.setMinWidth(20);
        interest.setCellValueFactory(new PropertyValueFactory<>("interest"));
        // Balance column
        TableColumn<Account, Double> balance = new TableColumn<>("Balance");
        balance.setMinWidth(110);
        balance.setCellValueFactory(new PropertyValueFactory<>("balance"));

        // Adjust number of visible rows and create table
        // http://stackoverflow.com/questions/26298337/tableview-adjust-number-of-visible-rows
        table = new TableView<>();
        table.setFixedCellSize(25);
        table.prefHeightProperty().bind(Bindings.size(table.getItems()).multiply(table.getFixedCellSize()).add(128));
        table.setItems(getAccount());
        table.getColumns().addAll(AccountID, sortcode, type, date, interest, balance);

        Label labelInfo = new Label("Specific info about an account...");
        GridPane.setConstraints(labelInfo, 0, 2);

        // Add elements to layout and create scene
        gridOpenAccount.getChildren().addAll(table, labelInfo);

        /////////////////////////////////////////////
        //////// DISPLAY SCENEPROCESSACCOUNT ////////
        /////////////////////////////////////////////

        // Create radio buttons to select account type
        Label labelTitle2 = new Label("Select account type:");
        ToggleGroup group = new ToggleGroup();
        RadioButton rbCurrent1 = new RadioButton("Current ID:");
        rbCurrent1.setUserData("Current");
        rbCurrent1.setToggleGroup(group);
        rbCurrent1.setSelected(true);
        RadioButton rbCurrent2 = new RadioButton("Current ID:");
        rbCurrent2.setUserData("Current");
        rbCurrent2.setToggleGroup(group);
        RadioButton rbDeposit = new RadioButton("Deposit ID:");
        rbDeposit.setUserData("Deposit");
        rbDeposit.setToggleGroup(group);
        RadioButton rbSavings = new RadioButton("Savings ID:");
        rbSavings.setUserData("Savings");
        rbSavings.setToggleGroup(group);

        // Create open a new account and back to main button
        Label labelAmount = new Label("Enter amount:");
        TextField amount = new TextField();
        amount.setPromptText("amount");
        Button buttonCancel = new Button("Cancel");
        buttonCancel.setOnAction(e -> {
            try {
                backToMain();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        Button buttonDeposit = new Button("Deposit");
        Button buttonWithdraw = new Button("Withdraw");
        Label label2 = new Label("Copyright 2017, Ioannis Gkourtzounis");

        // Set a Column of Buttons to the Same Width
        // http://docs.oracle.com/javafx/2/layout/size_align.htm
        buttonCancel.setMaxWidth(Double.MAX_VALUE);
        buttonDeposit.setMaxWidth(Double.MAX_VALUE);
        buttonWithdraw.setMaxWidth(Double.MAX_VALUE);

        // Set layout: vbox1 for Center, vbox2 for Right, hbox1 for Bottom
        VBox vbox1 = new VBox(10);
        vbox1.setPadding(new Insets(20, 80, 10, 80));
        VBox vbox2 = new VBox(8);
        vbox2.setPadding(new Insets(20, 80, 10, 0));
        HBox hbox1 = new HBox();
        hbox1.setPadding(new Insets(0, 50, 10, 50));

        // Add elements to layouts
        vbox1.getChildren().addAll(labelTitle2, rbCurrent1, rbCurrent2, rbDeposit, rbSavings);
        vbox2.getChildren().addAll(labelAmount, amount, buttonCancel, buttonDeposit, buttonWithdraw);
        hbox1.getChildren().addAll(label2);

        // Add layouts to borderpane
        // https://docs.oracle.com/javase/8/javafx/api/javafx/scene/layout/BorderPane.html
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(gridOpenAccount);
        borderPane.setCenter(vbox1);
        borderPane.setRight(vbox2);
        borderPane.setBottom(hbox1);

        // Create "sceneProcessAccount"
        sceneProcessAccount = new Scene(borderPane, 600, 400);

        /////////////////////////////////////////////
        //////// DISPLAY SCENEPROCESSACCOUNT ////////
        /////////////////////////////////////////////

        // Display "sceneEnterInfo" when starting the application
        window.setScene(sceneProcessAccount);
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