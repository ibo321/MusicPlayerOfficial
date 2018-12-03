package com.example.ibo.musicplayerofficial.Classes;

public class Song {

    //Declare variables
    String artist, songName, lyrics;
    int song, artistImg;

    //Constructor
    public Song(String artist, String songName, int song, int artistImg, String lyrics) {
        this.artist = artist;
        this.songName = songName;
        this.song = song;
        this.artistImg = artistImg;
        this.lyrics = lyrics;
    }

    public String getLyrics() {
        return lyrics;
    }

    public void setLyrics(String lyrics) {
        this.lyrics = lyrics;
    }

    //Get/Set methods
    public int getArtistImg() {
        return artistImg;
    }

    public void setArtistImg(int artistImg) {
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

    public int getSong() {
        return song;
    }

    public void setSong(int song) {
        this.song = song;
    }


}

