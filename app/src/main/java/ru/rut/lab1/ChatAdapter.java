package ru.rut.lab1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    private List<ChatItem> chatList;

    // Конструктор
    public ChatAdapter(List<ChatItem> chatList) {
        this.chatList = chatList;
    }

    // Создаёт ViewHolder для элемента списка
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_chat, parent, false);
        return new ViewHolder(view);
    }

    // Заполняет данные в элемент списка
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChatItem chat = chatList.get(position);
        holder.senderName.setText(chat.getSender());
        holder.lastMessage.setText(chat.getMessage());
        holder.time.setText(chat.getTime());
        holder.profileIcon.setImageResource(chat.getProfileIcon());
    }

    // Возвращает количество элементов
    @Override
    public int getItemCount() {
        return chatList.size();
    }

    // Класс для хранения ссылок на элементы View
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView profileIcon;
        public TextView senderName;
        public TextView lastMessage;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);
            profileIcon = itemView.findViewById(R.id.profile_icon);
            senderName = itemView.findViewById(R.id.sender_name);
            lastMessage = itemView.findViewById(R.id.last_message);
            time = itemView.findViewById(R.id.time);
        }
    }
}
