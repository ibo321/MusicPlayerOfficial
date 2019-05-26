package com.example.ibo.musicplayerofficial.Interfaces;

import com.example.ibo.musicplayerofficial.Classes.Playlist;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface PlaylistDAO {

    @Insert
    void insertPlaylist(Playlist playlist);

    @Delete
    void deletePlaylist(Playlist playlist);

    @Update
    void updatePlaylist(Playlist playlist);

    @Query("SELECT * FROM playlist_table ORDER BY created")
    LiveData<List<Playlist>> getPlaylists();

    @Query("DELETE FROM playlist_table")
    void deleteAllPlaylist();
}
