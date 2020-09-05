package com.example.multi_note;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    MainActivity mainAct;
    List<Note> notes;

    Adapter(MainActivity ma, List<Note> notes) {
        this.notes = notes;
        this.mainAct = ma;
    }

    @NonNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_listview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.ViewHolder holder, int i) {
        String  title    = notes.get(i).getTitle();
        String  date     = notes.get(i).getDate();
        String  time     = notes.get(i).getTime();
        long    ID       = notes.get(i).getID();
        Note n = notes.get(i);

        holder.title.setText(title);
        holder.date.setText(date);
        holder.time.setText(time);
        holder.ID.setText(String.valueOf(ID));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    //ViewHolder class
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView date;
        TextView time;
        TextView ID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView)itemView.findViewById(R.id.title);
            date = (TextView)itemView.findViewById(R.id.date);
            time = (TextView)itemView.findViewById(R.id.time);
            ID = (TextView)itemView.findViewById(R.id.ID);

            //intent that gets specific item's ID and sends to EditNote class to open
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), EditNote.class);
                    intent.putExtra("ID", notes.get(getAdapterPosition()).getID());
                    v.getContext().startActivity(intent);
                }
            });

        }

    }
}
