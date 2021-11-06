package baseline;

import java.time.LocalDate;

public class Item {
    private String itemDescription;
    private LocalDate itemDueDate;
    private boolean itemComplete;
    private int itemId;

    private static int count = 0;



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
        this.itemComplete = false;
        this.initItemId();
    }

    public int getItemId() {
        return itemId;
    }

    public static void incrementCount() {
        count++;
    }

    public void initItemId() {
        incrementCount();
        this.itemId = count;
    }

    @Override
    public String toString() {
        if (this.getItemDueDate() != null)
            return "At: " + this.getItemDueDate() + " " + this.getItemDescription() + " " + getItemId() + " " + (this.itemComplete ? "complete" : "incomplete");
        else {
            return this.getItemDescription()+ " " + getItemId()+ " " + (this.itemComplete ? "complete" : "incomplete");
        }
    }
}
