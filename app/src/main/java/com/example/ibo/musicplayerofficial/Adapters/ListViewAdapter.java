package com.example.ibo.musicplayerofficial.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.R;
import com.example.ibo.musicplayerofficial.ViewModel.SharedViewModel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

public class ListViewAdapter extends BaseAdapter implements Filterable {

    //Create variables
    int layout;
    ArrayList<Song> arrayList;
    Context context;
    Song oldPos;
    FileOutputStream file;
    ObjectOutputStream object;
    int counter = 0;

    ArrayList<Song> favList = new ArrayList<>();
    CustomFilter filter;
    ArrayList<Song> filterList;
    Set<Song> nodup = new LinkedHashSet<>();
    String filePath;
    File getFile;

    SharedViewModel viewModel;

    //Constructor
    public ListViewAdapter(int layout, ArrayList<Song> arrayList, Context context) {
        this.layout = layout;
        this.arrayList = arrayList;
        this.context = context;
        this.filterList = arrayList;
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
        ImageView favBtn, artistImg;
    }

    @Override
    public int getCount() {
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
    public View getView(final int position, View convertView, final ViewGroup parent) {

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
            viewholder.artistTxt = view.findViewById(R.id.artistTxt);
            viewholder.songNameTxt = view.findViewById(R.id.songNameTxt);
            viewholder.favBtn = view.findViewById(R.id.favButton);
            viewholder.genreTxt = view.findViewById(R.id.genre);

            /*Set my view to viewholder*/
            view.setTag(viewholder);

        } else {
            view = convertView;
        }

        /*Assign song to my arraylist*/
        final Song song = arrayList.get(position);
        final Viewholder viewholder = (Viewholder) view.getTag();
        //Set my views to their resources
        Glide.with(context).asBitmap().load(song.getArtistImg()).into(viewholder.artistImg);
        //        viewholder.artistImg.setImageURI(Uri.parse(song.getArtistImg()));
        viewholder.artistTxt.setText(song.getArtist());
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

                favList.add(song);
                nodup.add(song);
                oldPos = arrayList.get(position);

                /*If the no-duplicate list is empty then i handle the exception like this*/
                if (nodup.size() == 0) {
                    nodup.addAll(favList);
                }

                /*Iterate through the favorite list*/
                for (int i = 0; i < favList.size(); i++) {

                    /*As the Set<> type of list cannot contain any duplicates -
                    i check if the list is lower than the favorite list and this
                    means if it is TRUE -> then there is duplicates
                     */
                    if (nodup.size() < favList.size()) {

                        /*I remove the song from both list
                        to update them synchronously
                         */
                        song.setFavorite(false);
                        favList.remove(song);
                        nodup.remove(song);
                        viewModel.deleteFromfav(song, false);
                        viewholder.favBtn.setImageResource(R.drawable.favorite_half);
                        Toast.makeText(context, "Removed " + song.getArtist() + " " + song.getSongName() + " from favorites", Toast.LENGTH_SHORT).show();
                        break;
                    } else {
                        song.setFavorite(true);
                        viewModel.setFavList(favList, true);
                        viewholder.favBtn.setImageResource(R.drawable.favorite);
                        Toast.makeText(context, "Added " + song.getArtist() + " " + song.getSongName() + " to favorites", Toast.LENGTH_SHORT).show();
                        break;
                    }
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

                ArrayList<Song> filters = new ArrayList<Song>();

                /*Iterate through the whole list
                 * and checks if the matching inputtext
                 * is equal to the artist name
                 * and converts everything to Uppercase*/
                for (int i = 0; i < filterList.size(); i++) {
                    if (filterList.get(i).getArtist().toUpperCase().contains(inputText)) {
                        Song p = new Song(filterList.get(i).getArtist(), filterList.get(i).getSongName(), filterList.get(i).getSong(), filterList.get(i).getArtistImg(), filterList.get(i).getLyrics(), filterList.get(i).getGenre());
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