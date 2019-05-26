package com.example.ibo.musicplayerofficial.Interfaces;

import com.example.ibo.musicplayerofficial.Classes.Artist;
import com.example.ibo.musicplayerofficial.Classes.Song;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SongDao {

    @Insert
    void insertSong(Song... song);

    @Insert
    long insertSongId(Song id);

    @Delete
    void deleteSong(Song song);

    @Update
    void updateSong(Song song);

    @Query("SELECT * FROM song_table WHERE isFavorite = 1")
    LiveData<List<Song>> getFavList();

    @Query("DELETE FROM song_table")
    void deleteAllSongs();

    @Query("SELECT * FROM song_table ORDER BY genre DESC")
    LiveData<List<Song>> getAllSongs();

}