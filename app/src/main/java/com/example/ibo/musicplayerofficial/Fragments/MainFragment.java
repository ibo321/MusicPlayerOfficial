package com.example.ibo.musicplayerofficial.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ibo.musicplayerofficial.Adapters.ListViewAdapter;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.R;

import java.util.ArrayList;

public class MainFragment extends Fragment {


    //Declare variables
    ArrayList<Song> arrayList;
    ListView listView;
    ListViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //Find my listview
        listView = (ListView) view.findViewById(R.id.listView);

        //create a new arraylist object
        arrayList = new ArrayList<>();

        //Add songs to my list
        arrayList.add(new Song("Beyonce", "-Formation", R.raw.beyonce_formation));
        arrayList.add(new Song("Chris Brown", "-Hope You Do", R.raw.chrisbrown_hopeyoudo));
        arrayList.add(new Song("Akon ft. Colby'O'Donis", "-Beautiful", R.raw.akon_beautiful_ft_colbyodonis_kardinaloffishall));
        arrayList.add(new Song("Akon", "-Beautiful", R.raw.akon_dontmatter));
        arrayList.add(new Song("Akon", "-Locked Up", R.raw.akon_lockedup_ft_stylesp));
        arrayList.add(new Song("Ava Max", "-Sweet but Psycho", R.raw.avamax_sweetbutpsycho));
        arrayList.add(new Song("Tupac and Biggie ft. Akon Remix", "-Ghetto", R.raw.tupacbiggieakon_ghetto));

        //Create a new adapter of my custom adapter and assign its values
        adapter = new ListViewAdapter(R.layout.listview_customlayout, arrayList, getActivity());

        //Set my listview to my custom adapter
        listView.setAdapter(adapter);

        //return my view
        return view;
    }
}
