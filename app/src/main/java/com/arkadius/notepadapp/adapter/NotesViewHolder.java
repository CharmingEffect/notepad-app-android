package com.arkadius.notepadapp.adapter;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.arkadius.notepadapp.R;

public class NotesViewHolder extends RecyclerView.ViewHolder {

    CardView notes_container;
    TextView textView_title, textView_content, textView_date;
    ImageView imageView_pin;

   public NotesViewHolder(@NonNull View itemView){
       super(itemView);
       notes_container = itemView.findViewById(R.id.notes_container);
       textView_title = itemView.findViewById(R.id.textView_title);
       textView_content = itemView.findViewById(R.id.textView_content);
       textView_date = itemView.findViewById(R.id.textView_date);
       imageView_pin = itemView.findViewById(R.id.imageView_pin);


   }
}
