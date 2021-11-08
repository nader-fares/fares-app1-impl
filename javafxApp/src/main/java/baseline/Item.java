/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Nader Fares
 */

package baseline;

import java.time.LocalDate;

public class Item {
    private String itemDescription;
    private LocalDate itemDueDate;

    private boolean itemComplete;   //keep track of whether an item is complete or not
    private int itemId; //make sure every item has a unique id to be fetched

    private static int count = 0;   //static id to ensure no two items have the same id



    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public LocalDate getItemDueDate() {
        return itemDueDate;
    }

    public void setItemDueDate(LocalDate itemDueDate) {
        this.itemDueDate = itemDueDate;
    }

    public boolean isItemComplete() {
        return itemComplete;
    }

    public void setItemComplete(boolean itemComplete) {
        this.itemComplete = itemComplete;
    }

    public Item(String itemDescription, LocalDate itemDueDate) {
        this.itemDescription = itemDescription;
        this.itemDueDate = itemDueDate;
        this.itemComplete = false;  //                initialized incomplete
        this.initItemId();      //initialized id
    }

    public int getItemId() {
        return itemId;
    }

    private static void incrementCount() {
        count++;
    }   //increment count variable

    private void initItemId() {
        incrementCount();
        this.itemId = count;
    }

    public void toggleItemComplete() {
        this.setItemComplete(!this.isItemComplete());
    }
    //display object as string in listview
    @Override
    public String toString() {
        String returnString = "";
        if (this.getItemDueDate() != null)  //display date with formatting if it exists
            returnString = this.getItemDueDate() + " | ";
        returnString += getItemDescription();
        return returnString;
    }
}
