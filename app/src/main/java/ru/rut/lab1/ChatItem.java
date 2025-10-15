package ru.rut.lab1;

/**
 * Класс для хранения данных элемента чата.
 * Содержит информацию об отправителе, сообщении, времени и иконке профиля.
 */
public class ChatItem {

    // Имя отправителя сообщения
    String sender;
    
    // Текст последнего сообщения
    String message;
    
    // Время отправки сообщения
    String time;
    
    // ID ресурса иконки профиля
    int profileIcon;

    /**
     * Конструктор для создания элемента чата
     * @param sender имя отправителя
     * @param message текст сообщения
     * @param time время отправки
     * @param profileIcon ID ресурса иконки профиля
     */
    public ChatItem(String sender, String message, String time, int profileIcon) {
        this.sender = sender;
        this.message = message;
        this.time = time;
        this.profileIcon = profileIcon;
    }

    /**
     * Получить имя отправителя
     * @return имя отправителя
     */
    public String getSender() {
        return sender;
    }
    
    /**
     * Получить текст сообщения
     * @return текст сообщения
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Получить время отправки
     * @return время отправки
     */
    public String getTime() {
        return time;
    }
    
    /**
     * Получить ID ресурса иконки профиля
     * @return ID ресурса иконки
     */
    public int getProfileIcon() {
        return profileIcon;
    }
}
