package com.example.ibo.musicplayerofficial.Interfaces;

import com.example.ibo.musicplayerofficial.Classes.Artist;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;

/**
 * Created by Ibrahim Delice on 09,March,2019
 */
@Dao
public interface ArtistDao {

    @Insert
    long insertId(Artist id);

    @Insert
    void insert(Artist... artists);

    @Delete
    void deleteArtist(Artist artist);

    //
    //    @Update
    //    void updateArtist(Artist artist);
    //
    //    @Query("DELETE FROM artist_table")
    //    void deleteAllArtist();
    //
    //    @Query("SELECT * FROM artist_table ORDER BY artist_name DESC")
    //    LiveData<List<Artist>> getAllArtist();

    //    @Query("SELECT artist_name, artist_image, artist_born FROM artist_table")
    //    LiveData<List<Artist>> getArtistBySongId();
}
