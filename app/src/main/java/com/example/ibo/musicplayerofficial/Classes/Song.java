package com.example.ibo.musicplayerofficial.Classes;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "song_table")
public class Song {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private long song_artistId;
    private String songName;
    private String lyrics;
    private String genre;
    private String song;
    private boolean isFavorite;

    @Embedded(prefix = "artist_")
    private Artist artist;

    public Song(long song_artistId, Artist artist, String songName, String lyrics, String genre, String song) {
        this.song_artistId = song_artistId;
        this.artist = artist;
        this.songName = songName;
        this.lyrics = lyrics;
        this.genre = genre;
        this.song = song;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public Song() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getSong_artistId() {
        return song_artistId;
    }

    public void setSong_artistId(long artistId) {
        this.song_artistId = artistId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }


    //    @PrimaryKey(autoGenerate = true)
    //    private int id;
    //    private String artist;
    //    private String songName;
    //    private String lyrics;
    //    private String genre;
    //    private String song;
    //    private String artistImg;
    //    private boolean isFavorite;
    //
    //
    //    //Constructor
    //    public Song(String artist, String songName, String song, String artistImg, String lyrics, String genre) {
    //        this.artist = artist;
    //        this.songName = songName;
    //        this.song = song;
    //        this.artistImg = artistImg;
    //        this.lyrics = lyrics;
    //        this.genre = genre;
    //    }
    //
    //    public int getId() {
    //        return id;
    //    }
    //
    //    public void setId(int id) {
    //        this.id = id;
    //    }
    //
    //    public boolean isFavorite() {
    //        return isFavorite;
    //    }
    //
    //    public void setFavorite(boolean favorite) {
    //        isFavorite = favorite;
    //    }
    //
    //    public String getGenre() {
    //        return genre;
    //    }
    //
    //    public void setGenre(String genre) {
    //        this.genre = genre;
    //    }
    //
    //    public String getLyrics() {
    //        return lyrics;
    //    }
    //
    //    public void setLyrics(String lyrics) {
    //        this.lyrics = lyrics;
    //    }
    //
    //    public String getArtistImg() {
    //        return artistImg;
    //    }
    //
    //    public void setArtistImg(String artistImg) {
    //        this.artistImg = artistImg;
    //    }
    //
    //    public String getArtist() {
    //        return artist;
    //    }
    //
    //    public void setArtist(String artist) {
    //        this.artist = artist;
    //    }
    //
    //    public String getSongName() {
    //        return songName;
    //    }
    //
    //    public void setSongName(String songName) {
    //        this.songName = songName;
    //    }
    //
    //    public String getSong() {
    //        return song;
    //    }
    //
    //    public void setSong(String song) {
    //        this.song = song;
    //    }
}

