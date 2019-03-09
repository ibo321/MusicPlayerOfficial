package com.example.ibo.musicplayerofficial;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ibo.musicplayerofficial.Fragments.CollectionFragment;
import com.example.ibo.musicplayerofficial.Fragments.MainFragment;
import com.example.ibo.musicplayerofficial.Fragments.RadioFragment;
import com.example.ibo.musicplayerofficial.LoginRegister.LoginActivity;
import com.example.ibo.musicplayerofficial.ViewModel.SharedViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    SharedViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.OrangeTheme));

        getSupportActionBar().setTitle("Song list");

        //        viewModel = ViewModelProviders.of(this).get(SharedViewModel.class);

        //find my bottomNavigation
        BottomNavigationView bottomNav = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //set onItemSelectedListener - the method 'navListener' is placed outside onCreate
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        //Setup MainFragment as homepage
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment(), "main").commit();
    }

    //onItemSelectedListener method
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (menuItem.getItemId()) {
                case R.id.action_main:

                    /*Checks if MainFragment is clicked with its tag
                     * Shows the fragment if its in the backstack
                     * If not then it adds it to the container
                     * And hides previous fragments in the backstack*/

                    if (fragmentManager.findFragmentByTag("collection") != null) {
                        fragmentTransaction.hide(fragmentManager.findFragmentByTag("collection"));
                    }

                    if (fragmentManager.findFragmentByTag("radio") != null) {
                        fragmentTransaction.hide(fragmentManager.findFragmentByTag("radio"));
                    }

                    //FIXED: Stopped song from playing when clicking on "main" again by replacing "hide" with "remove"
                    if (fragmentManager.findFragmentByTag("songfragment") != null) {
                        fragmentTransaction.remove(fragmentManager.findFragmentByTag("songfragment"));
                    }

                    if (fragmentManager.findFragmentByTag("mainplaylistfragment") != null) {
                        fragmentTransaction.remove(fragmentManager.findFragmentByTag("mainplaylistfragment"));
                    }

                    if (fragmentManager.findFragmentByTag("main") != null) {
                        fragmentTransaction.show(fragmentManager.findFragmentByTag("main"));
                    } else {
                        fragmentTransaction.add(R.id.fragment_container, new MainFragment(), "main");
                    }
                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle("Song list");
                    break;

                case R.id.action_collection:

                    /*Checks if CollectionFragment is clicked with its tag
                     * Shows the fragment if its in the backstack
                     * If not then it adds it to the container
                     * And hides previous fragments in the backstack*/

                    if (fragmentManager.findFragmentByTag("main") != null) {
                        fragmentTransaction.hide(fragmentManager.findFragmentByTag("main"));
                    }

                    if (fragmentManager.findFragmentByTag("radio") != null) {
                        fragmentTransaction.hide(fragmentManager.findFragmentByTag("radio"));
                    }

                    //FIXED: Stopped song from playing when clicking on "main" again by replacing "hide" with "remove"
                    if (fragmentManager.findFragmentByTag("songfragment") != null) {
                        fragmentTransaction.remove(fragmentManager.findFragmentByTag("songfragment"));
                    }

                    if (fragmentManager.findFragmentByTag("mainplaylistfragment") != null) {
                        fragmentTransaction.remove(fragmentManager.findFragmentByTag("mainplaylistfragment"));
                    }

                    if (fragmentManager.findFragmentByTag("collection") != null) {
                        fragmentTransaction.show(fragmentManager.findFragmentByTag("collection"));
                    } else {
                        fragmentTransaction.add(R.id.fragment_container, new CollectionFragment(), "collection");
                    }

                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle("Collection");

                    break;
                case R.id.action_radio:

                    /*Checks if RadioFragment is clicked with its tag
                     * Shows the fragment if its in the backstack
                     * If not then it adds it to the container
                     * And hides previous fragments in the backstack*/

                    if (fragmentManager.findFragmentByTag("main") != null) {
                        fragmentTransaction.hide(fragmentManager.findFragmentByTag("main"));
                    }

                    if (fragmentManager.findFragmentByTag("collection") != null) {
                        fragmentTransaction.hide(fragmentManager.findFragmentByTag("collection"));
                    }

                    //FIXED: Stopped song from playing when clicking on "main" again by replacing "hide" with "remove"
                    if (fragmentManager.findFragmentByTag("songfragment") != null) {
                        fragmentTransaction.remove(fragmentManager.findFragmentByTag("songfragment"));
                    }

                    if (fragmentManager.findFragmentByTag("mainplaylistfragment") != null) {
                        fragmentTransaction.remove(fragmentManager.findFragmentByTag("mainplaylistfragment"));
                    }

                    if (fragmentManager.findFragmentByTag("radio") != null) {
                        fragmentTransaction.show(fragmentManager.findFragmentByTag("radio"));
                    } else {
                        fragmentTransaction.add(R.id.fragment_container, new RadioFragment(), "radio");
                    }

                    fragmentTransaction.commit();
                    getSupportActionBar().setTitle("Radio");

                    break;
            }
            return true;
        }
    };

    //    TODO: Hide searchview in SongFragment
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_logout:
                signOut();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        Toast.makeText(this, "Goodbye!", Toast.LENGTH_LONG).show();
    }

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