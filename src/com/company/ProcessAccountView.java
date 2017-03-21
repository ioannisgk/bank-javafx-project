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
import java.util.Date;
import javax.swing.JOptionPane;

public class ProcessAccountView extends Application {

    Stage window;
    Scene sceneProcessAccount;
    TableView<Account> table;
    public Customer customer;
    private RadioButton rbCurrent1;
    private RadioButton rbCurrent2;
    private RadioButton rbDeposit;
    private RadioButton rbSavings;
    public MainView currentMainView;
    public ProcessAccountView () {

    }
    public ProcessAccountView (Customer _customer) {
        this.customer = _customer;
    }
    public static void run (String[] args) {
        launch(args);
    }
    //@Override
    public void start (Stage mainStage) throws Exception {

        window = mainStage;
        window.setTitle("Bank Application");

        /////////////////////////////////////////////
        //////// DISPLAY SCENEPROCESSACCOUNT ////////
        /////////////////////////////////////////////

        // GridPane layout with 10px padding
        GridPane gridOpenAccount = new GridPane();
        
        gridOpenAccount.setPadding(new Insets(15, 20, 10, 30));
        gridOpenAccount.setVgap(8);
        gridOpenAccount.setHgap(10);

        // Account ID column
        TableColumn<Account, String> AccountID = new TableColumn<>("ID");
        AccountID.setMinWidth(97);
        AccountID.setCellValueFactory(new PropertyValueFactory<Account, String>("accountID"));
        
        // Sortcode column
        TableColumn<Account, String> sortcode = new TableColumn<>("Sortcode");
        sortcode.setMinWidth(90);
        sortcode.setCellValueFactory(new PropertyValueFactory<Account, String>("sortcode"));
        // Type column
        TableColumn<Account, String> type = new TableColumn<>("Type");
        type.setMinWidth(90);
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        // Date column
        TableColumn<Account, Date> date = new TableColumn<>("Date");
        date.setMinWidth(90);
        date.setCellValueFactory(new PropertyValueFactory<>("dateOpened"));
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
        
        rbCurrent1 = new RadioButton("Current ID:");
        rbCurrent1.setUserData("Current");
        rbCurrent1.setToggleGroup(group);
        rbCurrent1.setSelected(true);
        rbCurrent1.setOnAction(e -> {
            try {
                table.getSelectionModel().select((Account)rbCurrent1.getUserData());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        rbCurrent2 = new RadioButton("Current ID:");
        rbCurrent2.setUserData("Current");
        rbCurrent2.setOnAction(e -> {
            try {
                table.getSelectionModel().select((Account)rbCurrent2.getUserData());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });;
        rbCurrent2.setToggleGroup(group);
        rbDeposit = new RadioButton("Deposit ID:");
        rbDeposit.setOnAction(e -> {
            try {
                table.getSelectionModel().select((Account)rbDeposit.getUserData());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        rbDeposit.setUserData("Deposit");
        rbDeposit.setToggleGroup(group);
        rbSavings = new RadioButton("Savings ID:");
        rbSavings.setUserData("Savings");
        rbSavings.setOnAction(e -> {
            try {
                table.getSelectionModel().select((Account)rbSavings.getUserData());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        rbSavings.setToggleGroup(group);

        // Create open a new account and back to main button
        Label labelAmount = new Label("Enter amount:");
        TextField amount = new TextField();
        amount.setPromptText("amount");
        Button buttonCancel = new Button("Cancel");
        
        Button buttonDeposit = new Button("Deposit");
        Button buttonWithdraw = new Button("Withdraw");
        Label label2 = new Label("Copyright 2017, Ioannis Gkourtzounis");

        // Set a Column of Buttons to the Same Width
        // http://docs.oracle.com/javafx/2/layout/size_align.htm
        buttonCancel.setMaxWidth(Double.MAX_VALUE);
        buttonCancel.setOnAction(e -> {
            try {
                if (MainView.loginType.equals("staff")){
                backToMain();
                }else{
                    currentMainView.logout();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        buttonDeposit.setMaxWidth(Double.MAX_VALUE);
        buttonDeposit.setOnAction(e -> {
            try {
                Account account = table.getSelectionModel().getSelectedItem();
                double inputAmount = Double.parseDouble(amount.getText()); 
                account.deposit(inputAmount);
                Main.saveToFile(this.customer);
                table.setItems(getAccount());
                table.refresh();
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Amount must be a number");
            }
        });
        buttonWithdraw.setMaxWidth(Double.MAX_VALUE);
        buttonWithdraw.setOnAction(e -> {
            try {
                Account account = table.getSelectionModel().getSelectedItem();
                double inputAmount = Double.parseDouble(amount.getText()); 
                account.withdraw(inputAmount);
                Main.saveToFile(this.customer);
                table.setItems(getAccount());
                table.refresh();
            } catch (Exception e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "Amount must be a number");
            }
        });
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
        boolean firstCurrentAccount = false;
        if (this.customer.getCustomerAccounts() != null){
            for(Account account : customer.getCustomerAccounts()) {
                if (firstCurrentAccount == false && account.getType().toLowerCase().equals("current")){
                    firstCurrentAccount = true;
                    rbCurrent1.setText("Current Account ID:" + account.getAccountID());
                    rbCurrent1.setUserData(account);
                }
                if (firstCurrentAccount = true && account.getType().toLowerCase().equals("current")){
                    rbCurrent2.setText("Current Account ID:" + account.getAccountID());
                    rbCurrent2.setUserData(account);
                }
                if (account.getType().toLowerCase().equals("deposit")){
                    rbDeposit.setText("Deposit Account ID:" + account.getAccountID());
                    rbDeposit.setUserData(account);
                }
                if (account.getType().toLowerCase().equals("savings")){
                    rbSavings.setText("Savings Account ID:" + account.getAccountID());
                    rbSavings.setUserData(account);
                }
            }
        }
        table.setOnMouseClicked(e -> {
            try {
               Account account = table.getSelectionModel().getSelectedItem();
               Account account1 = (Account)rbCurrent1.getUserData();
               Account account2 = (Account)rbCurrent2.getUserData();
               Account account3 = (Account)rbDeposit.getUserData();
               Account account4 = (Account)rbSavings.getUserData();
               if (account.getAccountID().equals(account1.getAccountID())){
                   rbCurrent1.setSelected(true);
               }else if(account.getAccountID().equals(account2.getAccountID())){
                   rbCurrent2.setSelected(true);
               }else if(account.getAccountID().equals(account3.getAccountID())){
                   rbDeposit.setSelected(true);
               }else if(account.getAccountID().equals(account4.getAccountID())){
                   rbSavings.setSelected(true);
               }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        if (MainView.loginType.equals("customer")){
            buttonDeposit.setVisible(false);
            buttonWithdraw.setVisible(false);
        }else{
            buttonDeposit.setVisible(true);
            buttonWithdraw.setVisible(true);
        }
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
        boolean firstCurrentAccount = false;
        if (this.customer.getCustomerAccounts() != null){
            for(Account account : customer.getCustomerAccounts()) {
                accounts.add(account);
            }
        }
//        accounts.add(new CurrentAccount(newcustomer, 20.00));
//        accounts.add(new CurrentAccount(newcustomer, 15.00));
//        accounts.add(new DepositAccount(newcustomer, 120.00));
//        accounts.add(new SavingsAccount(newcustomer, 500.00, 1));
        return accounts;
    }
}