package com.example.ibo.musicplayerofficial.Fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ibo.musicplayerofficial.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MainPlaylistFragment extends DialogFragment {

    TextView addPlaylist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_addplaylist, container, false);

        addPlaylist = view.findViewById(R.id.mainFrag_playlist_add);

        addPlaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog fbDialogue = new Dialog(getActivity(), android.R.style.Theme_Black_NoTitleBar);
                fbDialogue.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(100, 0, 0, 0)));
                fbDialogue.setContentView(R.layout.fragment_add_playlist);
                fbDialogue.setCancelable(true);

                fbDialogue.show();

                //                AddPlaylistFragment addPlaylistFragment = new AddPlaylistFragment();
                //                FragmentManager fragmentManager = getFragmentManager();
                //                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //
                //                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                //                        .replace(R.id.fragment_container, addPlaylistFragment, "addplaylist")
                //                        .addToBackStack(null);
                //                fragmentTransaction.commit();
            }
        });
        return view;
    }
}
