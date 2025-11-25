package ru.rut.lab1.ui.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import ru.rut.lab1.R;
import ru.rut.lab1.data.model.Character;

public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder> {
    private List<Character> characters = new ArrayList<>();

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_character, parent, false);
        return new CharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterViewHolder holder, int position) {
        Character character = characters.get(position);
        holder.bind(character);
    }

    @Override
    public int getItemCount() {
        return characters.size();
    }

    static class CharacterViewHolder extends RecyclerView.ViewHolder {
        private TextView nameText, cultureText, bornText, titlesText, aliasesText, playedByText;

        public CharacterViewHolder(@NonNull View itemView) {
            super(itemView);
            nameText = itemView.findViewById(R.id.nameText);
            cultureText = itemView.findViewById(R.id.cultureText);
            bornText = itemView.findViewById(R.id.bornText);
            titlesText = itemView.findViewById(R.id.titlesText);
            aliasesText = itemView.findViewById(R.id.aliasesText);
            playedByText = itemView.findViewById(R.id.playedByText);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Character character) {
            nameText.setText("Name: " + (character.getName().isEmpty() ? "Unknown" : character.getName()));
            cultureText.setText("Culture: " + character.getCulture());
            bornText.setText("Born: " + character.getBorn());
            titlesText.setText("Titles: " + String.join(", ", character.getTitles()));
            aliasesText.setText("Aliases: " + String.join(", ", character.getAliases()));
            playedByText.setText("Played by: " + String.join(", ", character.getPlayedBy()));
        }
    }
}