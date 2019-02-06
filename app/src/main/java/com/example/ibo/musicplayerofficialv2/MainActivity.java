package com.example.ibo.musicplayerofficialv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ibo.musicplayerofficialv2.Fragments.CollectionFragment;
import com.example.ibo.musicplayerofficialv2.Fragments.MainFragment;
import com.example.ibo.musicplayerofficialv2.Fragments.RadioFragment;
import com.example.ibo.musicplayerofficialv2.LoginRegister.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setBackgroundDrawable(getDrawable(R.color.OrangeTheme));

        getSupportActionBar().setTitle("Song list");

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

                    //FIXED: Stopped song from playing when clicking on "main" again by replacing "hide" with "remove"
                    if (fragmentManager.findFragmentByTag("song") != null) {
                        fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("song")).commit();
                    }

                    if (fragmentManager.findFragmentByTag("main") != null) {
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("main")).commit();
                    } else {
                        fragmentManager.beginTransaction().add(R.id.fragment_container, new MainFragment(), "main").commit();
                    }
                    getSupportActionBar().setTitle("Song list");
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

                    //FIXED: Stopped song from playing when clicking on "main" again by replacing "hide" with "remove"
                    if (fragmentManager.findFragmentByTag("song") != null) {
                        fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("song")).commit();
                    }

                    //TODO: Re-create CollectionFragment so it can update the listview
                    //FIXED: Changed the management of the transactions!
                    if (fragmentManager.findFragmentByTag("collection") == null) {
                        fragmentManager.beginTransaction().add(R.id.fragment_container, new CollectionFragment(), "collection").addToBackStack(null).commit();
                    } else {
                        fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("collection"));
                        fragmentManager.beginTransaction().add(R.id.fragment_container, new CollectionFragment(), "collection").addToBackStack(null).commit();
                        fragmentManager.beginTransaction().show(new CollectionFragment());
                    }

                    //region Old code used to manage CollectionFragment
                    //if (fragmentManager.findFragmentByTag("collection") != null) {
                    //   fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("collection")).commit();
                    //
                    //} else {
                    //   fragmentManager.beginTransaction().add(R.id.fragment_container, new CollectionFragment(), "collection").commit();
                    //}
                    //endregion

                    getSupportActionBar().setTitle("Collection");
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

                    //FIXED: Stopped song from playing when clicking on "main" again by replacing "hide" with "remove"
                    if (fragmentManager.findFragmentByTag("song") != null) {
                        fragmentManager.beginTransaction().remove(fragmentManager.findFragmentByTag("song")).commit();
                    }
                    if (fragmentManager.findFragmentByTag("radio") != null) {
                        fragmentManager.beginTransaction().show(fragmentManager.findFragmentByTag("radio")).commit();
                    } else {
                        fragmentManager.beginTransaction().add(R.id.fragment_container, new RadioFragment(), "radio").commit();
                    }

                    getSupportActionBar().setTitle("Radio");
                    //getSupportActionBar().show();
                    //getSupportActionBar().setTitle("Radio");
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

    //    @Override
    //    public boolean onCreateOptionsMenu(Menu menu) {
    //        getMenuInflater().inflate(R.menu.menu_actionbar, menu);
    //
    //        return super.onCreateOptionsMenu(menu);
    //    }

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