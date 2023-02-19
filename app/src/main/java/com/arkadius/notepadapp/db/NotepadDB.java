package com.arkadius.notepadapp.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.arkadius.notepadapp.dao.NoteDao;
import com.arkadius.notepadapp.model.Note;

@Database(entities = Note.class, version = 1, exportSchema = false)
public abstract class NotepadDB  extends RoomDatabase {
    private static NotepadDB database;


    private static String DATABASE_NAME = "Notepad";

    public synchronized static NotepadDB getInstance(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context.getApplicationContext(), NotepadDB.class, DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();

        }


        return database;
    }



    public abstract NoteDao noteDao();

}
