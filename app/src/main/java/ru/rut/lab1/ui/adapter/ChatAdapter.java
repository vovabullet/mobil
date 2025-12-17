package ru.rut.lab1.ui.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.rut.lab1.data.model.ChatItem;
import ru.rut.lab1.databinding.ItemChatBinding;

/**
 * Адаптер для RecyclerView для отображения списка чатов.
 * Связывает данные ChatItem с элементами интерфейса.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {

    // Список элементов чата для отображения
    private final List<ChatItem> chatList;

    /**
     * Конструктор адаптера
     * @param chatList список элементов чата
     */
    public ChatAdapter(List<ChatItem> chatList) {
        this.chatList = chatList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemChatBinding binding = ItemChatBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(chatList.get(position));
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    /**
     * ViewHolder на ViewBinding.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ItemChatBinding binding;

        public ViewHolder(ItemChatBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ChatItem chat) {
            binding.senderName.setText(chat.getSender());
            binding.lastMessage.setText(chat.getMessage());
            binding.time.setText(chat.getTime());
            binding.profileIcon.setImageResource(chat.getProfileIcon());
        }
    }
}