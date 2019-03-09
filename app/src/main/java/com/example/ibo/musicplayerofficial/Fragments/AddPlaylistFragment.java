package com.example.ibo.musicplayerofficial.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ibo.musicplayerofficial.Classes.Playlist;
import com.example.ibo.musicplayerofficial.R;
import com.example.ibo.musicplayerofficial.ViewModel.SharedViewModel;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

public class AddPlaylistFragment extends DialogFragment {

    private EditText title;
    private EditText genre;
    private Button doneBtn;
    PlaylistFragment playlistFragment;
    SharedViewModel viewModel;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_playlist, container, false);


        title = view.findViewById(R.id.addPlaylist_addPlaylistTitle);
        genre = view.findViewById(R.id.addPlaylist_addGenre);
        doneBtn = view.findViewById(R.id.addPlaylist_doneBtn);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        final LocalDate localDate = LocalDate.now();

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleTxt = title.getText().toString().trim();
                String genreTxt = genre.getText().toString().trim();

                playlistFragment = new PlaylistFragment();
                FragmentManager fragmentManager = getFragmentManager();

                Playlist playlist = new Playlist();
                playlist.setPlaylist_name(titleTxt);
                playlist.setPlaylist_genre(genreTxt);
                playlist.setCreated(localDate);
//                playlist.setSongId(viewModel.getClickedSong());
//                viewModel.insertPlaylist(playlist);

                fragmentManager.beginTransaction().replace(R.id.fragment_container, playlistFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }

}
