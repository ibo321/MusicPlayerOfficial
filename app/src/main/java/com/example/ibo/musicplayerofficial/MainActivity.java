package com.example.ibo.musicplayerofficial;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;

import com.example.ibo.musicplayerofficial.Adapters.FavoriteListViewAdapter;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.Fragments.CollectionFragment;
import com.example.ibo.musicplayerofficial.Fragments.MainFragment;
import com.example.ibo.musicplayerofficial.Fragments.RadioFragment;
import com.example.ibo.musicplayerofficial.Interfaces.OnUpdateFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CollectionFragment collectionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        //        getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.OrangeTheme));
        collectionFragment = new CollectionFragment();
        //find my bottomNavigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        //set onItemSelectedListener - the method 'navListener' is placed outside onCreate
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //Setup MainFragment as homepage
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment(), "main").commit();
    }

    //onItemSelectedListener method
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            //set fragment to null
            //            Fragment selectedFragment = null;

            FragmentManager fragmentManager = getSupportFragmentManager();
            switch (menuItem.getItemId()) {
                case R.id.action_main:

                    /*Checks if MainFragment is clicked with its tag
                     * Shows the fragment if its in the backstack
                     * If not then it adds it to the container
                     * And hides previous fragments in the backstack*/


                    if (fragmentManager.findFragmentByTag("collection") != null) {
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("collection")).commit();
                    }
                    if (fragmentManager.findFragmentByTag("radio") != null) {
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("radio")).commit();
                    }
                    if (fragmentManager.findFragmentByTag("song") != null) {
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("song")).commit();
                    }

                    if (fragmentManager.findFragmentByTag("main") != null) {
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("main")).commit();
                    } else {
                        fragmentManager.beginTransaction().add(R.id.fragment_container, new MainFragment(), "main").commit();
                    }
                    //                    getSupportActionBar().hide();
                    break;

                case R.id.action_collection:

                    /*Checks if CollectionFragment is clicked with its tag
                     * Shows the fragment if its in the backstack
                     * If not then it adds it to the container
                     * And hides previous fragments in the backstack*/


                    if (fragmentManager.findFragmentByTag("main") != null) {
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("main")).commit();
                    }
                    if (fragmentManager.findFragmentByTag("radio") != null) {
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("radio")).commit();
                    }
                    if (fragmentManager.findFragmentByTag("collection" ) != null){
                    }
                    if (fragmentManager.findFragmentByTag("song") != null) {
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("song")).commit();
                    }

                    if (fragmentManager.findFragmentByTag("collection") != null) {
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("collection")).commit();

                    } else {
                        fragmentManager.beginTransaction().add(R.id.fragment_container, new CollectionFragment(), "collection").commit();
                    }


                    //                    getSupportActionBar().hide();
                    break;
                case R.id.action_radio:

                    /*Checks if RadioFragment is clicked with its tag
                     * Shows the fragment if its in the backstack
                     * If not then it adds it to the container
                     * And hides previous fragments in the backstack*/


                    if (fragmentManager.findFragmentByTag("main") != null) {
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("main")).commit();
                    }
                    if (fragmentManager.findFragmentByTag("collection") != null) {
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("collection")).commit();
                    }
                    if (fragmentManager.findFragmentByTag("song") != null) {
                        fragmentManager.beginTransaction().hide(fragmentManager.findFragmentByTag("song")).commit();
                    }
                    if (fragmentManager.findFragmentByTag("radio") != null) {
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("radio")).commit();
                    } else {
                        fragmentManager.beginTransaction().add(R.id.fragment_container, new RadioFragment(), "radio").commit();
                    }

                    //                    getSupportActionBar().show();
                    //                    getSupportActionBar().setTitle("Radio");
                    break;
            }
            return true;
        }
    };

    @Override
    public void onBackPressed() {

//        int count = getFragmentManager().getBackStackEntryCount();
//
//        if (count == 0) {
//            super.onBackPressed();
//            //additional code
//        } else {
//            getFragmentManager().popBackStack();
//        }

        //if there is something in my stack of fragments then go back to it when clicked 'back'
        android.app.FragmentManager fragmentManager = getFragmentManager();

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
