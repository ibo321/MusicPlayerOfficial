package com.example.ibo.musicplayerofficial.Music;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.ibo.musicplayerofficial.Adapters.ListViewAdapter;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.R;

import java.util.ArrayList;

public class SongListActivity extends AppCompatActivity {

    ArrayList<Song> arrayList;
    ListView listView;
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        getSupportActionBar().hide();
        listView = (ListView) findViewById(R.id.listView);

        arrayList = new ArrayList<>();
        arrayList.add(new Song("Beyonce","-Formation",R.raw.beyonce_formation));
        arrayList.add(new Song("Chris Brown","-Hope You Do",R.raw.chrisbrown_hopeyoudo));
        arrayList.add(new Song("Akon ft. Colby'O'Donis","-Beautiful",R.raw.akon_beautiful_ft_colbyodonis_kardinaloffishall));
        arrayList.add(new Song("Akon", "-Beautiful", R.raw.akon_dontmatter));
        arrayList.add(new Song("Akon", "-Locked Up", R.raw.akon_lockedup_ft_stylesp));
        arrayList.add(new Song("Ava Max", "-Sweet but Psycho", R.raw.avamax_sweetbutpsycho));
        arrayList.add(new Song("Tupac and Biggie ft. Akon Remix","-Ghetto", R.raw.tupacbiggieakon_ghetto));
        adapter = new ListViewAdapter(R.layout.listview_customlayout, arrayList, this);
        listView.setAdapter(adapter);
    }
}
