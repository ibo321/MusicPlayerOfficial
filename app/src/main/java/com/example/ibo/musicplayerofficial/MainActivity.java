package com.example.ibo.musicplayerofficial;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ibo.musicplayerofficial.Fragments.CollectionFragment;
import com.example.ibo.musicplayerofficial.Fragments.MainFragment;
import com.example.ibo.musicplayerofficial.Fragments.RadioFragment;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    break;

                case R.id.action_collection:
                    //When clicked on 'Collection' set selectedFragment to the CollectionFragment class
                    selectedFragment = new CollectionFragment();
                    break;

                case R.id.action_radio:
                    //When clicked on 'Radio' set selectedFragment to the RadioFragment class
                    selectedFragment = new RadioFragment();
                    break;
            }

            //set the selected fragments to the container so it display the views in their
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

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
