package com.diary.model;

public class Language {
    private int name;
    private int image;
    private String key;

    public Language(int name, int image, String key) {
        this.name = name;
        this.image = image;
        this.key = key;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
