package ru.rut.lab1.data.local;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "characters")
public class CharacterEntity {

    @PrimaryKey
    public int characterId;

    public int batchNumber; // какой "номер набора" (для load more)

    public String name;
    public String culture;
    public String born;

    public List<String> titles;
    public List<String> aliases;
    public List<String> playedBy;

    public CharacterEntity(int characterId,
                           int batchNumber,
                           String name,
                           String culture,
                           String born,
                           List<String> titles,
                           List<String> aliases,
                           List<String> playedBy) {
        this.characterId = characterId;
        this.batchNumber = batchNumber;
        this.name = name;
        this.culture = culture;
        this.born = born;
        this.titles = titles;
        this.aliases = aliases;
        this.playedBy = playedBy;
    }
}