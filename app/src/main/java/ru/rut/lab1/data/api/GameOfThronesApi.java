package ru.rut.lab1.data.api;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.rut.lab1.data.model.Character;

public interface GameOfThronesApi {

    @GET("api/characters/{id}")
    Call<Character> getCharacter(@Path("id") int id);

    @GET("api/characters")
    Call<List<Character>> getCharacters(
            @Query("page") int page,
            @Query("pageSize") int pageSize
    );
}