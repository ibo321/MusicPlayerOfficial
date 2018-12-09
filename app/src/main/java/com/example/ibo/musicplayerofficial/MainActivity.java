package com.example.ibo.musicplayerofficial;

import android.content.Context;
import android.os.Bundle;
import android.service.notification.StatusBarNotification;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.Fragments.CollectionFragment;
import com.example.ibo.musicplayerofficial.Fragments.MainFragment;
import com.example.ibo.musicplayerofficial.Fragments.RadioFragment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getSupportActionBar().setTitle("Song list");
        getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.OrangeTheme));
        //find my bottomNavigation
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        //set onItemSelectedListener - the method 'navListener' is placed outside onCreate
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //Setup MainFragment as homepage
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
    }

    //onItemSelectedListener method
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            //set fragment to null
            Fragment selectedFragment = null;

            switch (menuItem.getItemId()) {
                case R.id.action_main:
                    //When clicked on 'Main' set selectedFragment to the MainFragment class
                    selectedFragment = new MainFragment();
                    //Actionbar
                    getSupportActionBar().setTitle("Song list");
                    break;

                case R.id.action_collection:
                    //When clicked on 'Collection' set selectedFragment to the CollectionFragment class
                    selectedFragment = new CollectionFragment();
                    getSupportActionBar().setTitle("Collection");
                    break;

                case R.id.action_radio:
                    //When clicked on 'Radio' set selectedFragment to the RadioFragment class
                    selectedFragment = new RadioFragment();
                    getSupportActionBar().setTitle("Radio");
                    break;
            }

            //set the selected fragments to the container so it display the views in their
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
                    .replace(R.id.fragment_container, selectedFragment).commit();

            return true;
        }
    };

    @Override
    public void onBackPressed() {

        //if there is something in my stack of fragments then go back to it when clicked 'back'
        android.app.FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            super.onBackPressed();
        }
    }
}
