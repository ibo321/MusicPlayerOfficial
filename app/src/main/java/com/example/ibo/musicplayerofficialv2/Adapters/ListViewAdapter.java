package com.example.ibo.musicplayerofficialv2.Adapters;

import android.content.Context;
import android.util.Log;
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
import com.example.ibo.musicplayerofficialv2.Classes.Song;
import com.example.ibo.musicplayerofficialv2.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter implements Filterable {

    //Create variables
    int layout;
    ArrayList<Song> arrayList;
    Context context;

    FileOutputStream file;
    ObjectOutputStream object;
    int counter = 0;

    ArrayList<Song> favList;
    CustomFilter filter;
    ArrayList<Song> filterList;

    String filePath;
    File getFile;

    //Constructor
    public ListViewAdapter(int layout, ArrayList<Song> arrayList, Context context) {
        this.layout = layout;
        this.arrayList = arrayList;
        this.context = context;
        this.filterList = arrayList;
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
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        //Create viewholder variable
        final Viewholder viewholder;

        //Check if view is null
        if (view == null) {

            //Create new ViewHolder object
            viewholder = new Viewholder();

            //Inflate my view
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.songlist_customlayout, null);

            //Find my view id's
            viewholder.artistImg = view.findViewById(R.id.songFrag_artistImg);
            viewholder.artistTxt = view.findViewById(R.id.artistTxt);
            viewholder.songNameTxt = view.findViewById(R.id.songNameTxt);
            viewholder.favBtn = view.findViewById(R.id.favButton);
            viewholder.genreTxt = view.findViewById(R.id.genre);

            //Set my view to viewholder
            view.setTag(viewholder);
        } else {
            viewholder = (Viewholder) view.getTag();
        }

        //Assign song to my arraylist
        final Song song = arrayList.get(position);

        //Set my views to their resources
        Glide.with(view).load(song.getArtistImg()).into(viewholder.artistImg);
        //        viewholder.artistImg.setImageURI(Uri.parse(song.getArtistImg()));
        viewholder.artistTxt.setText(song.getArtist());
        viewholder.songNameTxt.setText(song.getSongName());
        viewholder.genreTxt.setText(song.getGenre());

        /*Create new list to store favorite music*/
        favList = new ArrayList<>();

        /*Create filepath*/
        //        filePath = context.getFilesDir().getPath() + "/test.txt";
        //        getFile = new File(filePath);
        getFile = new File(context.getFilesDir().getPath() + "/test.txt");

        viewholder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    /*Create fileoutputstream and objectoutputstream*/
                    //                    file = new FileOutputStream(getFile);
                    object = new ObjectOutputStream(new FileOutputStream(getFile));

                    /*Add song to new list*/
                    favList.add(song);
                    /*Add list to the stream*/
                    object.writeObject(favList);

                    //region Can also use this to write data (instead of class object)
                    //                    fileout = context.openFileOutput("mytextfile.txt", MODE_PRIVATE);
                    //                    outputWriter = new OutputStreamWriter(fileout);
                    //                    outputWriter.write(song.getSongName());
                    //                    outputWriter.close();
                    //endregion
                    /*Show message of saved files*/
                    counter++;
                    Toast.makeText(context, "Added to favorite list: " + song.getArtist() + " " + song.getSongName(), Toast.LENGTH_SHORT).show();
                    Log.d("favList", "Added to the favList: " + counter + " " + song.getArtist() + " " + song.getSongName());

                    /*Closing resources*/
                    object.close();
                    file.close();

                    //region Used to APPEND in stream but couldnt make it work
                    //                    ObjectOutputStream os2 = new ObjectOutputStream(new FileOutputStream("/test.txt", true)) {
                    //                        protected void writeStreamHeader() throws IOException {
                    //                            reset();
                    //                        }
                    //                    };
                    //
                    //                    os2.writeObject(song);
                    //endregion

                } catch (Exception e) {
                    e.printStackTrace();
                }
                viewholder.favBtn.setImageResource(R.drawable.favorite);
            }
        });

        return view;
    }

    //INNER CLASS
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