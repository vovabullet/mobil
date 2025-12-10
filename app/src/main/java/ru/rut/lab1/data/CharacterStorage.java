package ru.rut. lab1.data;

import java.util.ArrayList;
import java. util.List;
import ru.rut.lab1.data.model.Character;

// Простое хранилище данных (Singleton)
public class CharacterStorage {

    private static CharacterStorage instance;
    private List<Character> characters = new ArrayList<>();

    private CharacterStorage() {}

    public static CharacterStorage getInstance() {
        if (instance == null) {
            instance = new CharacterStorage();
        }
        return instance;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
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
        sb. append("Всего: ").append(characters.size()).append(" персонажей\n");
        sb.append("=====================================\n\n");

        for (int i = 0; i < characters.size(); i++) {
            Character c = characters.get(i);
            sb.append("--- Персонаж #").append(i + 1).append(" ---\n");
            sb.append("Имя: ").append(c.getName().isEmpty() ? "Неизвестно" : c.getName()).append("\n");
            sb.append("Культура: "). append(c.getCulture()).append("\n");
            sb.append("Рождение: "). append(c.getBorn()).append("\n");
            sb.append("Титулы: ").append(String.join(", ", c.getTitles())).append("\n");
            sb.append("Прозвища: ").append(String.join(", ", c.getAliases())).append("\n");
            sb.append("Актёры: "). append(String.join(", ", c. getPlayedBy())).append("\n");
            sb.append("\n");
        }

        return sb.toString();
    }

    public boolean hasData() {
        return characters != null && ! characters.isEmpty();
    }
}