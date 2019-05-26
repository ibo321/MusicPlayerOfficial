package com.example.ibo.musicplayerofficial.Classes;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Ibrahim Delice on 09,March,2019
 */

@Entity(tableName = "album_table", foreignKeys = @ForeignKey(entity = Song.class, parentColumns = "id", childColumns = "songId"))
public class Album {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int songId;
    private String album_name;
    private String created;
    private String no_of_songs;

    public Album(int songId, String album_name, String created, String no_of_songs) {
        this.songId = songId;
        this.album_name = album_name;
        this.created = created;
        this.no_of_songs = no_of_songs;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getNo_of_songs() {
        return no_of_songs;
    }

    public void setNo_of_songs(String no_of_songs) {
        this.no_of_songs = no_of_songs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
