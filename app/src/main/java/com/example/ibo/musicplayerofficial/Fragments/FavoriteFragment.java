package com.example.ibo.musicplayerofficial.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.ibo.musicplayerofficial.Adapters.FavoriteListViewAdapter;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.MainActivity;
import com.example.ibo.musicplayerofficial.R;
import com.example.ibo.musicplayerofficial.ViewModel.SharedViewModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class FavoriteFragment extends Fragment {

    private String TAG1 = "callingviews";

    ListView listView;
    ArrayList<Song> arrayList;
    FavoriteListViewAdapter adapter;

    TextView errorMsg;
    SharedViewModel viewModel;
    FavoriteFragment favoriteFragment;

    //region Declare ObjectInputStream objects replaced with ViewModel
    String filePath;
    File f;
    FileInputStream file;
    ObjectInputStream object;
    boolean cont = true;
    //endregion

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Collection");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);
        Log.d(TAG1, "onCreateView: is called");
        listView = view.findViewById(R.id.favFrag_listview);
        errorMsg = view.findViewById(R.id.favFrag_emptyMsg);
        favoriteFragment = new FavoriteFragment();
        //region Unused ActionBar
        //        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        //        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Collection");
        //endregion

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        //region Replaced ObjectStream with ViewModel
        //        try {
        //
        //            /*Get the file path and convert to a file*/
        //            filePath = getActivity().getFilesDir().getPath() + "/test.txt";
        //            f = new File(filePath);
        //
        //            /*Get file streams*/
        //            file = new FileInputStream(f);
        //            object = new ObjectInputStream(file);
        //
        //            while (cont) {
        //                try {
        //
        //                    /*Get list of songs from the stream
        //                     *Use Set<Song> object to remove duplicates*/
        //                    arrayList = (ArrayList<Song>) object.readObject();
        //                    Set<Song> noDupList = new LinkedHashSet<>(arrayList);
        //
        //                    for (Song song : arrayList) {
        //                        noDupList.add(song);
        //                    }
        //
        //                } catch (EOFException e) {
        //                    e.toString();
        //                    break;
        //                }
        //            }
        //
        //            /*close resources*/
        //            object.close();
        //            file.close();
        //        } catch (Exception e) {
        //            Log.e("printstack: ", e.toString());
        //        }
        //endregion

        adapter = new FavoriteListViewAdapter(R.layout.fragment_favorites_customlayout, getActivity());

        //Set my listview to my custom adapter
        listView.setAdapter(adapter);
        listView.setEmptyView(errorMsg);

        viewModel.getFavSongs().observe(getActivity(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                adapter.setSongs(songs);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                SongFragment songFragment = new SongFragment();
                FavoriteFragment favoriteFragment = new FavoriteFragment();
                //                Song song = viewModel.getFavList().getValue().get(position);
                Song song = viewModel.getFavSongs().getValue().get(position);
                viewModel.setClickedSong(song);

                if (fragmentManager.findFragmentByTag("songfragment") == null) {
                    fragmentManager.beginTransaction().add(R.id.fragment_container, songFragment, "songfragment").addToBackStack(null).commit();
                    fragmentManager.beginTransaction().hide(favoriteFragment);
                } else {
                    fragmentManager.beginTransaction().remove(songFragment).commit();
                    fragmentManager.beginTransaction().add(R.id.fragment_container, songFragment, "songfragment").addToBackStack(null).commit();
                    fragmentManager.beginTransaction().show(songFragment).commit();
                }
            }
        });
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.menu_actionbar, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        //        MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(item, searchView);
        searchView.setQueryHint("Search here..");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String input) {
                if (adapter != null) {
                    adapter.getFilter().filter(input);
                }
                return false;
            }
        });
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
