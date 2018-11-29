package com.example.ibo.musicplayerofficial.Fragments;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.R;

public class SongFragment extends Fragment {


    TextView songNameVIEW;
    ImageView artistImgVIEW, playBVIEW;
    MediaPlayer mediaPlayer;
    Song currentSong;
    String songName;
    int song, artistImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        //find views
        songNameVIEW = (TextView) view.findViewById(R.id.songNameTxt);
        artistImgVIEW = (ImageView) view.findViewById(R.id.artistImgDetail);
        playBVIEW = (ImageView) view.findViewById(R.id.playBDetail);

        //set the details of the song i clicked on in my list
        //Method is implemented in MainFragment.java (inside listViewOnClick)
        playBVIEW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //WORK ON THIS METHOD!!
                if (currentSong == null) {
                    mediaPlayer = MediaPlayer.create(getActivity(), song);
                }

                if (mediaPlayer != null) {

                    mediaPlayer.start();
                    playBVIEW.setImageResource(R.drawable.pause_black);
                } else {
                    mediaPlayer.pause();
                    playBVIEW.setImageResource(R.drawable.play_black);
                }
            }
        });

        //set the values to the correct singer
        artistImgVIEW.setImageResource(artistImg);
        songNameVIEW.setText(songName);

        return view;
    }

    //Get song details of  the song clicked on
    //refer to MainFragment for the implementation
    public void getSongDetails(String songName, int artistImg, int song) {
        this.artistImg = artistImg;
        this.song = song;
        this.songName = songName;
    }
}
