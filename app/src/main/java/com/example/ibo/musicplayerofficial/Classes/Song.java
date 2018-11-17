package com.example.ibo.musicplayerofficial.Classes;

public class Song {

    String artist, songName;
    int song;

    public Song(String artist, String songName, int song) {
        this.artist = artist;
        this.songName = songName;
        this.song = song;
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
