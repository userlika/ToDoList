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

    @Insert(onConflict = OnConflictStrategy.ABORT)
    void add(Note note);

    @Query("DELETE FROM notes WHERE id = :id")
    void remove(int id);

}
