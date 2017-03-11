package com.company;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    static boolean result;

    public static boolean display(String title, String message) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        Label label = new Label();
        label.setText(message);

        // Buttons for the windows
        Button buttonYes = new Button("Yes");
        Button buttonNo = new Button("No");

        // If a button is clicked save result and close the window
        buttonYes.setOnAction(e -> {
            result = true;
            window.close();
        });
        buttonNo.setOnAction(e -> {
            result = false;
            window.close();
        });

        // GridPane layout with 10px padding
        GridPane gridConfirm = new GridPane();
        gridConfirm.setPadding(new Insets(10, 20, 10, 30));
        gridConfirm.setVgap(20);
        gridConfirm.setHgap(-40);

        buttonYes.setMinWidth(70.0);
        buttonNo.setMinWidth(70.0);

        // Elements placement on grid
        GridPane.setConstraints(label, 0, 0);
        GridPane.setConstraints(buttonYes, 0, 1);
        GridPane.setConstraints(buttonNo, 1, 1);

        // Add elements to layout and create scene
        gridConfirm.getChildren().addAll(label, buttonYes, buttonNo);
        Scene scene = new Scene(gridConfirm, 300, 100);
        window.setScene(scene);
        window.showAndWait();

        return result;
    }

    // Use as info box to inform user about data processing result
    public static void display(String type, String title, String message) {

        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        Label label = new Label();
        label.setText(message);

        // Button to return to main scene
        Button buttonBackToMain = new Button("Back to Main");
        buttonBackToMain.setOnAction(e -> {
            MainView mainView = new MainView();
            mainView.setSession(true);
            try {
                mainView.start(window);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        // GridPane layout with 10px padding
        GridPane gridConfirm = new GridPane();
        gridConfirm.setPadding(new Insets(10, 20, 10, 30));
        gridConfirm.setVgap(20);
        gridConfirm.setHgap(-40);

        buttonBackToMain.setMinWidth(70.0);

        // Elements placement on grid
        GridPane.setConstraints(label, 0, 0);
        GridPane.setConstraints(buttonBackToMain, 0, 1);

        // Add elements to layout and create scene
        gridConfirm.getChildren().addAll(label, buttonBackToMain);
        Scene scene = new Scene(gridConfirm, 300, 100);
        window.setScene(scene);
        window.showAndWait();
    }
}