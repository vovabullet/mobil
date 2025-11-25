package ru.rut.lab1.data.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Character {
    @SerializedName("name")
    private String name;

    @SerializedName("culture")
    private String culture;

    @SerializedName("born")
    private String born;

    @SerializedName("titles")
    private List<String> titles;

    @SerializedName("aliases")
    private List<String> aliases;

    @SerializedName("playedBy")
    private List<String> playedBy;

    // Геттеры
    public String getName() { return name; }
    public String getCulture() { return culture; }
    public String getBorn() { return born; }
    public List<String> getTitles() { return titles; }
    public List<String> getAliases() { return aliases; }
    public List<String> getPlayedBy() { return playedBy; }
}