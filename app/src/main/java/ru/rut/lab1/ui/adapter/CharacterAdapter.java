package ru.rut.lab1.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ru.rut.lab1.data.model.Character;
import ru.rut.lab1.databinding.ItemCharacterBinding;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private List<Character> characters = new ArrayList<>();

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
        notifyDataSetChanged();
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public String getCharactersAsText() {
        if (characters == null || characters.isEmpty()) {
            return "Нет данных для сохранения";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("=== Персонажи Game of Thrones ===\n");
        sb.append("Всего: ").append(characters.size()).append(" персонажей\n");
        sb.append("=====================================\n\n");

        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            sb.append("--- Персонаж #").append(i + 1).append(" ---\n");
            sb.append("Имя: ").append(c.getName().isEmpty() ? "Неизвестно" : c.getName()).append("\n");
            sb.append("Культура: ").append(c.getCulture()).append("\n");
            sb.append("Рождение: ").append(c.getBorn()).append("\n");
            sb.append("Титулы: ").append(String.join(", ", c.getTitles())).append("\n");
            sb.append("Прозвища: ").append(String.join(", ", c.getAliases())).append("\n");
            sb.append("Актёры: ").append(String.join(", ", c.getPlayedBy())).append("\n\n");
        }

        return sb.toString();
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCharacterBinding binding = ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
        );
        return new CharacterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        holder.bind(characters.get(position));
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    static class CharacterViewHolder extends RecyclerView.ViewHolder {
        private final ItemCharacterBinding binding;

        public CharacterViewHolder(@NonNull ItemCharacterBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(Character character) {
            binding.nameText.setText("Name: " + (character.getName().isEmpty() ? "Unknown" : character.getName()));
            binding.cultureText.setText("Culture: " + character.getCulture());
            binding.bornText.setText("Born: " + character.getBorn());
            binding.titlesText.setText("Titles: " + String.join(", ", character.getTitles()));
            binding.aliasesText.setText("Aliases: " + String.join(", ", character.getAliases()));
            binding.playedByText.setText("Played by: " + String.join(", ", character.getPlayedBy()));
        }
    }
}