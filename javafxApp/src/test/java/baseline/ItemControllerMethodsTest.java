package baseline;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ItemControllerMethodsTest {

    ItemControllerMethods items = new ItemControllerMethods();

    @BeforeEach
    public void startUp() {
        items.getItems().add(new Item("test test 1", null));

        Item.resetCount();
    }

    @Test
    void addItemHelper() {
        items.addItemHelper("test test 2", null);
        items.addItemHelper("test test 3", null);

        int size = items.getItems().size();

        assertEquals(3, size);
    }

    @Test
    void addItem() {
        items.addItem("test test 1", null);     //ignored
        items.addItem("test test 2", null);     //added
        items.addItem("test test 3", LocalDate.parse("1012-10-30"));        //added
        items.addItem("", null);          //ignored

        int size = items.getItems().size();

        assertEquals(3, size);
    }

    @Test
    void clearItems() {
        items.addItemHelper("test test 2", null);
        items.addItemHelper("test test 3", null);
        items.clearItems();

        int size = items.getItems().size();
        assertEquals(0, size);

    }

    @Test
    void deleteItem() {
        items.addItemHelper("test test 2", null);
        items.addItemHelper("test test 3", null);

        items.deleteItem(1);
        int size = items.getItems().size();
        assertEquals(2, size);
    }

    @Test
    void editItem() {
        items.editItem(1, "this string has just been edited", LocalDate.parse("2014-12-10"));

        if (items.getItemById(1).isPresent()) {
            String actualDescription = items.getItemById(1).get().getItemDescription();
            LocalDate actualDate = items.getItemById(1).get().getItemDueDate();


            String expectedDescription = "this string has just been edited";
            LocalDate expectedDate = LocalDate.parse("2014-12-10");

            assertEquals(expectedDescription, actualDescription);
            assertEquals(expectedDate, actualDate);
        }
    }

    @Test
    void markItem() {
        //complete value starts as false and toggles to the opposite with each use

        Optional<Item> tempItem = items.getItemById(1);
        if (tempItem.isPresent()) {
            tempItem.get().toggleItemComplete();
            assertTrue(tempItem.get().isItemComplete());


            tempItem.get().toggleItemComplete();
            assertFalse(tempItem.get().isItemComplete());


            tempItem.get().toggleItemComplete();
            assertTrue(tempItem.get().isItemComplete());
        }

    }

    @Test
    void getItemById() {
        items.getItems().add(new Item("test test 1", null));
        items.addItemHelper("test test 2", null);
        items.addItemHelper("test test 3", null);
        items.addItemHelper("test test 4", null);
        items.addItemHelper("test test 5", null);
        items.addItemHelper("test test 6", null);
        items.addItemHelper("test test 7", null);

        if (items.getItemById(4).isPresent()) {
            String actualDescription = items.getItemById(4).get().getItemDescription();
            String expectedDescription = "test test 4";
            assertEquals(expectedDescription, actualDescription);
        }

    }

    @Test
    void checkForUnique() {
        items.addItemHelper("test test 2", null);
        items.addItemHelper("test test 3", null);

        assertFalse(items.checkForUnique("test test 2"));   //will return false as item already exists

        assertTrue(items.checkForUnique("test test 4"));    //will return true as item does not exist
    }

    @Test
    void validateDescriptionInput() {
        assertTrue(items.validateDescriptionInput("test test 2"));  //returns true because it meets requirements
        assertFalse(items.validateDescriptionInput(""));    //returns false because it does not meet requirements
    }

    @Test
    void verifyDate() {
        LocalDate testDate1 = items.verifyDate("2014-12-10");   //valid format will return date
        assertEquals(LocalDate.parse("2014-12-10"), testDate1);

        LocalDate testDate2 = items.verifyDate("24-2012-10");   //invalid format will return null
        assertNull(testDate2);
    }
}