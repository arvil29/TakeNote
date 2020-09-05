package com.example.multi_note;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class AddNote extends AppCompatActivity {
    Toolbar toolbar;
    EditText title;
    EditText details;
    Calendar calendar;
    String dateToday;
    String timeNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //links to this activity when plus button is clicked
        setContentView(R.layout.activity_add_note);

        //brings in toolbar
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("New Note");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));

        //setting up back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title = findViewById(R.id.title);
        details = findViewById(R.id.details);
        details.setMovementMethod(new ScrollingMovementMethod());

        //to make title actively change with edit
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0) {
                    getSupportActionBar().setTitle(s);
                }
                else {
                    getSupportActionBar().setTitle("New Note");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        //get current date and time
        calendar = Calendar.getInstance(TimeZone.getDefault());
        dateToday =  pad(calendar.get(Calendar.DAY_OF_MONTH)) + "-" + calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                + "-" + calendar.get(Calendar.YEAR);
        timeNow = pad(calendar.get(Calendar.HOUR)) + ":" + pad(calendar.get(Calendar.MINUTE));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.save) {
            if (title.getText().length() != 0) {
                Note note = new Note(title.getText().toString(), details.getText().toString(),
                        dateToday, timeNow);

                NoteDatabase db = new NoteDatabase(this);
                long ID = db.addNote(note);

                Note check = db.getNote(ID);
                Log.d("Inserted", "Note: " + ID + " -> Title: " + check.getTitle() + " Date: " + check.getDate());
                goToMain();
                Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
            }
            else {
                title.setError("Title cannot be blank!");
            }
        }


        return super.onOptionsItemSelected(item);
    }

    private String pad(int i) {
        if(i < 10) {
            return "0" + i;
        }
        return String.valueOf(i);
    }

    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



}
