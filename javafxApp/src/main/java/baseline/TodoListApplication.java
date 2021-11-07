/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Nader Fares
 */
package baseline;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class TodoListApplication extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("scene.fxml")));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());

        stage.setTitle("Todo List");
        stage.setScene(scene);
        stage.show();
    }
}
