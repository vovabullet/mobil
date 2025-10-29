package ru.rut.lab1.data.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.Serializable;

/**
 * Класс для хранения данных пользователя.
 * Реализует Serializable и Parcelable для передачи между Activity.
 */
public class User implements Serializable, Parcelable {
    
    // Поля пользователя
    private String name;      // Имя пользователя
    private String email;     // Адрес электронной почты
    private String password;  // Пароль

    /**
     * Конструктор для создания нового пользователя
     * @param name имя пользователя
     * @param email адрес электронной почты
     * @param password пароль
     */
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Геттеры для доступа к полям
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // Реализация Parcelable
    
    /**
     * Конструктор для восстановления объекта из Parcel
     */
    protected User(Parcel in) {
        name = in.readString();
        email = in.readString();
        password = in.readString();
    }

    /**
     * Creator для создания объекта User из Parcel
     */
    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    /**
     * Описание содержимого (обычно возвращает 0)
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Запись данных объекта в Parcel
     * @param dest Parcel для записи данных
     * @param flags дополнительные флаги
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
    }
}
