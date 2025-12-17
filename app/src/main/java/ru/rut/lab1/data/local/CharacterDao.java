package ru.rut.lab1.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

/**
 * CRUD
 */
@Dao
public interface CharacterDao {

    // реактивная подписка на изменения таблицы, будет автоматически пушить обновления в UI
    @Query("SELECT * FROM characters WHERE batchNumber = :batchNumber ORDER BY characterId ASC")
    Flowable<List<CharacterEntity>> observeByBatch(int batchNumber);

    @Query("SELECT COUNT(*) FROM characters WHERE batchNumber = :batchNumber")
    Single<Integer> countByBatch(int batchNumber);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(List<CharacterEntity> items);

    @Query("DELETE FROM characters WHERE batchNumber = :batchNumber")
    Completable deleteByBatch(int batchNumber);
}