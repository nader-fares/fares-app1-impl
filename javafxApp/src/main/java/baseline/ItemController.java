package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.InputMismatchException;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

    private ObservableList<Item> items = FXCollections.observableArrayList();

    //displays list of items on gui
    @FXML
    private ListView<Item> itemListView;

    //holds description input
    @FXML
    private TextField descriptionText;

    //input due date for item
    @FXML
    private DatePicker dueDate;

    //adds item to listview
    public void addItem(ActionEvent actionEvent) {
        /*
        button
        check if description and date match requirements
        if so
            create object with
                description set from the inputted text field and
                date set from datepicker
                initialized incomplete
        else throw alert
        object added to item list
         */
        try {
            validateDescriptionInput();
            items.add(new Item(descriptionText.getText(), dueDate.getValue()));
            itemListView.setItems(items);
        } catch(InputMismatchException e) {
            System.out.println("Input Error");
        } finally {
            refresh();
        }
    }

    public void validateDescriptionInput() {
        String trimmedInput = descriptionText.getText().trim();
        String formattedDate = dueDate.getValue().toString().trim();
        System.out.println(formattedDate);
        if (dueDate.getValue() != null) {
        }
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        String string = "\\d{4}/[01]\\d/[0-3]\\d";
        if (trimmedInput.length() < 1 || trimmedInput.length() > 256 || dueDate.getValue() == null || !dueDate.getValue().toString().matches("^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$")) {
            throwAlert();
        }
    }

    public void throwAlert() {
        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("Invalid Input!");
        errorAlert.setContentText("Description must be between 1 and 256 characters in length.");
        errorAlert.showAndWait();
        throw new InputMismatchException();
    }

    public void refresh() {
        descriptionText.setText("");
        dueDate.setValue(null);
    }

    //delete every item in the listview
    public void clearAll(ActionEvent actionEvent) {
        /*
        button
        deletes every item in the listview
         */
        if (!items.isEmpty()){
            items.clear();
        }
        itemListView.setItems(items);
    }

    //delete selected item in listview
    public void deleteItem(ActionEvent actionEvent) {
//        button
//        get selected item index
        int itemId = itemListView.getSelectionModel().getSelectedItem().getItemId();
//        delete item with the item index
        items.removeIf(item -> item.getItemId() == itemId);
        itemListView.setItems(items);
    }

    //edit selected item in listview
    public void editItem(ActionEvent actionEvent) {
        /*
        button
        when clicking on listview cell show item description in
            textfield and
            due date in datepicker
        track changes in textfield and datepicker and alter the same item
         */
    }

    //mark selected item in listview as complete/incomplete
    public void markItem(ActionEvent actionEvent) {
        /*
        button
        check for items complete status
            if true -> change to false
            if false -> change to true
        highlight completed item cell box
         */
        int itemId = itemListView.getSelectionModel().getSelectedIndex();
        items.get(itemId).setItemComplete(!items.get(itemId).isItemComplete());
        itemListView.setItems(items);
    }

    //display all items in listview
    public void showAllItems(ActionEvent actionEvent) {
        //radio button
        /*
        default
        set listview equal to original list
         */
        itemListView.setItems(items);
    }

    //display all items marked as complete in listview
    public void showCompleteItems(ActionEvent actionEvent) {
        //radio button
//        create new filtered list
        FilteredList<Item> completeItems = new FilteredList<>(items, Item::isItemComplete);        //filter list to show only items marked as complete

        //set listview equal to filtered list
        itemListView.setItems(completeItems);

    }

    //display all items marked as incomplete in listview
    public void showIncompleteItems(ActionEvent actionEvent) {
        //radio button
//        create new filtered list
        FilteredList<Item> incompleteItems = new FilteredList<>(items, item -> !item.isItemComplete());      //filter list to show only items marked as incomplete

        //set listview equal to filtered list
        itemListView.setItems(incompleteItems);
    }

    //save list of items into single txt file
    public void saveList(ActionEvent actionEvent) {
        /*
        button
        opens filechooser
        user chooses file
            save location
            name
        create text file
        print item list onto newly created text file
         */
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Save File");
        File selectedFile = directoryChooser.showDialog(null);
        if (selectedFile != null) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(selectedFile + "/output.txt"))) {
                for (int i = 0; i < itemListView.getItems().size(); i++) {
                    bufferedWriter.write(itemListView.getItems().get(i).getItemDescription() + "\n");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            System.out.println("File not found");
    }

    //load a previously saved list
    public void loadList(ActionEvent actionEvent) {
        /*
        button
        opens filechooser
        only allow txt file to be loaded
            throw error alert if not
        clear original list
        read line by line and create item object for each line
        add each item to list
         */
        FileChooser filechooser = new FileChooser();
        filechooser.setTitle("Load File");
        File selectedFile = filechooser.showOpenDialog(null);
        if (selectedFile != null) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile))) {
                String currentLine = bufferedReader.readLine();
                while (currentLine != null) {
                    System.out.println(currentLine);
                    descriptionText.setText(currentLine);
                    currentLine = bufferedReader.readLine();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else
            System.out.println("File not found");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemListView.setItems(null);
    }
}
