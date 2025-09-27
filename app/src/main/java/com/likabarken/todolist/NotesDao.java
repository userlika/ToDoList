package com.likabarken.todolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

// DAO - Data Access Objects
@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();
    // Метод, который возвращает коллекцию данных в Dao должен иметь интерфейсный тип List.
    // Здесь нельзя указывать конкретную реализацию.

    @Insert(onConflict = OnConflictStrategy.ABORT)
    // Completable - класс из библиотеки RxJava3, на этот объект можно подписываться
    Completable add(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    Completable remove(int id);

}
