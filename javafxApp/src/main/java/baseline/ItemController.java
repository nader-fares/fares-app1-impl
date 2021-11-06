package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemController implements Initializable {

//    private ObservableList<Item> items = FXCollections.observableArrayList();

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
    }

    //delete every item in the listview
    public void clearAll(ActionEvent actionEvent) {
        /*
        button
        deletes every item in the listview
         */
    }

    //delete selected item in listview
    public void deleteItem(ActionEvent actionEvent) {
//        button
//        get selected item index
        int itemId;
//        delete item with the item index
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
    }

    //display all items in listview
    public void showAllItems(ActionEvent actionEvent) {
        //radio button
        /*
        default
        set listview equal to original list
         */
    }

    //display all items marked as complete in listview
    public void showCompleteItems(ActionEvent actionEvent) {
        //radio button
//        create new filtered list
        FilteredList<Item> completeItems;        //filter list to show only items marked as complete
        //set listview equal to filtered list
    }

    //display all items marked as incomplete in listview
    public void showIncompleteItems(ActionEvent actionEvent) {
        //radio button
//        create new filtered list
        FilteredList<Item> incompleteItems;      //filter list to show only items marked as incomplete
        //set listview equal to filtered list
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
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemListView.setItems(null);
    }
}
