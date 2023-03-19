package com.arkadius.notepadapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arkadius.notepadapp.R;
import com.arkadius.notepadapp.listener.NoteClickListener;
import com.arkadius.notepadapp.model.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NoteListAdapter extends RecyclerView.Adapter<NotesViewHolder> {

    Context context;
    List<Note> list;
    NoteClickListener listener;

    public NoteListAdapter(Context context, List<Note> list, NoteClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }



    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_list, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_title.setSelected(true);
        holder.textView_content.setText(list.get(position).getContent());
        holder.textView_date.setText(list.get(position).getDate());
        holder.textView_date.setSelected(true);

        if (list.get(position).isPinned()){
            holder.imageView_pin.setImageResource(R.drawable.ic_pin);
        } else {

            holder.imageView_pin.setImageResource(0);

        }

        int color_code = getRandomColor();

        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));



        holder.notes_container.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                listener.onClick(list.get(holder.getAdapterPosition()));

            }
        });

        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener(){


            @Override
            public boolean onLongClick(View view) {
                listener.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_container);
                return true;
            }
        });
    }

    // it selects a random color for each note
    private int getRandomColor(){

    List<Integer> colorCode = new ArrayList<>();

        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color5);

        Random random = new Random();
        int random_Color = random.nextInt(colorCode.size());

        return colorCode.get(random_Color);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterNotes (List<Note> filteredNotes){
        list = filteredNotes;
        notifyDataSetChanged();
    }

}



