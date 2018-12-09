package com.example.ibo.musicplayerofficial.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ibo.musicplayerofficial.R;

public class CollectionFragment extends Fragment {

    Button favoriteBtn, historyBtn, playlistBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_collection, container, false);

        //Find views
        favoriteBtn = view.findViewById(R.id.nav_favorites);
        historyBtn = view.findViewById(R.id.nav_history);
        playlistBtn = view.findViewById(R.id.nav_playlist);

        //Favorite button listener
        favoriteBtn.setOnClickListener(new OnFavoritesClick());

        return view;
    }

    private class OnFavoritesClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            insertNestedFragment();
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Favorites");
        }
    }

    //TODO: Work on this method later (low priority)
    private class OnHistoryClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }

    //TODO: Work on this method later (low priority)
    private class OnPlaylistClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

        }
    }


    // Embeds the child fragment dynamically
    private void insertNestedFragment() {
        Fragment childFragment = new FavoriteFragment();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.collection_container, childFragment)
                .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                .commit();

    }
}
