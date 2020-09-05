package com.example.multi_note;

import java.util.Objects;

public class Note {
    private long ID;
    private String title;
    private String detail;
    private String date;
    private String time;

    Note() {

    }

    Note(String title, String detail, String date, String time) {
        this.title = title;
        this.detail = detail;
        this.date = date;
        this.time = time;
    }

    Note(long ID, String title, String detail, String date, String time) {
        this.ID = ID;
        this.title = title;
        this.detail = detail;
        this.date = date;
        this.time = time;
    }

    public long getID() {
        return ID;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
