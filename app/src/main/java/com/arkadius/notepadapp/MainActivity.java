package com.arkadius.notepadapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.SearchView;
import android.widget.Toast;

import com.arkadius.notepadapp.adapter.NoteListAdapter;
import com.arkadius.notepadapp.db.NotepadDB;
import com.arkadius.notepadapp.listener.NoteClickListener;
import com.arkadius.notepadapp.model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    SearchView searchView;
    NoteListAdapter noteListAdapter;
    List<Note> notes = new ArrayList<>();
    NotepadDB database;

    Note selectedNote;

    FloatingActionButton fab_add;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        searchView = findViewById(R.id.search_view);

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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return true;
            }


        });


    }

    private void filter(String s) {
        List<Note> filteredList = new ArrayList<>();
        for(Note note : notes){
            if(note.getTitle().toLowerCase().contains(s.toLowerCase()) || note.getContent().toLowerCase().contains(s.toLowerCase() )){
                filteredList.add(note);
            }
        }

        noteListAdapter.filterNotes(filteredList);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 101){
            if(resultCode == Activity.RESULT_OK){
                assert data != null;
                Note new_note = (Note) data.getSerializableExtra("note");
                database.noteDao().insert(new_note);
                notes.clear();
                notes.addAll(database.noteDao().getAll());
                noteListAdapter.notifyDataSetChanged();
                //recreate();

            }

        } else if(requestCode == 102){
            if(resultCode == Activity.RESULT_OK){
                assert data != null;
                Note new_note = (Note) data.getSerializableExtra("note");
                database.noteDao().update(new_note.getId(), new_note.getTitle(), new_note.getContent());
                notes.clear();
                notes.addAll(database.noteDao().getAll());
                noteListAdapter.notifyDataSetChanged();

                if (data.getBooleanExtra("searchClear", false)) {
                    searchView.setQuery("", false);
                    searchView.clearFocus();
                }

            }

        }

    }

    private void updateRecycler(List<Note> notes){

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));

        noteListAdapter = new NoteListAdapter(MainActivity.this, notes, noteClickListener);
        recyclerView.setAdapter(noteListAdapter);
        noteListAdapter.notifyDataSetChanged();


    }

    private final NoteClickListener noteClickListener = new NoteClickListener() {
        @Override
        public void onClick(Note note) {
            Intent intent = new Intent(MainActivity.this, NoteFormActivity.class);
            intent.putExtra("old_note", note);
            startActivityForResult(intent, 102);


        }

        @Override
        public void onLongClick(Note note, CardView cardView) {
            selectedNote = new Note();
            selectedNote = note;
            popupMenu(cardView);

        }
    };

    private void popupMenu(CardView cardView) {
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }



    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.pin:
                 if(selectedNote.isPinned()){
                    database.noteDao().pin(selectedNote.getId(), false);
                     Toast.makeText(this, "Unpinned", Toast.LENGTH_SHORT).show();
                     noteListAdapter.notifyDataSetChanged();

                 } else {
                     database.noteDao().pin(selectedNote.getId(), true);
                     Toast.makeText(this, "Pinned", Toast.LENGTH_SHORT).show();
                     noteListAdapter.notifyDataSetChanged();
                 }

                notes.clear();
                notes.addAll(database.noteDao().getAll());

                return true;

            case R.id.delete:
                database.noteDao().delete(selectedNote);
                notes.clear();
                notes.addAll(database.noteDao().getAll());
                noteListAdapter.notifyDataSetChanged();

                return true;

            default:
                return false;

        }

    }
}