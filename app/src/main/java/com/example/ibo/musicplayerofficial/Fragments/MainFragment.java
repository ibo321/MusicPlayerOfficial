package com.example.ibo.musicplayerofficial.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ibo.musicplayerofficial.Adapters.ListViewAdapter;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainFragment extends Fragment {

    //Declare variables
    ArrayList<Song> arrayList;
    ListView songListView;
    ListViewAdapter adapter;

    SongFragment songFragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //Actionbar
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Song list");

        //Find my listview
        songListView = (ListView) view.findViewById(R.id.songListView);

        //create a new arraylist object
        arrayList = new ArrayList<>();

        //Add songs to my list
        arrayList.add(new Song("Beyonce", "-Formation", R.raw.beyonce_formation, R.drawable.beyonce));
        arrayList.add(new Song("Chris Brown", "-Hope You Do", R.raw.chrisbrown_hopeyoudo, R.drawable.chrisbrown));
        arrayList.add(new Song("Akon ft. Colby'O'Donis", "-Beautiful", R.raw.akon_beautiful_ft_colbyodonis_kardinaloffishall, R.drawable.akon));
        arrayList.add(new Song("Akon", "-Beautiful", R.raw.akon_dontmatter, R.drawable.akon));
        arrayList.add(new Song("Akon", "-Locked Up", R.raw.akon_lockedup_ft_stylesp, R.drawable.akon));
        arrayList.add(new Song("Ava Max", "-Sweet but Psycho", R.raw.avamax_sweetbutpsycho, R.drawable.avamax));
        arrayList.add(new Song("Tupac and Biggie ft. Akon Remix", "-Ghetto", R.raw.tupacbiggieakon_ghetto, R.drawable.biggie));

        //Create a new adapter of my custom adapter and assign its values
        adapter = new ListViewAdapter(R.layout.listview_customlayout, arrayList, getActivity());

        //Set my listview to my custom adapter
        songListView.setAdapter(adapter);

        //Click on a specific song from my list
        songListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Initiliaze my songFragment to my fragment class
            songFragment = new SongFragment();

            //get details of the song clicked on to the SongFragment page
            final Song song = arrayList.get(position);
            songFragment.getSongDetails(song.getSongName(), song.getArtistImg(), song.getSong());
            //call FragmentManager and begin the transaction to my songFragment class
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            //When clicked on a listview item - navigate to songfragment and when clicked back -> go back to mainfragment
            //save my mainfragment to my stack so it isnt destroyed but kept safe so i can get back to it
            fragmentManager.beginTransaction().replace(R.id.fragment_container, songFragment).addToBackStack(null).commit();

        }
    });
    //return my view
        return view;
}
}
