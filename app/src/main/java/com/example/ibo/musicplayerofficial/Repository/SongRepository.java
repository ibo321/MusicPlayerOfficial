package com.example.ibo.musicplayerofficial.Repository;

import android.app.Application;
import android.os.AsyncTask;

import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.SongDao;
import com.example.ibo.musicplayerofficial.SongDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class SongRepository {

    private SongDao songDao;
    private LiveData<List<Song>> allSongs;
    private LiveData<List<Song>> favSongs;

    public SongRepository(Application application) {
        SongDatabase database = SongDatabase.getInstance(application);
        songDao = database.songDao();
        allSongs = songDao.getAllSongs();
        favSongs = songDao.getFavList();
    }

    public void update(Song song){
        new UpdateSongAsyncTask(songDao).execute(song);
    }
    public void insert(Song song) {
        new InsertNoteAsyncTask(songDao).execute(song);
    }

    public LiveData<List<Song>> getFavList() {
        return favSongs;
    }

    public void delete(Song song) {
        new DeleteSongAsyncTask(songDao).execute(song);
    }

    public LiveData<List<Song>> getAllSongs() {
        return allSongs;
    }

    //Insert data to DB async
    private static class InsertNoteAsyncTask extends AsyncTask<Song, Void, Void> {
        private SongDao songDao;

        private InsertNoteAsyncTask(SongDao songDao) {
            this.songDao = songDao;
        }

        @Override
        protected Void doInBackground(Song... songs) {
            songDao.insert(songs[0]);
            return null;
        }
    }

    //Delete data from DB async
    private static class DeleteSongAsyncTask extends AsyncTask<Song, Void, Void> {
        private SongDao songDao;

        private DeleteSongAsyncTask(SongDao songDao) {
            this.songDao = songDao;
        }

        @Override
        protected Void doInBackground(Song... songs) {
            songDao.delete(songs[0]);
            return null;
        }
    }

    //Update data from DB async
    private static class UpdateSongAsyncTask extends AsyncTask<Song, Void, Void> {
        private SongDao songDao;

        private UpdateSongAsyncTask(SongDao songDao) {
            this.songDao = songDao;
        }

        @Override
        protected Void doInBackground(Song... songs) {
            songDao.update(songs[0]);
            return null;
        }
    }
}
