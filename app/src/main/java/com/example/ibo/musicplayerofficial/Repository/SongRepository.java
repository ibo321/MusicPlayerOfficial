package com.example.ibo.musicplayerofficial.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.ibo.musicplayerofficial.Classes.Artist;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.Database.SongDatabase;
import com.example.ibo.musicplayerofficial.Interfaces.ArtistDao;
import com.example.ibo.musicplayerofficial.Interfaces.SongDao;

import java.util.List;

import androidx.lifecycle.LiveData;

public class SongRepository {

    private SongDao songDao;
    private ArtistDao artistDao;

    private LiveData<List<Song>> allSongs;
    private LiveData<List<Song>> favSongs;

    public SongRepository(Application application) {
        SongDatabase database = SongDatabase.getInstance(application);

        songDao = database.songDao();
        artistDao = database.artistDao();

        allSongs = songDao.getAllSongs();
        favSongs = songDao.getFavList();
    }

    public void insert(Song song) {
        new InsertSongAsyncTask(songDao).execute(song);
    }

    public void update(Song song){
        new UpdateSongAsyncTask(songDao).execute(song);
    }

    public void delete(Song song) {
        new DeleteSongAsyncTask(songDao).execute(song);
    }

    public void inserArtist(Artist artist){
        new InsertArtistAsyncTask(artistDao).execute(artist);
    }

    public LiveData<List<Song>> getAllSongs() {
        return allSongs;
    }

    public LiveData<List<Song>> getFavList() {
        return favSongs;
    }

    //Insert #SONG to DB async
    private static class InsertArtistAsyncTask extends AsyncTask<Artist, Void, Void> {
        private ArtistDao artistDao;

        private InsertArtistAsyncTask(ArtistDao artistDao) {
            this.artistDao = artistDao;
        }

        @Override
        protected Void doInBackground(Artist... artists) {
            artistDao.insert(artists[0]);
            return null;
        }
    }


    //Insert #SONG to DB async
    private static class InsertSongAsyncTask extends AsyncTask<Song, Void, Void> {
        private SongDao songDao;

        private InsertSongAsyncTask(SongDao songDao) {
            this.songDao = songDao;
        }

        @Override
        protected Void doInBackground(Song... songs) {
            songDao.insertSong(songs[0]);
            return null;
        }
    }

    //Delete #SONG from DB async
    private static class DeleteSongAsyncTask extends AsyncTask<Song, Void, Void> {
        private SongDao songDao;

        private DeleteSongAsyncTask(SongDao songDao) {
            this.songDao = songDao;
        }

        @Override
        protected Void doInBackground(Song... songs) {
            songDao.deleteSong(songs[0]);
            return null;
        }
    }

    //Update #SONG from DB async
    private static class UpdateSongAsyncTask extends AsyncTask<Song, Void, Void> {
        private SongDao songDao;

        private UpdateSongAsyncTask(SongDao songDao) {
            this.songDao = songDao;
        }

        @Override
        protected Void doInBackground(Song... songs) {
            songDao.updateSong(songs[0]);
            return null;
        }
    }
}
