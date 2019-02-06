package com.example.ibo.musicplayerofficialv2.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ibo.musicplayerofficialv2.Classes.Song;
import com.example.ibo.musicplayerofficialv2.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteListViewAdapter extends BaseAdapter implements Filterable {

    int layout;
    ArrayList<Song> arrayList;
    Context context;
    CustomFilter filter;
    ArrayList<Song> filterList;


    public FavoriteListViewAdapter(int layout, ArrayList<Song> arrayList, Context context) {
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

    //ViewHolder class holding my views
    private class Viewholder {
        TextView artistTxt, songNameTxt;
        CircleImageView artistImg;
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
            view = layoutInflater.inflate(R.layout.fragment_favorites_customlayout, null);

            //Find my view id's
            viewholder.artistImg = view.findViewById(R.id.favFrag_artistImg);
            viewholder.artistTxt = view.findViewById(R.id.favFrag_artistTxt);
            viewholder.songNameTxt = view.findViewById(R.id.favFrag_songNameTxt);

            //Set my view to viewholder
            view.setTag(viewholder);
        } else {
            viewholder = (Viewholder) view.getTag();
        }

        //Assign song to my arraylist
        final Song song = arrayList.get(position);

        //Set my views to their resources
        viewholder.artistImg.setImageURI(Uri.parse(song.getArtistImg()));
        viewholder.artistTxt.setText(song.getArtist());
        viewholder.songNameTxt.setText(song.getSongName());

        /*Decrease memory size of each artist image using Glide library*/
        Glide.with(view).load(song.getArtistImg()).into(viewholder.artistImg);
        notifyDataSetChanged();
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
                    if (filterList.get(i).getArtist().contains(inputText)) {
                        Song p = new Song(filterList.get(i).getArtist(), filterList.get(i).getSongName(), filterList.get(i).getSong(),
                                filterList.get(i).getArtistImg(), filterList.get(i).getLyrics(), filters.get(i).getGenre());
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
            // TODO Auto-generated method stub

            try {
                arrayList = (ArrayList<Song>) results.values;
                notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
