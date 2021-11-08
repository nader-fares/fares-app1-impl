/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Nader Fares
 */
package baseline;

import javafx.scene.control.Alert;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

public class ErrorMap {
    public ErrorMap(String errorType) {
        //map of errors object, each different error has a different key
        Map<String, Error> errors = new HashMap<>();
        errors.put("description", new Error("Invalid Input!", "Description must be between 1 and 256 characters in length."));
        errors.put("file", new Error("Invalid File!", "Must be a .txt file."));
        errors.put("unique", new Error("Invalid Item!", "Item already exists in list."));

        //depending on key, error will display different message
        Error error = errors.get(errorType);
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText(error.getHeaderText());
        errorAlert.setContentText(error.getContentText());
        errorAlert.showAndWait();
        throw new InputMismatchException();
    }
}
