package ru.rut.lab1.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.rut.lab1.R;
import ru.rut.lab1.data.model.ChatItem;

/**
 * Адаптер для RecyclerView для отображения списка чатов.
 * Связывает данные ChatItem с элементами интерфейса.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    // Список элементов чата для отображения
    private List<ChatItem> chatList;

    /**
     * Конструктор адаптера
     * @param chatList список элементов чата
     */
    public ChatAdapter(List<ChatItem> chatList) {
        this.chatList = chatList;
    }

    /**
     * Создаёт ViewHolder для элемента списка
     * Вызывается при создании нового элемента списка
     * @param parent родительская ViewGroup
     * @param viewType тип представления
     * @return новый ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Создаем View из XML layout для элемента чата
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Заполняет данные в элемент списка
     * Вызывается для привязки данных к ViewHolder
     * @param holder ViewHolder для заполнения
     * @param position позиция элемента в списке
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // Получаем элемент чата по позиции
        ChatItem chat = chatList.get(position);
        // Заполняем данные в View элементы
        holder.senderName.setText(chat.getSender());
        holder.lastMessage.setText(chat.getMessage());
        holder.time.setText(chat.getTime());
        holder.profileIcon.setImageResource(chat.getProfileIcon());
    }

    /**
     * Возвращает количество элементов в списке
     * @return количество элементов
     */
    @Override
    public int getItemCount() {
        return chatList.size();
    }

    /**
     * Класс для хранения ссылок на элементы View
     * Паттерн ViewHolder для оптимизации производительности RecyclerView
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Ссылки на элементы интерфейса
        public ImageView profileIcon;   // Иконка профиля
        public TextView senderName;     // Имя отправителя
        public TextView lastMessage;    // Последнее сообщение
        public TextView time;           // Время сообщения

        /**
         * Конструктор ViewHolder
         * Инициализирует ссылки на элементы View
         * @param itemView корневой View элемента списка
         */
        public ViewHolder(View itemView) {
            super(itemView);
            // элементы по ID
            profileIcon = itemView.findViewById(R.id.profile_icon);
            senderName = itemView.findViewById(R.id.sender_name);
            lastMessage = itemView.findViewById(R.id.last_message);
            time = itemView.findViewById(R.id.time);
        }
    }
}
