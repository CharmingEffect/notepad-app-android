package com.arkadius.notepadapp.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "notes")
public class Note implements Serializable {


    @PrimaryKey(autoGenerate = true)
    private int Id = 0;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name="content")
    private String content;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name="pinned")
    boolean pinned = false;


    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPinned() {
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }
}
