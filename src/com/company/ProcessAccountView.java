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

/**
 * ProcessAccountView class
 * Task 1: Create GridPane layout for use with sceneProcessAccount (it is the upper half of this scene)
 * Task 2: Setup radiobuttons so hen user makes selection, the corresponding table row gets selected
 * Task 3: Create sceneProcessAccount, the window which provides the options for deposit and withdrawal
 * Task 4: Display sceneProcessAccount window
 * Task 5: Method showAccountIDs for showing currently selected Account IDs to radiobuttons
 * Task 6: Method deposit
 * Task 7: Method withdraw
 * Task 8: Method getAccounts to get a list of customer's accounts
 **/

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

    public ProcessAccountView (Customer customer) {
        this.customer = customer;
    }

    public static void run (String[] args) {
        launch(args);
    }
    //@Override
    public void start (Stage mainStage) throws Exception {

        window = mainStage;
        window.setTitle("Bank Application");

        /////////////////////////////////////////////
        //////// 1. Create GridPane layout //////////
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

        // Get current customer's accounts
        table.setItems(getAccounts());
        table.getColumns().addAll(AccountID, sortcode, type, date, interest, balance);

        // Add elements to layout and create scene
        gridOpenAccount.getChildren().addAll(table);

        ///////////////////////////////////////
        //////// 2. Setup radiobuttons ////////
        ///////////////////////////////////////

        // Create radio buttons to select account type
        Label labelTitle2 = new Label("Select account type:");
        ToggleGroup group = new ToggleGroup();

        // When user makes selection on radio buttons, the corresponding table row gets selected
        rbCurrent1 = new RadioButton("Current:");
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
        rbCurrent2 = new RadioButton("Current:");
        rbCurrent2.setUserData("Current");
        rbCurrent2.setToggleGroup(group);
        rbCurrent2.setOnAction(e -> {
            try {
                table.getSelectionModel().select((Account)rbCurrent2.getUserData());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        rbDeposit = new RadioButton("Deposit");
        rbDeposit.setUserData("Deposit");
        rbDeposit.setToggleGroup(group);
        rbDeposit.setOnAction(e -> {
            try {
                table.getSelectionModel().select((Account)rbDeposit.getUserData());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        rbSavings = new RadioButton("Savings:");
        rbSavings.setUserData("Savings");
        rbSavings.setToggleGroup(group);
        rbSavings.setOnAction(e -> {
            try {
                table.getSelectionModel().select((Account)rbSavings.getUserData());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        /////////////////////////////////////////////////
        //////// 3. Create sceneProcessAccount //////////
        /////////////////////////////////////////////////

        // Create deposit, withdraw and cancel buttons
        Label labelAmount = new Label("Enter amount:");
        TextField amount = new TextField();
        amount.setPromptText("amount");
        Button buttonCancel = new Button("Cancel");
        
        Button buttonDeposit = new Button("Deposit");
        Button buttonWithdraw = new Button("Withdraw");
        Label label2 = new Label("Copyright 2017, Ioannis Gkourtzounis");

        // Go back to Main Menu or to first login screen
        buttonCancel.setMaxWidth(Double.MAX_VALUE);
        buttonCancel.setOnAction(e -> {
            try {
                if (MainView.loginType.equals("staff")){
                backToMain();
                } else {
                    currentMainView.logout();
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // Deposit and Withdraw buttons
        buttonDeposit.setMaxWidth(Double.MAX_VALUE);
        buttonDeposit.setOnAction(e -> {
            deposit(Double.parseDouble(amount.getText()));
        });

        buttonWithdraw.setMaxWidth(Double.MAX_VALUE);
        buttonWithdraw.setOnAction(e -> {
            withdraw(Double.parseDouble(amount.getText()));
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

        ////////////////////////////////////////////////
        //////// 4. Display sceneProcessAccount ////////
        ////////////////////////////////////////////////

        // Display "sceneEnterInfo" when starting the application
        window.setScene(sceneProcessAccount);
        window.setOnCloseRequest(e -> {
            // Prevent default close action with consume()
            e.consume();
            closeApplication();
        });

        // Show IDs next to radiobuttons
        showAccountIDs();

        if (MainView.loginType.equals("customer")){
            buttonDeposit.setVisible(false);
            buttonWithdraw.setVisible(false);
        } else {
            buttonDeposit.setVisible(true);
            buttonWithdraw.setVisible(true);
        }
        window.show();
    }

    ///////////////////////////////////////////
    //////// 5. Method: showAccountIDs ////////
    ///////////////////////////////////////////

    // Check which is the currently selected account type and get its ID
    private void showAccountIDs() {
        boolean firstCurrentAccount = false;
        if (this.customer.getCustomerAccounts() != null){
            for(Account account : customer.getCustomerAccounts()) {
                if (firstCurrentAccount == false && account.getType().toLowerCase().equals("current")){
                    firstCurrentAccount = true;
                    rbCurrent1.setText("Current ID:" + account.getAccountID());
                    rbCurrent1.setUserData(account);
                }
                if (firstCurrentAccount = true && account.getType().toLowerCase().equals("current")){
                    rbCurrent2.setText("Current ID:" + account.getAccountID());
                    rbCurrent2.setUserData(account);
                }
                if (account.getType().toLowerCase().equals("deposit")){
                    rbDeposit.setText("Deposit ID:" + account.getAccountID());
                    rbDeposit.setUserData(account);
                }
                if (account.getType().toLowerCase().equals("savings")){
                    rbSavings.setText("Savings ID:" + account.getAccountID());
                    rbSavings.setUserData(account);
                }
            }
        }
    }

    ////////////////////////////////////
    //////// 6. Method: deposit ////////
    ////////////////////////////////////

    // Get selected Account, deposit, save to file and refresh
    private void deposit(double inputAmount) {
        try {
            Account account = table.getSelectionModel().getSelectedItem();
            account.deposit(inputAmount);
            Main.saveToFile(this.customer);
            table.setItems(getAccounts());
            table.refresh();
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Amount must be a number");
        }
    }

    /////////////////////////////////////
    //////// 7. Method: withdraw ////////
    /////////////////////////////////////

    // Get selected Account, withdraw, save to file and refresh
    private void withdraw(double inputAmount) {
        try {
            Account account = table.getSelectionModel().getSelectedItem();
            account.withdraw(inputAmount);
            Main.saveToFile(this.customer);
            table.setItems(getAccounts());
            table.refresh();
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "Amount must be a number");
        }
    }

    ////////////////////////////////////////
    //////// 8. Method: getAccounts ////////
    ////////////////////////////////////////

    // Get current customer's accounts and return his accounts in a list
    private ObservableList<Account> getAccounts() {
        Customer newcustomer = new Customer();
        ObservableList<Account> accounts = FXCollections.observableArrayList();
        boolean firstCurrentAccount = false;
        if (this.customer.getCustomerAccounts() != null){
            for(Account account : customer.getCustomerAccounts()) {
                accounts.add(account);
            }
        }
        return accounts;
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