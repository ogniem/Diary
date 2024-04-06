package com.diary.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "diary_entries")
public class DiaryEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String timeCreate;

    private int emotion;

    private String title;

    private String imageLink1;
    private String imageLink2;
    private String imageLink3;

    private String content;

    public DiaryEntry() {
    }

    public DiaryEntry(String timeCreate, int emotion, String title, String imageLink1, String imageLink2, String imageLink3, String content) {
        this.timeCreate = timeCreate;
        this.emotion = emotion;
        this.title = title;
        this.imageLink1 = imageLink1;
        this.imageLink2 = imageLink2;
        this.imageLink3 = imageLink3;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(String timeCreate) {
        this.timeCreate = timeCreate;
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

    public String getImageLink1() {
        return imageLink1;
    }

    public void setImageLink1(String imageLink1) {
        this.imageLink1 = imageLink1;
    }

    public String getImageLink2() {
        return imageLink2;
    }

    public void setImageLink2(String imageLink2) {
        this.imageLink2 = imageLink2;
    }

    public String getImageLink3() {
        return imageLink3;
    }

    public void setImageLink3(String imageLink3) {
        this.imageLink3 = imageLink3;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
