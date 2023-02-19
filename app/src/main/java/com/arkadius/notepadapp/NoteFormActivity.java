package com.arkadius.notepadapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.arkadius.notepadapp.model.Note;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class NoteFormActivity extends AppCompatActivity {

    EditText editText_title, editText_content;
    ImageView imageView_save, imageView_back;
    Note note;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_form);

        imageView_save = findViewById(R.id.imageView_save);
        editText_title = findViewById(R.id.editText_title);
        editText_content = findViewById(R.id.editText_content);
        imageView_back = findViewById(R.id.imageView_back);


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

                note = new Note();
                note.setTitle(title);
                note.setContent(content);
                note.setDate(formatter.format(date));

                Intent intent = new Intent();
                intent.putExtra("note", note);
                setResult(Activity.RESULT_OK, intent);
                Toast.makeText(NoteFormActivity.this, "Note added!", Toast.LENGTH_LONG).show();
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