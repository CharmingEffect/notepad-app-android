package com.arkadius.notepadapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.arkadius.notepadapp.adapter.NoteListAdapter;
import com.arkadius.notepadapp.db.NotepadDB;
import com.arkadius.notepadapp.listener.NoteClickListener;
import com.arkadius.notepadapp.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    NoteListAdapter noteListAdapter;
    List<Note> notes = new ArrayList<>();
    NotepadDB database;

    FloatingActionButton fab_add;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        fab_add = findViewById(R.id.fab_add);
        database = NotepadDB.getInstance(this);
        notes = database.noteDao().getAll();

        //https://youtu.be/Shh0N45S4hE?t=5775
        updateRecycler(notes);

        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, NoteFormActivity.class);
                    startActivityForResult(intent, 101);


            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            if(resultCode == Activity.RESULT_OK){
                Note new_note = (Note) data.getSerializableExtra("note");
                database.noteDao().insert(new_note);
                notes.clear();
                notes.addAll(database.noteDao().getAll());
                noteListAdapter.notifyDataSetChanged();

            }


        }
    }

    private void updateRecycler(List<Note> notes){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));

        noteListAdapter = new NoteListAdapter(MainActivity.this, notes, noteClickListener);
        recyclerView.setAdapter(noteListAdapter);

    }

    private final NoteClickListener noteClickListener = new NoteClickListener() {
        @Override
        public void onClick(Note note) {


        }

        @Override
        public void onLongClick(Note note, CardView cardView) {

        }
    };

}