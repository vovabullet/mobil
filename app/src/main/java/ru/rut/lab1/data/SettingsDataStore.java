package ru.rut.lab1.data;

import android.content.Context;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core. Preferences;
import androidx.datastore. preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex. rxjava3. core.Flowable;
import io.reactivex. rxjava3. core.Single;

public class SettingsDataStore {

    private static final String DATASTORE_NAME = "settings_datastore";

    // кллючи для DataStore
    public static final Preferences. Key<String> KEY_NICKNAME = PreferencesKeys.stringKey("nickname");
    public static final Preferences.Key<Boolean> KEY_NOTIFICATIONS = PreferencesKeys.booleanKey("notifications");

    private final RxDataStore<Preferences> dataStore;

    public SettingsDataStore(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, DATASTORE_NAME). build();
    }

    // Сохранение никнейма
    public Single<Preferences> saveNickname(String nickname) {
        return dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            mutablePreferences.set(KEY_NICKNAME, nickname);
            return Single.just(mutablePreferences);
        });
    }

    // Получение никнейма
    public Flowable<String> getNickname() {
        return dataStore.data().map(preferences -> {
            String nickname = preferences.get(KEY_NICKNAME);
            return nickname != null ? nickname : "User";
        });
    }

    // Сохранение настройки уведомлений
    public Single<Preferences> saveNotifications(boolean enabled) {
        return dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            mutablePreferences.set(KEY_NOTIFICATIONS, enabled);
            return Single. just(mutablePreferences);
        });
    }

    // Получение настройки уведомлений
    public Flowable<Boolean> getNotifications() {
        return dataStore.data().map(preferences -> {
            Boolean notifications = preferences.get(KEY_NOTIFICATIONS);
            return notifications != null ? notifications : true;
        });
    }
}