package ru.rut.lab1.data.local;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

/**
 * Room не умеет хранить List<String> напрямую, сохраняем их как JSON-строку
 */
public class StringListConverter {
    private static final Gson gson = new Gson();
    private static final Type listType = new TypeToken<List<String>>() {}.getType();

    @TypeConverter
    public static String fromList(List<String> value) {
        if (value == null) return "[]";
        return gson.toJson(value);
    }

    @TypeConverter
    public static List<String> toList(String value) {
        if (value == null || value.isEmpty()) return Collections.emptyList();
        return gson.fromJson(value, listType);
    }
}