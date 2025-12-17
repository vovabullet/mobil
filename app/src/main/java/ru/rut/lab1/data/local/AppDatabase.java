package ru.rut.lab1.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(
        entities = {CharacterEntity.class},
        version = 1,
        exportSchema = false
)
@TypeConverters({StringListConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    /**
     * один экземпляр на приложение
     */
    private static volatile AppDatabase INSTANCE;

    public abstract CharacterDao characterDao();

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "lab1.db"
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}