package com.example.ibo.musicplayerofficialv2.Fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
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

import com.example.ibo.musicplayerofficialv2.Adapters.ListViewAdapter;
import com.example.ibo.musicplayerofficialv2.Classes.Song;
import com.example.ibo.musicplayerofficialv2.MainActivity;
import com.example.ibo.musicplayerofficialv2.R;
import com.example.ibo.musicplayerofficialv2.ViewModel.SharedViewModel;

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
    String TAG = "mainfragmentviews";

    MenuItem item;
    SharedViewModel viewModel;

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

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        adapter = new ListViewAdapter(R.layout.songlist_customlayout, viewModel.getSongs().getValue(), getActivity());

        //Set my listview to my custom adapter
        songListView.setAdapter(adapter);
        songListView.setEmptyView(errorMsg);
        songListView.setScrollingCacheEnabled(false);
        songListView.setOnItemClickListener(new ListViewClickListener());

        Log.d(TAG, "onCreateView: isCalled");
        //return my view
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        menu.clear();
        inflater.inflate(R.menu.menu_actionbar, menu);
        item = menu.findItem(R.id.action_search);
        SearchView searchView = new SearchView(((MainActivity) getActivity()).getSupportActionBar().getThemedContext());
        //            MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
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

    //I call this class inside my onCreateView so i dont populate it too much
    private class ListViewClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            final Song song = viewModel.getSongs().getValue().get(position);
            songFragment = new SongFragment();

            /*Call FragmentManager and begin the transaction to my SongFragment class*/
            fragmentManager = getFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();

            /*Send master details to SongFragment using bundle*/
            bundle = new Bundle();
            bundle.putSerializable("arg_song", song);
            songFragment.setArguments(bundle);

            //FIXED: When navigating to "main" and clicked on another song the same song was showing in SongFragment.
            //HOW: This was fixed by changing this whole fragment managing by "remove" instead of "hide" SongFragment.
            //TODO: Maybe this is a duplicate? Check BottomNavigationBar Switch-statement under "main"
            if (fragmentManager.findFragmentByTag("song") == null) {
                fragmentManager.beginTransaction().add(R.id.fragment_container, songFragment, "song").addToBackStack(null).commit();
                fragmentTransaction.setCustomAnimations(R.anim.pull_up, R.anim.pull_down);
                fragmentManager.beginTransaction().hide(mainFragment);
                //                item.setVisible(false);
            } else {
                fragmentManager.beginTransaction().remove(songFragment).commit();
                fragmentManager.beginTransaction().add(R.id.fragment_container, songFragment, "song").addToBackStack(null).commit();
                fragmentManager.beginTransaction().show(songFragment).commit();
                //                item.setVisible(false);
                //fragmentManager.beginTransaction().show(songFragment).addToBackStack(null).commit();
            }
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
