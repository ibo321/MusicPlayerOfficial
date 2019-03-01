package com.example.ibo.musicplayerofficial.Fragments;

import android.annotation.SuppressLint;
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

import com.example.ibo.musicplayerofficial.Adapters.ListViewAdapter;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.MainActivity;
import com.example.ibo.musicplayerofficial.R;
import com.example.ibo.musicplayerofficial.ViewModel.SharedViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class MainFragment extends Fragment {

    //Declare variables
    ListView songListView;
    ListViewAdapter adapter;

    TextView errorMsg;

    SongFragment songFragment;
    MainFragment mainFragment;
    FragmentTransaction fragmentTransaction;
    FragmentManager fragmentManager;

    Bundle bundle;
    String TAG = "timecalculator";

    MenuItem menuItem;
    SharedViewModel viewModel;
    List<Song> s;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //Find my listview
        songListView = view.findViewById(R.id.mainFrag_songListView);
        errorMsg = view.findViewById(R.id.mainFrag_emptyMsg);

        /*Initiliaze my SongFragment*/
        songFragment = new SongFragment();
        mainFragment = new MainFragment();

        adapter = new ListViewAdapter(R.layout.songlist_customlayout, getActivity());

        //Set my listview to my custom adapter
        songListView.setAdapter(adapter);
        songListView.setEmptyView(errorMsg);
        //        songListView.setScrollingCacheEnabled(false);
        songListView.setOnItemClickListener(new ListViewClickListener());

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getAllSongs().observe(getActivity(), new Observer<List<Song>>() {
            @Override
            public void onChanged(List<Song> songs) {
                adapter.setSongs(songs);
            }
        });

        Log.d(TAG, "onCreateView: isCalled");

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.menu_actionbar, menu);
        menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        //            MenuItemCompat.setShowAsAction(menuItem, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
        MenuItemCompat.setActionView(menuItem, searchView);
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

    //I call this class inside my onCreateView so i dont populate it too much
    private class ListViewClickListener implements AdapterView.OnItemClickListener {

        @SuppressLint("SimpleDateFormat")
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            //            final Song song = viewModel.getSongs().getValue().get(position);
            songFragment = new SongFragment();

            /*Call FragmentManager and begin the transaction to my SongFragment class*/
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            //region Send master details to SongFragment using bundle replaced with Viewmodel.
            //            bundle = new Bundle();
            //            bundle.putSerializable("arg_song", song);
            //            songFragment.setArguments(bundle);
            //endregion

            Log.d(TAG, "Reading song from viewmodel: " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
            viewModel.setClickedSong(viewModel.getAllSongs().getValue().get(position));

            if (fragmentManager.findFragmentByTag("songfragment") == null) {
                fragmentManager.beginTransaction().add(R.id.fragment_container, songFragment, "songfragment").addToBackStack(null).commit();
                fragmentTransaction.setCustomAnimations(R.anim.pull_up, R.anim.pull_down);
                fragmentManager.beginTransaction().hide(mainFragment);
                //                menuItem.setVisible(false);
            } else {
                fragmentManager.beginTransaction().remove(songFragment).commit();
                fragmentManager.beginTransaction().add(R.id.fragment_container, songFragment, "songfragment").addToBackStack(null).commit();
                fragmentManager.beginTransaction().show(songFragment).commit();
                //                menuItem.setVisible(false);
                //fragmentManager.beginTransaction().show(songFragment).addToBackStack(null).commit();
            }
            Log.d(TAG, "Began transaction to Songfragment:  " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
        }
    }
    //region Calling a method to stop the song from adapter class (unused)
    //    @Override
    //    public void onPause() {
    //        super.onPause();
    //
    //        //Call method PauseSong inside my adapter
    //        adapter.PauseSong();
    //    }
    //endregion
}