package com.example.ibo.musicplayerofficial.Music;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
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

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigationBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.action_main:
                        startActivity(new Intent(getApplicationContext(), SongListActivity.class));
                        break;

                    case R.id.action_collection:
                        startActivity(new Intent(getApplicationContext(), CollectionActivity.class));
                        break;
                }

                return true;
            }
        });

        //Actionbar modified
        getSupportActionBar().setTitle("Song list");
//        getSupportActionBar().setBackgroundDrawable(getDrawable(R.drawable.blackgradient));

        //Find my listview
        listView = (ListView) findViewById(R.id.listView);

        //create a new arraylist object
        arrayList = new ArrayList<>();

        //Add songs to my list
        arrayList.add(new Song("Beyonce","-Formation",R.raw.beyonce_formation));
        arrayList.add(new Song("Chris Brown","-Hope You Do",R.raw.chrisbrown_hopeyoudo));
        arrayList.add(new Song("Akon ft. Colby'O'Donis","-Beautiful",R.raw.akon_beautiful_ft_colbyodonis_kardinaloffishall));
        arrayList.add(new Song("Akon", "-Beautiful", R.raw.akon_dontmatter));
        arrayList.add(new Song("Akon", "-Locked Up", R.raw.akon_lockedup_ft_stylesp));
        arrayList.add(new Song("Ava Max", "-Sweet but Psycho", R.raw.avamax_sweetbutpsycho));
        arrayList.add(new Song("Tupac and Biggie ft. Akon Remix","-Ghetto", R.raw.tupacbiggieakon_ghetto));

        //Create a new adapter of my custom adapter and assign its values
        adapter = new ListViewAdapter(R.layout.listview_customlayout, arrayList, this);

        //Set my listview to my custom adapter
        listView.setAdapter(adapter);
    }
}
