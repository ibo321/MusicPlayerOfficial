package com.example.ibo.musicplayerofficial.Classes;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * Created by Ibrahim Delice on 16,March,2019
 */

@Entity(tableName = "songartist_table", primaryKeys = {"artistid", "songid"}, foreignKeys = {@ForeignKey(entity = Artist.class, parentColumns = "id", childColumns = "artistid"), @ForeignKey(entity = Song.class, parentColumns = "id", childColumns = "songid")})

public class SongArtist {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int artistid;
    private int songid;

    @Embedded
    Artist artist;
    @Embedded
    Song song;

    public SongArtist(int artistid, int songid, Artist artist, Song song) {
        this.artistid = artistid;
        this.songid = songid;
        this.artist = artist;
        this.song = song;
    }

    public int getArtistid() {
        return artistid;
    }

    public void setArtistid(int artistid) {
        this.artistid = artistid;
    }

    public int getSongid() {
        return songid;
    }

    public void setSongid(int songid) {
        this.songid = songid;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
