package com.example.ibo.musicplayerofficial.Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Created by Ibrahim Delice on 09,March,2019
 */

@Entity
public class Artist {

    @PrimaryKey(autoGenerate = true)
    private int artistId;
    @ColumnInfo(name = "artist_name")
    private String artist_name;
    @ColumnInfo(name = "artist_image")
    private String artist_image;
    @ColumnInfo(name = "artist_born")
    private String artist_born;

    public Artist(String artist_name, String artist_image, String artist_born) {
        this.artist_name = artist_name;
        this.artist_image = artist_image;
        this.artist_born = artist_born;
    }

    public Artist() {

    }

    public int getArtistId() {
        return artistId;
    }

    public void setArtistId(int artistId) {
        this.artistId = artistId;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getArtist_image() {
        return artist_image;
    }

    public void setArtist_image(String artist_image) {
        this.artist_image = artist_image;
    }

    public String getArtist_born() {
        return artist_born;
    }

    public void setArtist_born(String artist_born) {
        this.artist_born = artist_born;
    }
}