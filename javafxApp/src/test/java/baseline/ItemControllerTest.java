package baseline;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ItemControllerTest {

    @Test
    void addItem() {
        ItemController app = new ItemController();

        ObservableList<Item> expectedList = FXCollections.observableArrayList();
        Item item1 = new Item("Dentist Appointment", LocalDate.parse("2014-01-24"));
        Item item2 = new Item("Get groceries", LocalDate.parse("2021-09-11"));
        Item item3 = new Item("Walk the dog", null);
        int oldLength = app.getItems().size();
        expectedList.add(item1);
        expectedList.add(item2);
        expectedList.add(item3);

        ObservableList<Item> actualList = FXCollections.observableArrayList();
//        app.addItem();
//        app.addItem();
//        app.addItem();
        int newLength = app.getItems().size();
        assertEquals(3, newLength);
    }

    @Test
    void validateDescriptionInput() {
    }

    @Test
    void checkForUnique() {
    }

    @Test
    void throwAlert() {
    }

    @Test
    void refresh() {
    }

    @Test
    void clearItems() {
    }

    @Test
    void deleteItem() {
    }

    @Test
    void editItem() {
    }

    @Test
    void markItem() {
    }

    @Test
    void showAll() {
    }

    @Test
    void showComplete() {
    }

    @Test
    void showIncomplete() {
    }

    @Test
    void saveItems() {
    }

    @Test
    void loadItems() {
    }

    @Test
    void verifyDate() {
    }

    @Test
    void sortItems() {
    }

    @Test
    void refreshList() {
    }
}