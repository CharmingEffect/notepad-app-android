package com.arkadius.notepadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.arkadius.notepadapp.adapter.NoteListAdapter;
import com.arkadius.notepadapp.model.Note;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class NoteFormActivity extends AppCompatActivity {

    EditText editText_title, editText_content;
    ImageView imageView_save, imageView_back;
    TextView textView_save;

    Note note;


    SearchView searchView;
    boolean isOldNote = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form);

        imageView_save = findViewById(R.id.imageView_save);
        editText_title = findViewById(R.id.editText_title);
        editText_content = findViewById(R.id.editText_content);
        imageView_back = findViewById(R.id.imageView_back);
        textView_save = findViewById(R.id.textView_save);



        note = new Note();

       try {
              note = (Note) getIntent().getSerializableExtra("old_note");
              editText_title.setText(note.getTitle());
              editText_content.setText(note.getContent());
              isOldNote = true;
       } catch (Exception e) {
           e.printStackTrace();
       }

        imageView_save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String title = editText_title.getText().toString();
                String content = editText_content.getText().toString();

                if (content.isEmpty() ){
                    Toast.makeText(NoteFormActivity.this, "Note is empty!", Toast.LENGTH_LONG).show();
                    return;


                }

                SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy HH:mm a");
                Date date = new Date();

                if(!isOldNote){

                    note = new Note();
                    Toast.makeText(NoteFormActivity.this, "Note added!", Toast.LENGTH_LONG).show();


                } else {
                    Toast.makeText(NoteFormActivity.this, "Note updated!", Toast.LENGTH_LONG).show();

                }

                textView_save.setText(R.string.text_save);
                note.setTitle(title);
                note.setContent(content);
                note.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note", note);
                intent.putExtra("searchClear", true);
                setResult(Activity.RESULT_OK, intent);
                recreate();


                finish();



            }
        });


        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
