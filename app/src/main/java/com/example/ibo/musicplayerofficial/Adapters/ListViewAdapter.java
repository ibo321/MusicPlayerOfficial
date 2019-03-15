package com.example.ibo.musicplayerofficial.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.Fragments.MainPlaylistFragment;
import com.example.ibo.musicplayerofficial.R;
import com.example.ibo.musicplayerofficial.ViewModel.SharedViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

public class ListViewAdapter extends BaseAdapter implements Filterable {

    //Create variables
    int layout;
    List<Song> arrayList;
    Context context;
    FileOutputStream file;
    ObjectOutputStream object;
    int counter = 0;

    List<Song> favList = new ArrayList<>();
    CustomFilter filter;
    List<Song> filterList;
    Set<Song> nodup = new LinkedHashSet<>();
    String filePath;
    File getFile;

    SharedViewModel viewModel;

    //Constructor
    public ListViewAdapter(int layout, Context context) {
        this.layout = layout;
        this.context = context;
        viewModel = ViewModelProviders.of((FragmentActivity) context).get(SharedViewModel.class);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new CustomFilter();
        }
        return filter;
    }

    //ViewHolder class holding my views
    static class Viewholder {
        TextView artistTxt, songNameTxt, genreTxt;
        ImageView favBtn, artistImg, moreBtn;
        RelativeLayout layout;
    }

    public void setSongs(List<Song> songs) {
        this.arrayList = songs;
        this.filterList = songs;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.indexOf(position);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {

        //        final Viewholder viewholder;
        View view = null;

        /*Check if view is null*/
        if (convertView == null) {
            /*Inflate my view*/
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.songlist_customlayout, null);

            /*Create new ViewHolder object*/
            final Viewholder viewholder = new Viewholder();

            //Find my view id's
            viewholder.artistImg = view.findViewById(R.id.songFrag_artistImg);
            viewholder.artistTxt = view.findViewById(R.id.songFrag_artist);
            viewholder.songNameTxt = view.findViewById(R.id.songFrag_songName);
            viewholder.favBtn = view.findViewById(R.id.songFrag_favorite);
            viewholder.genreTxt = view.findViewById(R.id.songFrag_genre);
            viewholder.moreBtn = view.findViewById(R.id.songFrag_more);
            viewholder.layout = view.findViewById(R.id.mainFrag_parent);
            /*Set my view to viewholder*/
            view.setTag(viewholder);

        } else {
            view = convertView;
        }

        /*Assign song to my arraylist*/
        final Song song = arrayList.get(position);
        final Viewholder viewholder = (Viewholder) view.getTag();

        //Set my views to their resources
        Glide.with(context).asBitmap().load(song.getArtist().getArtist_image()).into(viewholder.artistImg);
        viewholder.artistTxt.setText(song.getArtist().getArtist_name());
        viewholder.songNameTxt.setText(song.getSongName());
        viewholder.genreTxt.setText(song.getGenre());

        if (song.isFavorite()) {
            viewholder.favBtn.setImageResource(R.drawable.favorite);
        } else {
            viewholder.favBtn.setImageResource(R.drawable.favorite_half);
        }

        //region Create filepath (replaced with ViewModel)
        //        filePath = context.getFilesDir().getPath() + "/test.txt";
        //        getFile = new File(filePath);
        //        getFile = new File(context.getFilesDir().getPath() + "/test.txt");
        //endregion

        viewholder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //region Checking for dups (unused
                favList.add(song);
                nodup.add(song);

                /*If the no-duplicate list is empty then i handle the exception like this*/
                if (nodup.size() == 0) {
                    nodup.addAll(favList);
                }
                //endregion

                if (song.isFavorite()) {
                    song.setFavorite(false);
                    viewModel.update(song);
                    viewholder.favBtn.setImageResource(R.drawable.favorite_half);
                } else {
                    song.setFavorite(true);
                    viewModel.update(song);
                    viewholder.favBtn.setImageResource(R.drawable.favorite);

                }
                //region Replaced ObjectStream with ViewModel
                //                try {
                //                    /*Create fileoutputstream and objectoutputstream*/
                //                    //                    file = new FileOutputStream(getFile);
                //                    object = new ObjectOutputStream(new FileOutputStream(getFile));
                //
                //                    /*Add song to new list*/
                //                    favList.add(song);
                //                    /*Add list to the stream*/
                //                    object.writeObject(favList);
                //
                //                    //region Can also use this to write data (instead of class object)
                //                    //                    fileout = context.openFileOutput("mytextfile.txt", MODE_PRIVATE);
                //                    //                    outputWriter = new OutputStreamWriter(fileout);
                //                    //                    outputWriter.write(song.getSongName());
                //                    //                    outputWriter.close();
                //
                //
                //                    /*Show message of saved files*/
                //                    counter++;
                //                    Toast.makeText(context, "Added to favorite list: " + song.getArtist() + " " + song.getSongName(), Toast.LENGTH_SHORT).show();
                //                    Log.d("favList", "Added to the favList: " + counter + " " + song.getArtist() + " " + song.getSongName());
                //
                //                    /*Closing resources*/
                //                    object.close();
                //                    file.close();
                //
                //                    //Used to APPEND in stream but couldnt make it work
                //                    //                    ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream("/test.txt", true)) {
                //                    //                        protected void writeStreamHeader() throws IOException {
                //                    //                            reset();
                //                    //                        }
                //                    //                    };
                //                    //
                //                    //                    os2.writeObject(song);
                //
                //
                //                } catch (Exception e) {
                //                    e.printStackTrace();
                //                }
                //endregion
            }
        });

        //TODO: Work on this to display playlist fragment!
        viewholder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(context, viewholder.moreBtn);
                popupMenu.inflate(R.menu.menu_more);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_playlist:
                                MainPlaylistFragment mainPlaylistFragment = new MainPlaylistFragment();

                                FragmentManager manager = ((AppCompatActivity) context).getSupportFragmentManager();
                                FragmentTransaction transaction = manager.beginTransaction();
                                viewModel.setClickedSong(song);
                                transaction.setCustomAnimations(R.anim.pull_up, R.anim.pull_down)
                                        //                                        .hide(manager.findFragmentByTag("main"))
                                        .add(R.id.fragment_container, mainPlaylistFragment, "mainplaylistfragment").addToBackStack(null).commit();

                                break;
                        }
                        return false;
                    }
                });

                popupMenu.show();
            }
        });
        return view;
    }

    /*A custom inner class to filter list of songs*/
    class CustomFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence inputText) {
            FilterResults results = new FilterResults();
            if (inputText != null && inputText.length() > 0) {

                /*Converts to UpperCase*/
                inputText = inputText.toString().toUpperCase();

                List<Song> filters = new ArrayList<>();

                /*Iterate through the whole list
                 * and checks if the matching inputtext
                 * is equal to the artist name
                 * and converts everything to Uppercase*/
                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getArtist().getArtist_name().toUpperCase().contains(inputText)) {
                        Song p = new Song(filterList.get(i).getSong_artistId(), filterList.get(i).getArtist(), filterList.get(i).getSongName(), filterList.get(i).getLyrics(), filterList.get(i).getGenre(), filterList.get(i).getSong());
                        filters.add(p);
                    }
                }
                results.count = filters.size();
                results.values = filters;
            } else {
                results.count = filterList.size();
                results.values = filterList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {
                arrayList = (ArrayList<Song>) results.values;
                notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
//region A method to stop the mediaplayer called in another fragment/activity
//    public void StopSong() {
//        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//            mediaPlayer.stop();
//            mediaPlayer.release();
//            mediaPlayer = null;
//        }
//    }
//endregion