package com.example.ibo.musicplayerofficial.Classes;

import com.example.ibo.musicplayerofficial.TimeConverter;

import java.time.LocalDate;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity(tableName = "playlist_table", foreignKeys = @ForeignKey(entity = Song.class, parentColumns = "id", childColumns = "songId"))
public class Playlist {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @TypeConverters({TimeConverter.class})
    private LocalDate created;
    private String playlist_name;
    private String playlist_genre;
    private String playlist_songs;
    private int songId;

    public Playlist(LocalDate created, String playlist_name, String playlist_genre, String playlist_songs, int songId) {
        this.created = created;
        this.playlist_name = playlist_name;
        this.playlist_genre = playlist_genre;
        this.playlist_songs = playlist_songs;
        this.songId = songId;
    }

    public Playlist() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public String getPlaylist_name() {
        return playlist_name;
    }

    public void setPlaylist_name(String playlist_name) {
        this.playlist_name = playlist_name;
    }

    public String getPlaylist_genre() {
        return playlist_genre;
    }

    public void setPlaylist_genre(String playlist_genre) {
        this.playlist_genre = playlist_genre;
    }

    public String getPlaylist_songs() {
        return playlist_songs;
    }

    public void setPlaylist_songs(String playlist_songs) {
        this.playlist_songs = playlist_songs;
    }
}