package ru.rut.lab1;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Главная Activity приложения с списком чатов.
 * Наследуется от BaseActivity для логирования жизненного цикла.
 */
public class HomeActivity extends BaseActivity {

    /**
     * Инициализация Activity при создании.
     * Настраивает RecyclerView для отображения списка чатов.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Устанавливаем тег для логирования
        TAG = "HomeActivity";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Настройка RecyclerView для отображения списка чатов
        RecyclerView recyclerView = findViewById(R.id.chat_list);
        // Устанавливаем менеджер компоновки (линейный список)
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Создаем фиктивные данные для демонстрации
        List<ChatItem> chatList = new ArrayList<>();
        chatList.add(new ChatItem("Алексей", "Привет, как дела?", "10:30", 0));
        chatList.add(new ChatItem("Мария", "Увидимся завтра?", "11:00", 0));
        chatList.add(new ChatItem("Игорь", "Отправил файл", "09:15", 0));

        // Создаем и устанавливаем адаптер для RecyclerView
        ChatAdapter adapter = new ChatAdapter(chatList);
        recyclerView.setAdapter(adapter);
    }
}