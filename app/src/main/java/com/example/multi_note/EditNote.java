package com.example.multi_note;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class EditNote extends AppCompatActivity {
    TextView title;
    TextView details;
    NoteDatabase db;
    Note note;
    Calendar calendar;
    String dateToday;
    String timeNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        //setting up back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        details = findViewById(R.id.details);
        title = findViewById(R.id.title);

        //intent to recieve ID that adapter class sent
        Intent intent = getIntent();
        Long ID = intent.getLongExtra("ID",0);

        //get current date and time
        calendar = Calendar.getInstance(TimeZone.getDefault());
        dateToday =  pad(calendar.get(Calendar.DAY_OF_MONTH)) + "-" + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                + "-" + calendar.get(Calendar.YEAR);
        timeNow = pad(calendar.get(Calendar.HOUR)) + ":" + pad(calendar.get(Calendar.MINUTE));

        db = new NoteDatabase(this);
        note = db.getNote(ID);
        getSupportActionBar().setTitle(note.getTitle());
        title.setText(note.getTitle());
        details.setText(note.getDetail());

        details.setMovementMethod(new ScrollingMovementMethod());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_del_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //updates note
        if(item.getItemId() == R.id.save) {
            if(title.getText().length() != 0) {
                note.setTitle(title.getText().toString());
                note.setDetail(details.getText().toString());
                note.setTime(timeNow);
                note.setDate(dateToday);

                int ID = db.editNote(note);

                Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            else {
                title.setError("Title cannot be blank!");
            }
        }
        //deletes note
        if(item.getItemId() == R.id.delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditNote.this);

            builder.setTitle("Delete");

            builder.setMessage("Are you sure?");

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.deleteNote(note.getID());
                    //Toast.makeText(this, "Note deleted!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });

            builder.setNegativeButton("Cancel", null);

            AlertDialog alert = builder.create();
            alert.show();

        }
        return super.onOptionsItemSelected(item);
    }

    private String pad(int i) {
        if(i < 10) {
            return "0" + i;
        }
        return String.valueOf(i);
    }
}
