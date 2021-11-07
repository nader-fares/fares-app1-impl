/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Nader Fares
 */
package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ItemController implements Initializable {
    private final ObservableList<Item> items = FXCollections.observableArrayList();

    //displays list of items on gui
    @FXML
    private ListView<Item> itemListView;

    //holds description input
    @FXML
    private TextField descriptionText;

    //input due date for item
    @FXML
    private DatePicker dueDate;

    @FXML
    private Button addItemButton;

    @FXML
    private Button clearAllButton;

    //adds item to listview
    @FXML
    private void addItemToList(ActionEvent actionEvent) {
//        button
        try {
            validateDescriptionInput(); //        check if description and date match requirements
            checkForUnique();   //check if item is unique
            addItem();    //add item to list
        } catch(InputMismatchException e) {
            System.out.println("Input Error");
        } finally {
            refresh();
        }
    }
    //delete every item in the listview
    @FXML
    private void clearAll(ActionEvent actionEvent) {
        //button
        clearItems(); //        deletes every item in the listview
    }

    //delete selected item in listview
    @FXML
    private void deleteItemFromList(ActionEvent actionEvent) {
//        button
        //        get selected item index
        int itemId = itemListView.getSelectionModel().getSelectedItem().getItemId();

        deleteItem(itemId); //delete item
    }

    //edit selected item in listview
    @FXML
    private void editItemList(ActionEvent actionEvent) {
//        button
//        when clicking on listview cell show item description in
//            textfield and
//            due date in datepicker
//        track changes in textfield and datepicker and alter the same item
        int itemId = itemListView.getSelectionModel().getSelectedIndex();   //get the index of the item to be edited

        editItem(itemId);   //edit item
    }

    //mark selected item in listview as complete/incomplete
    @FXML
    private void markItemInList(ActionEvent actionEvent) {
//        button

        int itemId = itemListView.getSelectionModel().getSelectedIndex();   //        highlight completed item cell box

        markItem(itemId);
    }

    //display all items in listview
    @FXML
    private void showAllItems(ActionEvent actionEvent) {     //default

        //radio button
        showAll();

    }

    //display all items marked as complete in listview
    @FXML
    private void showCompleteItems(ActionEvent actionEvent) {
        //radio button
        showComplete();
    }

    //display all items marked as incomplete in listview
    @FXML
    private void showIncompleteItems(ActionEvent actionEvent) {
        //radio button
        showIncomplete();
    }


    //save list of items into single txt file
    @FXML
    private void saveList(ActionEvent actionEvent) {
//        button
        saveItems();
    }


    //load a previously saved list
    @FXML
    private void loadList(ActionEvent actionEvent) {
//        button
        loadItems();
    }

    //sorts items based on due date
    @FXML
    private void sortItemsList(ActionEvent actionEvent) {
        sortItems();
    }

    //add item to list
    public void addItem() {
        items.add(new Item(descriptionText.getText(), dueDate.getValue())); //create object with description from textfield and date from date picker
        itemListView.setItems(items);
    }

    //if item does not meet description requirements, throw alert and block item from being added
    public void validateDescriptionInput() {
        String trimmedInput = descriptionText.getText().trim();
        if (trimmedInput.length() < 1 || trimmedInput.length() > 256) {
            throwAlert("description");
        }
    }

    public void checkForUnique() {
        //if item with the same description and due date already exists, throw alert and block item from being added
        for (Item item : items) {
            if (descriptionText.getText().equals(item.getItemDescription())) {
                throwAlert("unique");
            }
        }
    }

    public void throwAlert(String errorType) {
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

    public void refresh() {
        //refresh text fields after every addition
        descriptionText.setText("");
        dueDate.setValue(null);
        refreshList();
    }


    public void sortItems() {
        items.sort(Comparator.comparing(Item::getItemDueDate, Comparator.nullsFirst(Comparator.naturalOrder())));
        itemListView.setItems(items);
    }

    public void refreshList() {
        items.sort(Comparator.comparingInt(Item::getItemId));

        itemListView.setItems(items);
    }


    public void clearItems() {
        items.clear();
        itemListView.setItems(items);
    }

    public void deleteItem(int itemId) {
//        delete item with the item index
        items.removeIf(item -> item.getItemId() == itemId);
        itemListView.setItems(items);
    }

    public void editItem(int itemId) {
        if (descriptionText.getText().trim().length() != 0) {   //only change description if textfield is not empty
            items.get(itemId).setItemDescription(descriptionText.getText());
        }
        if (dueDate.getValue() != null)             //only change due date if datepicker is not empty
            items.get(itemId).setItemDueDate(dueDate.getValue());

        refresh();

        itemListView.setItems(items);
    }

    public void markItem(int itemId) {
        items.get(itemId).setItemComplete(!items.get(itemId).isItemComplete()); //        check for items complete status and make it the opposite
        itemListView.setItems(items);
    }

    public void showAll() {
        itemListView.setItems(items);//        set listview equal to original list
    }

    public void showComplete() {
        //        create new filtered list
        FilteredList<Item> completeItems = new FilteredList<>(items, Item::isItemComplete);        //filter list to show only items marked as complete

        //set listview equal to filtered list
        itemListView.setItems(completeItems);

    }

    public void showIncomplete() {
        //        create new filtered list
        FilteredList<Item> incompleteItems = new FilteredList<>(items, item -> !item.isItemComplete());      //filter list to show only items marked as incomplete

        //set listview equal to filtered list
        itemListView.setItems(incompleteItems);
    }

    public void saveItems() {
        //        opens filechooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save file");
        fileChooser.showSaveDialog(null);

//        user chooses file
        File selectedFile = fileChooser.getSelectedFile();      //save location and name

//        create text file
        if (selectedFile != null) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile+".txt"))) { //make file a txt

                for (int i = 0; i < itemListView.getItems().size(); i++) {          //print item list onto newly created text file
                    bufferedWriter.write(itemListView.getItems().get(i) + "\n");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            System.out.println("File not found");
    }

    public void loadItems() {
        //        opens filechooser
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Load File");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
        fileChooser.setFileFilter(filter);
        fileChooser.showOpenDialog(null);

        File selectedFile = fileChooser.getSelectedFile();
        if (selectedFile != null) {

            //        only allow txt file to be loaded
            if (!selectedFile.getName().endsWith(".txt")) {
                throwAlert("file");     //            throw error alert if not
                return;
            }

            clearAllButton.fire();      //        clear original list

            try {
                try (Scanner reader = new Scanner(selectedFile)) {

                    while (reader.hasNext()) {
                        //read word by word
                        String currentLine = reader.next().trim();

                        //variable to store description
                        String itemDescription;

                        //variable to store date
                        LocalDate date;
                        date = verifyDate(currentLine);

                        //if line contains date display it
                        if (date != null) {
                            dueDate.setValue(date);
                            currentLine = reader.nextLine();    //first word
                        } else {
                            dueDate.setValue(null);
                            currentLine += reader.nextLine();
                        }

                        itemDescription = currentLine.replace("|", "").trim();
                        descriptionText.setText(itemDescription);
                        addItemButton.fire();           //create item object for each line
                    }
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    //verifies date follows format
    public LocalDate verifyDate(String currentLine) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(currentLine, dateFormat);    //date will be returned if date format matches pattern
        } catch (DateTimeException e) {
            return null;
        }
    }

    //blocks user from typing in datepicker textfield
    private void disableDatePicker() {
        dueDate.getEditor().setDisable(true);
        dueDate.getEditor().setOpacity(1);          //so that textfield is not greyed out when clicking it
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemListView.setItems(null);    //initialize list
        disableDatePicker();
    }



}
