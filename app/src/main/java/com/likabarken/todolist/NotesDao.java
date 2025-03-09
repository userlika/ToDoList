package com.likabarken.todolist;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

// DAO - Data Access Objects
@Dao
public interface NotesDao {

    @Query("SELECT * FROM notes")
    List<Note> getNotes();
    // Метод, который возвращает коллекцию данных в Dao должен иметь интерфейсный тип List.
    // Здесь нельзя указывать конкретную реализацию.

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void add(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    void remove(int id);

}
