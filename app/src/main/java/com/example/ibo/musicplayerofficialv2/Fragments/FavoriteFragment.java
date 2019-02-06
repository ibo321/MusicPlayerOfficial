package com.example.ibo.musicplayerofficialv2.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.ibo.musicplayerofficialv2.Adapters.FavoriteListViewAdapter;
import com.example.ibo.musicplayerofficialv2.Classes.Song;
import com.example.ibo.musicplayerofficialv2.MainActivity;
import com.example.ibo.musicplayerofficialv2.R;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class FavoriteFragment extends Fragment {

    private String TAG1 = "callingviews";

    //Declare listview objects
    ListView listView;
    ArrayList<Song> arrayList;
    FavoriteListViewAdapter adapter;

    //Declare view objects
    TextView errorMsg;

    //Declare ObjectInputStream objects
    String filePath;
    File f;
    FileInputStream file;
    ObjectInputStream object;
    boolean cont = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        //        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Collection");
    }

    //TODO: Listview is not getting updated!
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        listView = view.findViewById(R.id.favFrag_listview);
        errorMsg = view.findViewById(R.id.favFrag_emptyMsg);
        arrayList = new ArrayList<Song>();

        //        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        //        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Collection");

        Log.d(TAG1, "onCreateView: is called");
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.menu_actionbar, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        //        MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
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

    @Override
    public void onStart() {
        super.onStart();
        try {

            /*Get the file path and convert to a file*/
            filePath = getActivity().getFilesDir().getPath() + "/test.txt";
            f = new File(filePath);

            /*Get file streams*/
            file = new FileInputStream(f);
            object = new ObjectInputStream(file);

            while (cont) {
                try {

                    /*Get list of songs from the stream
                     *Use Set<Song> object to remove duplicates*/
                    arrayList = (ArrayList<Song>) object.readObject();
                    Set<Song> noDupList = new LinkedHashSet<>(arrayList);

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
        } catch (Exception e) {
            Log.e("printstack: ", e.toString());
        }

        adapter = new FavoriteListViewAdapter(R.layout.fragment_favorites_customlayout, arrayList, getActivity());

        //Set my listview to my custom adapter
        listView.setAdapter(adapter);
        listView.setEmptyView(errorMsg);

        //        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //            @Override
        //            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //                FragmentManager fragmentManager = getActivity().getSupportFragmentManager()
        //                fragmentManager.beginTransaction()
        //                        .add(R.id.fragment_container, new SongFragment())
        //                        .addToBackStack(null)
        //                        .commit();
        //            }
        //        });

        Log.d(TAG1, "onStart: isCalled");
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

    //region Lifecycle-methods
    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG1, "onPause: isCalled");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG1, "onDestroyView: isCalled");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG1, "onStop: isCalled");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG1, "onDestroy: isCalled");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG1, "onActivityCreated: isCalled");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG1, "onAttach: isCalled");
    }
    //endregion

}
