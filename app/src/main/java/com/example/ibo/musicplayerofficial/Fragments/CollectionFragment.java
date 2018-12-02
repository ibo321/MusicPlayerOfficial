package com.example.ibo.musicplayerofficial.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ibo.musicplayerofficial.R;

public class CollectionFragment extends Fragment {

    Button favoriteBtn, historyBtn, playlistBtn;
    FavoriteFragment favoriteFragment;
    FragmentManager fragmentManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);

        //Actionbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Collections");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setBackgroundDrawable(AppCompatResources.getDrawable(getActivity(), R.drawable.orange));


        //Find views
        favoriteBtn = view.findViewById(R.id.favoriteBtn);
        historyBtn = view.findViewById(R.id.historyBtn);
        playlistBtn = view.findViewById(R.id.playlistBtn);

        //Favorite button listener
        favoriteBtn.setOnClickListener(new OnFavoritesClick());

        return view;
    }

    private class OnFavoritesClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            favoriteFragment = new FavoriteFragment();

            fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.collection_container, favoriteFragment).addToBackStack(null).commit();

        }
    }

    //TODO: Work on this method later (low priority)
    private class OnHistoryClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {

        }
    }

    //TODO: Work on this method later (low priority)
    private class OnPlaylistClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {

        }
    }
}
