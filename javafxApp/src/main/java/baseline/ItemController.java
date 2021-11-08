/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Nader Fares
 */
package baseline;

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
    ItemControllerMethods items = new ItemControllerMethods();

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

    private String currentView = "all";  //all/complete/incomplete

    //adds item to listview
    @FXML
    private void addItemToList(ActionEvent actionEvent) {
        try {
            items.addItem(descriptionText.getText(), dueDate.getValue());
        } catch(InputMismatchException e) {
            System.out.println("Input Error");
        } finally {
            refresh();
        }
    }

    //delete every item in the listview
    @FXML
    private void clearAll(ActionEvent actionEvent) {
        items.clearItems();
        refresh();
    }

    //delete selected item in listview
    @FXML
    private void deleteItemFromList(ActionEvent actionEvent) {
        int itemId = itemListView.getSelectionModel().getSelectedItem().getItemId();        //get selected item index

        items.deleteItem(itemId); //delete item with index
        refresh();
    }

    //edit selected item in listview
    @FXML
    private void editItemList(ActionEvent actionEvent) {
      int itemId = itemListView.getSelectionModel().getSelectedItem().getItemId();   //get the index of the item to be edited

        items.editItem(itemId, descriptionText.getText(), dueDate.getValue());   //edit item
        refresh();
    }

    //mark selected item in listview as complete/incomplete
    @FXML
    private void markItemInList(ActionEvent actionEvent) {
        int itemId = itemListView.getSelectionModel().getSelectedItem().getItemId();   //        highlight completed item cell box

        items.markItem(itemId);

        refresh();
    }

    //display all items in listview
    @FXML
    private void showAllItems(ActionEvent actionEvent) {     //default
        showAll();

    }

    //display all items marked as complete in listview
    @FXML
    private void showCompleteItems(ActionEvent actionEvent) {
        showComplete();
    }

    //display all items marked as incomplete in listview
    @FXML
    private void showIncompleteItems(ActionEvent actionEvent) {
        showIncomplete();
    }


    //save list of items into single txt file
    @FXML
    private void saveList(ActionEvent actionEvent) {
        saveItems();
    }


    //load a previously saved list
    @FXML
    private void loadList(ActionEvent actionEvent) {
        loadItems();
    }

    //sorts items based on due date
    @FXML
    private void sortItemsList(ActionEvent actionEvent) {
        sortItems();
    }

    public void refresh() {
        //refresh text fields after every addition
        descriptionText.setText("");
        dueDate.setValue(null);
        refreshList(currentView);
    }

    public void sortItems() {
        items.getItems().sort(Comparator.comparing(Item::getItemDueDate, Comparator.nullsFirst(Comparator.naturalOrder())));
        itemListView.setItems(items.getItems());
    }

    public void refreshList(String currentView) {
        items.getItems().sort(Comparator.comparingInt(Item::getItemId));
        if (currentView.equals("complete")) {
            showComplete();
        } else if (currentView.equals("incomplete")) {
            showIncomplete();
        } else itemListView.setItems(items.getItems());
    }

    public void showAll() {
        currentView = "all";
        itemListView.setItems(items.getItems());//        set listview equal to original list
    }

    public void showComplete() {
        //create new filtered list
        FilteredList<Item> completeItems = new FilteredList<>(items.getItems(), Item::isItemComplete);        //filter list to show only items marked as complete

        currentView = "complete";

        //set listview equal to filtered list
        itemListView.setItems(completeItems);
    }

    public void showIncomplete() {
        //        create new filtered list
        FilteredList<Item> incompleteItems = new FilteredList<>(items.getItems(), item -> !item.isItemComplete());      //filter list to show only items marked as incomplete

        currentView = "incomplete";

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
            writeItems(selectedFile);
        } else
            System.out.println("File not found");
    }

    public void writeItems(File selectedFile) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile+".txt"))) { //make file a txt

            for (int i = 0; i < itemListView.getItems().size(); i++) {          //print item list onto newly created text file
                bufferedWriter.write(itemListView.getItems().get(i) + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
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
                new ErrorMap("file");     //            throw error alert if not
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
        itemListView.setItems(null);    //initialize
        disableDatePicker();
    }



}
