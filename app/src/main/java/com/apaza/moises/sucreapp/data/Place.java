package com.apaza.moises.sucreapp.data;

import android.support.annotation.Nullable;

import java.util.UUID;

public class Place {
    private String id;
    private String title;
    private String description;
    private String imageUrl;

    public Place(@Nullable String title,@Nullable String description) {
        this(title, description, null);
    }

    public Place(@Nullable String title,@Nullable String description, @Nullable String imageUrl) {
        id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isEmpty(){
        return (title == null || title.equals("")) && (description == null || description.equals(""));
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
