package ru.rut.lab1.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.rut.lab1.R;
import ru.rut.lab1.data.model.ChatItem;
import ru.rut.lab1.ui.adapter.ChatAdapter;

/**
 * Главный экран с чатом (фрагмент вместо Activity)
 */
public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Настройка RecyclerView для отображения списка чатов
        RecyclerView recyclerView = view.findViewById(R.id.chat_list);
        // менеджер компоновки (линейный список)
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        // фиктивные данные для демонстрации
        List<ChatItem> chatList = new ArrayList<>();
        chatList.add(new ChatItem("Алексей", "Привет, как дела?", "11:30", 0));
        chatList.add(new ChatItem("Мария", "Увидимся завтра?", "10:00", 0));
        chatList.add(new ChatItem("Игорь", "Отправил файл", "09:15", 0));

        //  адаптер для RecyclerView
        ChatAdapter adapter = new ChatAdapter(chatList);
        recyclerView.setAdapter(adapter);

        // Настройка кнопок навигации
        Button btnGoToTrones = view. findViewById(R.id.btn_go_to_trones);
        Button btnGoToSettings = view. findViewById(R.id.btn_go_to_settings);

        btnGoToTrones. setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_tronesFragment)
        );

        btnGoToSettings. setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_settingsFragment)
        );

        return view;
    }
}