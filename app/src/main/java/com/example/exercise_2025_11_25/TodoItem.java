package com.example.exercise_2025_11_25;

import java.time.LocalDateTime;
import java.util.List;

public class TodoItem {
    static final boolean FINISHED = true;
    static final boolean IN_PROGRESS = false;
    private int itemID;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private List<ContactItem> relatedContacts;
    private boolean status;
    private boolean isSelected;

    public TodoItem(int itemID, String Title, String Description, LocalDateTime deadline) {
        this(itemID, Title, Description, deadline, IN_PROGRESS);
    }
    public TodoItem(int itemID, String Title, String Description , LocalDateTime deadline,Boolean Status) {
        this.itemID = itemID;
        title = Title;
        description = Description;
        this.deadline = deadline;
        status = Status;
    }

    public  String getTitle() {
        return title;
    }

    public void setTitle(String Title) {
        title = Title;
    }

    public  String getDescription() {
        return description;
    }

    public void setDescription(String Description) {
        description = Description;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean Status) {
        status = Status;
    }

    public LocalDateTime getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDateTime deadline) {
        this.deadline = deadline;
    }

    public int getItemID(){
        return itemID;
    }

    public void setItemID(int itemID){
        this.itemID = itemID;
    }
    public boolean isSelected() {
        return  isSelected;
    }
    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    public List<ContactItem> getRelatedContacts() {
        return  relatedContacts;
    }
    public void setRelatedContacts(List<ContactItem> relatedContacts) {
        this.relatedContacts = relatedContacts;
    }
}
