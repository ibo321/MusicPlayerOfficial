package com.example.ibo.musicplayerofficial.Classes;

public class Song {

    //Declare variables
    String artist, songName;
    int song, artistImg;

    //Constructor
    public Song(String artist, String songName, int song, int artistImg) {
        this.artist = artist;
        this.songName = songName;
        this.song = song;
        this.artistImg = artistImg;
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
