package com.arkadius.notepadapp.dao;




import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;


import com.arkadius.notepadapp.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Query("SELECT * FROM notes ORDER BY id DESC")
    List<Note> getAll();

    @Query("UPDATE notes SET title= :title, content = :content WHERE id = :id")
    void update(int id, String title, String  content);

    @Delete
    void delete(Note note);

}
