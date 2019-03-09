package com.example.ibo.musicplayerofficial.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ibo.musicplayerofficial.Adapters.PlaylistAdapter;
import com.example.ibo.musicplayerofficial.R;
import com.example.ibo.musicplayerofficial.ViewModel.SharedViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PlaylistFragment extends Fragment {

    SharedViewModel viewModel;
FloatingActionButton floatingActionButton;
AddPlaylistFragment addPlaylistFragment;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        //        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Playlist");

        RecyclerView recyclerView = view.findViewById(R.id.playlist_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        PlaylistAdapter adapter  = new PlaylistAdapter();
        recyclerView.setAdapter(adapter);
        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);



        return view;
    }
}
