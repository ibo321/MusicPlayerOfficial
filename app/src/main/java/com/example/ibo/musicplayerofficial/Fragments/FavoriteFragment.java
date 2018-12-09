package com.example.ibo.musicplayerofficial.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ibo.musicplayerofficial.Adapters.ListViewAdapter;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static com.example.ibo.musicplayerofficial.Fragments.SongFragment.READ_BLOCK_SIZE;

public class FavoriteFragment extends Fragment {

    TextView songNameTV, artistTV;
    ImageView favBtn, artistImg;

    ListView listView;
    ArrayList<Song> arrayList;
    ListViewAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        songNameTV = view.findViewById(R.id.songNameTxt);
        artistTV = view.findViewById(R.id.artistTxt);
        artistImg = view.findViewById(R.id.songFrag_artistImg);
        favBtn = view.findViewById(R.id.favButton);
        listView = view.findViewById(R.id.favFrag_listview);

        arrayList = new ArrayList<>();

        //Remove the favorite button from this fragment (dont need)
        //ERROR: Causes the app to crash! FIX!
//        favBtn.setVisibility(view.GONE);

        try {

            String filePath = getActivity().getFilesDir().getPath() + "/test.txt";
            File f = new File(filePath);

            FileInputStream is = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(is);

            //read the retrieved object
            Song song = (Song) ois.readObject();

            //close resources
            ois.close();
            is.close();

            arrayList.add(new Song(song.getArtist(), song.getSongName(), song.getSong(), song.getArtistImg(), song.getLyrics()));

        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter = new ListViewAdapter(R.layout.songlist_customlayout, arrayList, getActivity());
        listView.setAdapter(adapter);

        return view;
    }

    //region Can also use this (strings and values instead of whole class object)
    //            FileInputStream fileIn = getActivity().openFileInput("mytextfile.txt");
//            InputStreamReader InputRead = new InputStreamReader(fileIn);
//
//            char[] inputBuffer = new char[READ_BLOCK_SIZE];
//            String s = "";
//            int charRead;
//
//            while ((charRead = InputRead.read(inputBuffer)) > 0) {
//                // char to string conversion
//                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
//                s += readstring;
//            }
//            InputRead.close();
//            songNameTV.setText(s);
    //endregion
}
