/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Nader Fares
 */

package baseline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.DateTimeException;
import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class ItemControllerMethods {

    private final ObservableList<Item> items;

    public ObservableList<Item> getItems() {
        return items;
    }

    public ItemControllerMethods() {
        items = FXCollections.observableArrayList();
    }
    //add item to list
    public void addItemHelper(String descriptionText, LocalDate dueDate) {
        items.add(new Item(descriptionText, dueDate)); //create object with description from textfield and date from date picker
    }

    public String addItem(String descriptionText, LocalDate dueDate) {
        if (!validateDescriptionInput(descriptionText)) {   //return errortype string if unsuccessful
            return "description";
        }
        if (!checkForUnique(descriptionText)) {
            return "unique";
        }
        addItemHelper(descriptionText, dueDate);    //add item to list
        return null;
    }

    public void clearItems() {
        items.clear();
    }       // deletes every item in the list

    public void deleteItem(int itemId) {
//        delete item with the item index
        items.removeIf(item -> item.getItemId() == itemId);
    }

    public void editItem(int itemId, String descriptionText, LocalDate dueDate) {
        Optional<Item> tempItem = getItemById(itemId);
        tempItem.ifPresent(item -> {
            if (descriptionText.trim().length() != 0) {   //only change description if textfield is not empty
                if (descriptionText.trim().length() < 256) {
                    if (!checkForUnique(descriptionText))
                        return;
                    item.setItemDescription(descriptionText);
                } else new ErrorMap("description");
            }
            if (dueDate != null)             //only change due date if datepicker is not empty
                item.setItemDueDate(dueDate);
        });
    }

    public void markItem(int itemId) {
        Optional<Item> tempItem = getItemById(itemId);
        tempItem.ifPresent(Item::toggleItemComplete);
    }

    public Optional<Item> getItemById(int itemId) {
        return items.stream().filter(item -> item.getItemId() == itemId).findFirst();
    }

    public boolean checkForUnique(String descriptionText) {
        //if item with the same description and due date already exists, throw alert and block item from being added
        for (Item item : items) {
            if (descriptionText.equals(item.getItemDescription())) {
                return false;           //item already exists
            }
        }
        return true;    //item is unique
    }

    //if item does not meet description requirements, throw alert and block item from being added
    public boolean validateDescriptionInput(String descriptionText) {
        String trimmedInput = descriptionText.trim();
        return trimmedInput.length() >= 1 && trimmedInput.length() <= 256;  //true if meets requirements
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
}
