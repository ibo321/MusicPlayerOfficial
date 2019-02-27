package com.example.ibo.musicplayerofficial.Classes;

import java.io.Serializable;

public class Song implements Serializable {

    //Declare variables
    private String artist, songName, lyrics, genre, song, artistImg;
    private boolean isFavorite;

    //Constructor
    public Song(String artist, String songName, String song, String artistImg, String lyrics, String genre) {
        this.artist = artist;
        this.songName = songName;
        this.song = song;
        this.artistImg = artistImg;
        this.lyrics = lyrics;
        this.genre = genre;
    }

    public Song() {

    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    public String getArtistImg() {
        return artistImg;
    }

    public void setArtistImg(String artistImg) {
        this.artistImg = artistImg;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSong() {
        return song;
    }

    public void setSong(String song) {
        this.song = song;
    }
}

