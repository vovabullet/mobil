package ru.rut.lab1;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Настройка RecyclerView
        RecyclerView recyclerView = findViewById(R.id.chat_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Фиктивные данные
        List<ChatItem> chatList = new ArrayList<>();
        chatList.add(new ChatItem("Алексей", "Привет, как дела?", "10:30", 0)); // вместо нуля должна быть R.drawable.ic_profile
        chatList.add(new ChatItem("Мария", "Увидимся завтра?", "11:00", 0));
        chatList.add(new ChatItem("Игорь", "Отправил файл", "09:15", 0));

        // Подключение адаптера
        ChatAdapter adapter = new ChatAdapter(chatList);
        recyclerView.setAdapter(adapter);
    }
}