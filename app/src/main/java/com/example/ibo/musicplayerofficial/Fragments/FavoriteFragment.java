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
import android.widget.Toast;

import com.example.ibo.musicplayerofficial.Adapters.ListViewAdapter;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.R;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import static android.view.View.GONE;
import static com.example.ibo.musicplayerofficial.Fragments.SongFragment.READ_BLOCK_SIZE;

public class FavoriteFragment extends Fragment {

    ListView listView;
    ArrayList<String> arrayList;
    ListViewAdapter adapter;
    TextView artistTV, songnameTV;
    ImageView favB, artistImg;

    String artist, songname;

    int counter = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.songlist_customlayout, container, false);

        listView = view.findViewById(R.id.favFrag_listview);
        favB = view.findViewById(R.id.favButton);
        artistTV = view.findViewById(R.id.artistTxt);
        songnameTV = view.findViewById(R.id.songNameTxt);
        artistImg = view.findViewById(R.id.songFrag_artistImg);


        //Doesnt work!
//        favB.setVisibility(GONE);

        arrayList = new ArrayList<>();

        try {

            String filePath = getActivity().getFilesDir().getPath() + "/test.txt";
            File f = new File(filePath);

            FileInputStream is = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(is);

            //read the retrieved object
            Song song = (Song) ois.readObject();

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

            artist = song.getArtist();
            songname = song.getSongName();

            songnameTV.setText(songname);
            artistTV.setText(artist);
            artistImg.setImageResource(song.getArtistImg());

            //close resources
            ois.close();
            is.close();




        } catch (Exception e) {
            e.printStackTrace();
        }

        return view;
    }


}
