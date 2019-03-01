package com.example.ibo.musicplayerofficial;

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
    void insert(Song song);

    @Delete
    void delete(Song song);

    @Update
    void update (Song song);

    @Query("SELECT * FROM song_table WHERE isFavorite = 1")
    LiveData<List<Song>> getFavList();

    @Query("DELETE FROM song_table")
    void deleteAllSongs();

    @Query("SELECT * FROM song_table ORDER BY genre DESC")
    LiveData<List<Song>> getAllSongs();
}
