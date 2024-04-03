package com.diary.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "diary_entries")
public class DiaryEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String date;

    private String time;

    private int emotion;

    private String title;

    private String[] imageLinks;

    private String content;

    public DiaryEntry() {
    }

    public DiaryEntry(int id, String date, String time, int emotion, String title, String[] imageLinks, String content) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.emotion = emotion;
        this.title = title;
        this.imageLinks = imageLinks;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getEmotion() {
        return emotion;
    }

    public void setEmotion(int emotion) {
        this.emotion = emotion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(String[] imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
