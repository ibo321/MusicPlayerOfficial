package com.example.ibo.musicplayerofficial.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibo.musicplayerofficial.Adapters.FavoriteListViewAdapter;
import com.example.ibo.musicplayerofficial.Adapters.ListViewAdapter;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.MainActivity;
import com.example.ibo.musicplayerofficial.R;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static android.view.View.GONE;

public class FavoriteFragment extends Fragment {

    //Declare listview objects
    ListView listView;
    ArrayList<Song> arrayList;
    FavoriteListViewAdapter adapter;

    //Declare view objects
    TextView artistTV, songnameTV;
    ImageView favB, artistImg;

    SearchView searchBar;
    //Declare ObjectInputStream objects
    String filePath;
    File f;
    FileInputStream file;
    ObjectInputStream object;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        listView = view.findViewById(R.id.favFrag_listview);
        searchBar = view.findViewById(R.id.searchBar);

        //Doesnt work!
        //        favB = (ImageView) view.findViewById(R.id.favButton);
        //        favB.setVisibility(View.INVISIBLE);

        arrayList = new ArrayList<Song>();
        boolean cont = true;

        try {

            /*Get the file path and declare the file*/
            filePath = getActivity().getFilesDir().getPath() + "/test.txt";
            f = new File(filePath);

            /*Get file streams*/
            file = new FileInputStream(f);
            object = new ObjectInputStream(file);

            //TODO: Duplicates one of the songs.. FIX IT!
            while (cont) {
                try {

                    /*Get list of songs from the stream
                     *Use Set<Song> object to remove duplicates*/
                    arrayList = (ArrayList<Song>) object.readObject();
                    Set<Song> noDupList = new LinkedHashSet<>(arrayList);

                    //Song song = (Song) ois.readObject();

                    /*Iterate through the array list and add songs to the list*/
                    for (Song song : arrayList) {
                        noDupList.add(song);
                    }

                } catch (EOFException e) {
                    e.toString();
                    break;
                }
            }

            /*close resources*/
            object.close();
            file.close();

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

            //region Another way to retrieve values using string/int values
            //            artist = song.getArtist();
            //            songname = song.getSongName();
            //
            //            songnameTV.setText(songname);
            //            artistTV.setText(artist);
            //            artistImg.setImageResource(song.getArtistImg());
            //endregion

        } catch (Exception e) {
            Log.d("printstack: ", e.toString());
        }

        adapter = new FavoriteListViewAdapter(R.layout.fragment_favorites_customlayout, arrayList, getActivity());

        //Set my listview to my custom adapter
        listView.setAdapter(adapter);

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String input) {

                adapter.getFilter().filter(input);
                return false;
            }
        });

        return view;
    }
}
