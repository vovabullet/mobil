package ru.rut.lab1.data;

import android.content.Context;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core. Preferences;
import androidx.datastore. preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import io.reactivex. rxjava3. core.Flowable;
import io.reactivex. rxjava3. core.Single;

// TODO добавить пароль
public class SettingsDataStore {

    private static final String DATASTORE_NAME = "settings_datastore";

    // кллючи для DataStore
    public static final Preferences.Key<String> KEY_NICKNAME = PreferencesKeys.stringKey("nickname");
    public static final Preferences.Key<String> KEY_EMAIL = PreferencesKeys.stringKey("email");
    private final RxDataStore<Preferences> dataStore;

    public SettingsDataStore(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, DATASTORE_NAME). build();
    }

    // nickname

    public Single<Preferences> saveNickname(String nickname) {
        return dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            mutablePreferences.set(KEY_NICKNAME, nickname);
            return Single.just(mutablePreferences);
        });
    }

    public Single<String> getNickname() {
        return dataStore.data()
                .map(preferences -> {
                    String nickname = preferences.get(KEY_NICKNAME);
                    return nickname != null ? nickname : "";
                })
                .firstOrError();
    }

    // email

    public Single<Preferences> saveEmail(String email) {
        return dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            mutablePreferences. set(KEY_EMAIL, email);
            return Single.just(mutablePreferences);
        });
    }

    public Single<String> getEmail() {
        return dataStore.data()
                .map(preferences -> {
                    String email = preferences.get(KEY_EMAIL);
                    return email != null ?  email : "";
                })
                .firstOrError();
    }

    // both

    public Single<Preferences> saveUserData(String nickname, String email) {
        return dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            mutablePreferences.set(KEY_NICKNAME, nickname);
            mutablePreferences.set(KEY_EMAIL, email);
            return Single.just(mutablePreferences);
        });
    }
}