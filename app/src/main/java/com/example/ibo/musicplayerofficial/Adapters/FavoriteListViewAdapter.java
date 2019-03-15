package com.example.ibo.musicplayerofficial.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FavoriteListViewAdapter extends BaseAdapter implements Filterable {

    int layout;
    List<Song> arrayList;
    Context context;
    CustomFilter filter;
    List<Song> filterList;

    public FavoriteListViewAdapter(int layout, Context context) {
        this.layout = layout;
        this.context = context;
        notifyDataSetChanged();
    }
    public void setSongs(List<Song> songs){
        this.arrayList = songs;
        this.filterList = songs;
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

        final Song song = arrayList.get(position);
        Glide.with(context).load(song.getArtist().getArtist_image()).into(viewholder.artistImg);
        viewholder.artistTxt.setText(song.getArtist().getArtist_name());
        viewholder.songNameTxt.setText(song.getSongName());

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
                    if (filterList.get(i).getArtist().getArtist_name().contains(inputText)) {
                        Song p = new Song(filterList.get(i).getSong_artistId(), filterList.get(i).getArtist(), filterList.get(i).getSong(),
                                filterList.get(i).getArtist().getArtist_image(), filterList.get(i).getLyrics(), filters.get(i).getGenre());
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
