package com.example.ibo.musicplayerofficial.ViewModel;

import android.app.Application;

import com.example.ibo.musicplayerofficial.Classes.Artist;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.Repository.SongRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class SharedViewModel extends AndroidViewModel {

    private LiveData<List<Song>> allSongs;
    private LiveData<List<Song>> favSongs;
    private MediatorLiveData<Song> clickedSong;
    private SongRepository repository;

    public SharedViewModel(@NonNull Application application) {
        super(application);
        repository = new SongRepository(application);

        allSongs = repository.getAllSongs();
        favSongs = repository.getFavList();
    }

    public void insertArtist(Artist artist) {
        repository.inserArtist(artist);
    }

    public void insert(Song song) {
        repository.insert(song);
    }

    public void delete(Song song) {
        repository.delete(song);
    }

    public LiveData<List<Song>> getFavSongs() {
        return favSongs;
    }

    public LiveData<List<Song>> getAllSongs() {
        return allSongs;
    }

    public void update(Song song) {
        repository.update(song);
    }

    public MediatorLiveData<Song> setClickedSong(Song song) {
        if (clickedSong == null) {
            clickedSong = new MediatorLiveData<>();
        }
        clickedSong.setValue(song);
        return clickedSong;
    }

    public MediatorLiveData<Song> getClickedSong() {
        return clickedSong;
    }
}