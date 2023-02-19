package com.arkadius.notepadapp.listener;

import androidx.cardview.widget.CardView;

import com.arkadius.notepadapp.model.Note;

public interface NoteClickListener {
    void onClick(Note note);
    void onLongClick(Note note, CardView cardView);

}
