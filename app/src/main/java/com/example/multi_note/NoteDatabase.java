package com.example.multi_note;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "db7";
    private static final String DATABASE_TABLE = "table7";

    //column names for database table
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DETAIL = "detail";
    private static final String KEY_DATE = "date";
    private static final String KEY_TIME = "time";

    NoteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DATABASE_TABLE  +
                "(" + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_TITLE + " TEXT, " +
                KEY_DETAIL + " TEXT, " +
                KEY_DATE + " TEXT, " +
                KEY_TIME + " TEXT)";

        db.execSQL(query);
    }

    //if older version of DB exists then drop and make table with current version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) {
            return;
        }

        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    public long addNote(Note note) {
        //gets data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();

        //create map of values with column names as keys
        c.put(KEY_TITLE, note.getTitle());
        c.put(KEY_DETAIL, note.getDetail());
        c.put(KEY_DATE, note.getDate());
        c.put(KEY_TIME, note.getTime());

        //insert new row returning primary key
        long ID = db.insert(DATABASE_TABLE, null, c);
        Log.d("inserted", "ID -> " + ID);
        return ID;
    }

    public Note getNote(long ID) {
        //select * from databaseTable where id=1
        SQLiteDatabase db = this.getWritableDatabase();
        String[] query = new String[] {KEY_ID, KEY_TITLE, KEY_DETAIL, KEY_DATE, KEY_TIME};

        //cursor is pointer that points to specific row in database column
        Cursor cursor = db.query(DATABASE_TABLE, query,KEY_ID + "=?", new String[]{String.valueOf(ID)}, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }
        Note note = new Note(Long.parseLong(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4));

        return note;
    }

    public List<Note> getNotes() {
        List<Note> allNotes = new ArrayList<Note>();

        String query = "SELECT * FROM " + DATABASE_TABLE + " ORDER BY " + KEY_ID + " DESC";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);


        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setID(Long.parseLong(cursor.getString(0)));
                note.setTitle(cursor.getString(1));
                note.setDetail(cursor.getString(2));
                note.setDate(cursor.getString(3));
                note.setTime(cursor.getString(4));

                allNotes.add(note);
            }
            while (cursor.moveToNext());
        }

        return allNotes;
    }

    void deleteNote(long ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, KEY_ID + "=?", new String[]{String.valueOf(ID)});
        db.close();
    }

    public int editNote(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues c = new ContentValues();
        Log.d("Edited", "Edited Title: -> " + note.getTitle() + "\n ID -> " + note.getID());
        c.put(KEY_TITLE, note.getTitle());
        c.put(KEY_DETAIL, note.getDetail());
        c.put(KEY_DATE, note.getDate());
        c.put(KEY_TIME, note.getTime());

        return db.update(DATABASE_TABLE, c, KEY_ID + "=?", new String[] {String.valueOf(note.getID())});
    }


}
