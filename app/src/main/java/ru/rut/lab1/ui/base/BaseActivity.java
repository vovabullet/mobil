package ru.rut.lab1.ui.base;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Базовый класс для всех Activity приложения.
 * Содержит логирование всех методов жизненного цикла Activity.
 */
public abstract class BaseActivity extends AppCompatActivity {
    
    // Тег для логирования, будет переопределен в наследниках
    protected String TAG = "BaseActivity";

    /**
     * Вызывается при создании Activity.
     * Первый метод жизненного цикла, вызывается один раз.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate вызван");
    }

    /**
     * Вызывается после onCreate или onRestart.
     * Activity становится видимым для пользователя.
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart вызван");
    }

    /**
     * Вызывается после onStop, когда Activity возвращается на передний план.
     * Используется для восстановления состояния после остановки.
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart вызван");
    }

    /**
     * Вызывается после onStart или onPause.
     * Activity начинает взаимодействовать с пользователем.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume вызван");
    }

    /**
     * Вызывается когда Activity теряет фокус.
     * Используется для сохранения данных и остановки анимаций.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause вызван");
    }

    /**
     * Вызывается когда Activity больше не видим пользователю.
     * Используется для освобождения ресурсов.
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop вызван");
    }

    /**
     * Вызывается перед уничтожением Activity.
     * Последний метод жизненного цикла, вызывается один раз.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy вызван");
    }
}
