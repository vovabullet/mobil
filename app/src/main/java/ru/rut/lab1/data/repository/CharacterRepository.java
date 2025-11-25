package ru.rut.lab1.data.repository;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.rut.lab1.data.api.RetrofitClient;
import ru.rut.lab1.data.model.Character;

public class CharacterRepository {
    private static final String TAG = "CharacterRepository";

    public interface CharacterCallback {
        void onSuccess(List<Character> characters);
        void onError(String message);
    }

    // Метод для загрузки персонажей по диапазону ID
    // вариант 6: ID 251-300
    public void loadCharacters(int startId, int endId, CharacterCallback callback) {
        List<Character> characters = new ArrayList<>();
        final int[] completedRequests = {0};
        final int totalRequests = endId - startId + 1;

        for (int id = startId; id <= endId; id++) {
            RetrofitClient.getApi().getCharacter(id).enqueue(new Callback<Character>() {
                @Override
                public void onResponse(Call<Character> call, Response<Character> response) {
                    completedRequests[0]++;

                    if (response.isSuccessful() && response.body() != null) {
                        synchronized (characters) {
                            characters.add(response.body());
                        }
                    }

                    if (completedRequests[0] == totalRequests) {
                        callback.onSuccess(characters);
                    }
                }

                @Override
                public void onFailure(Call<Character> call, Throwable t) {
                    completedRequests[0]++;
                    Log.e(TAG, "Error loading character: " + t.getMessage());

                    if (completedRequests[0] == totalRequests) {
                        if (characters.isEmpty()) {
                            callback.onError("Ошибка сети: " + t.getMessage());
                        } else {
                            callback.onSuccess(characters);
                        }
                    }
                }
            });
        }
    }
}