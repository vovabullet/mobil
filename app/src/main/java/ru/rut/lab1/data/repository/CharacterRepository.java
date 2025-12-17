package ru.rut.lab1.data.repository;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Response;
import ru.rut.lab1.data.api.RetrofitClient;
import ru.rut.lab1.data.local.AppDatabase;
import ru.rut.lab1.data.local.CharacterDao;
import ru.rut.lab1.data.local.CharacterEntity;
import ru.rut.lab1.data.model.Character;

public class CharacterRepository {
    private final AppDatabase db;
    private final CharacterDao dao;

    public CharacterRepository(Context context) {
        this.db = AppDatabase.getInstance(context);
        this.dao = db.characterDao();
    }

    // наблюдаемые данные из Room
    public Flowable<List<Character>> observeCharacters(int batchNumber) {
        return dao.observeByBatch(batchNumber)
                .map(entities -> {
                    List<Character> out = new ArrayList<>();
                    for (CharacterEntity e : entities) {
                        out.add(entityToModel(e));
                    }
                    return out;
                });
    }

    // холодный старт
    public Completable warmStartIfEmpty(int batchNumber) {
        return dao.countByBatch(batchNumber)
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(count -> {
                    if (count != null && count > 0) {
                        return Completable.complete(); // данные уже есть в Room
                    }
                    // если пусто, идём в сеть и сохраняем
                    return refresh(batchNumber);
                });
    }

    // Обновить (всегда перезапросить и перезаписать batch)
    public Completable refresh(int batchNumber) {
        return loadFromNetwork(batchNumber)
                .subscribeOn(Schedulers.io())
                .flatMapCompletable(items -> replaceBatchTransactional(batchNumber, items));
    }

    // транзакция должна быть синхронной, выполняемой в одном потоке
    private Completable replaceBatchTransactional(int batchNumber, List<CharacterEntity> items) {
        return Completable.fromAction(() -> {
                    db.runInTransaction(() -> {
                        // синхронные операции через DAO без Rx
                        // блокирование вызовы Rx-методов
                        dao.deleteByBatch(batchNumber).blockingAwait();
                        dao.insertAll(items).blockingAwait();
                    });
                })
                .subscribeOn(Schedulers.io());
    }

    // загрузка из сети и преобразование к Entity
    private Single<List<CharacterEntity>> loadFromNetwork(int batchNumber) {
        // вариант 6: 251-300
        // "загрузить еще" как следующий диапазон по batchNumber:
        // batch 6 - 251-300
        // batch 7 - 301-350
        int startId = 251 + (batchNumber - 6) * 50;
        int endId = startId + 49;

        return Single.fromCallable(() -> {
            List<CharacterEntity> result = new ArrayList<>();
            for (int id = startId; id <= endId; id++) {
                Call<Character> call = RetrofitClient.getApi().getCharacter(id);
                Response<Character> response = call.execute(); // синхронно, но на io-потоке
                if (response.isSuccessful() && response.body() != null) {
                    Character c = response.body();
                    result.add(modelToEntity(id, batchNumber, c));
                }
            }
            return result;
        });
    }

    private static CharacterEntity modelToEntity(int id, int batchNumber, Character c) {
        return new CharacterEntity(
                id,
                batchNumber,
                c.getName(),
                c.getCulture(),
                c.getBorn(),
                c.getTitles(),
                c.getAliases(),
                c.getPlayedBy()
        );
    }

    private static Character entityToModel(CharacterEntity e) {
        return new Character(
                e.name,
                e.culture,
                e.born,
                e.titles,
                e.aliases,
                e.playedBy
        );
    }
}