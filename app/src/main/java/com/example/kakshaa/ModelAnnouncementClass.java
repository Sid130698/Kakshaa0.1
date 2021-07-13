package com.example.kakshaa;

public class ModelAnnouncementClass {
    String Message,Name,Title;
    ModelAnnouncementClass(){

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public ModelAnnouncementClass(String name, String message, String title) {
        Name = name;
        Message = message;
        Title=title;

    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
