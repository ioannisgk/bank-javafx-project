package com.company;


import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    static boolean result;

    public static boolean display(String title, String message) {
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
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

        VBox layout = new VBox(10);

        // Add label and buttons to layout
        layout.getChildren().addAll(label, buttonYes, buttonNo);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return result;
    }
}
