package com.example.ibo.musicplayerofficial.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibo.musicplayerofficial.R;

public class FavoriteFragment extends Fragment {

    TextView artist, songName;
    ImageView artistImg, playB, stopB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.songlist_customlayout, container, false);

        //Set arguments to new properties
        artist = view.findViewById(R.id.artistTxt);
        songName = view.findViewById(R.id.songNameTxt);
        artistImg = view.findViewById(R.id.artistImgBackgroundDetail);


        playB = view.findViewById(R.id.playB);
        stopB = view.findViewById(R.id.stopB);

        SongFragment songFragment = new SongFragment();


        //TODO: Fix this! Bundle is NULL
        if (songFragment.getArguments() != null) {

            int arg_artistImg = songFragment.getArguments().getInt("arg_artistImg");
            String arg_artist = songFragment.getArguments().getString("arg_artist");
            String arg_songName = songFragment.getArguments().getString("arg_songName");

            artistImg.setImageResource(arg_artistImg);
            artist.setText(arg_artist);
            songName.setText(arg_songName);

        } else {
            Toast.makeText(getActivity(), "Bundle is null", Toast.LENGTH_LONG).show();
        }

        return view;
    }
}
