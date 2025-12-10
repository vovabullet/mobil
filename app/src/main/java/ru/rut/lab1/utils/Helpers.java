package ru.rut.lab1.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

public class Helpers {

    private static final String PREFS_NAME = "app_settings";
    private static final String KEY_DARK_THEME = "dark_theme";

    /**
     * Применяет тему (тёмную или светлую)
     */
    public static void applyTheme(boolean isDarkTheme) {
        if (isDarkTheme) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate. setDefaultNightMode(AppCompatDelegate. MODE_NIGHT_NO);
        }
    }

    /**
     * Загружает настройку темы из SharedPreferences и применяет её
     */
    public static void applyThemeFromPrefs(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context. MODE_PRIVATE);
        boolean isDarkTheme = prefs.getBoolean(KEY_DARK_THEME, false);
        applyTheme(isDarkTheme);
    }
}
