package ru.rut.lab1;

public class ChatItem {

    String sender;
    String message;
    String time;
    int profileIcon;

    public ChatItem(String sender, String message, String time, int profileIcon) {
        this.sender = sender;
        this.message = message;
        this.time = time;
        this.profileIcon = profileIcon;
    }

    public String getSender() {
        return sender;
    }
    public String getMessage() {
        return message;
    }
    public String getTime() {
        return time;
    }
    public int getProfileIcon() {
        return profileIcon;
    }
}
