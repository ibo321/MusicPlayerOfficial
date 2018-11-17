package com.example.ibo.musicplayerofficial.Adapters;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.R;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {

    //Create variables
    MediaPlayer mediaPlayer;
    int layout;
    Song currentSong;
    ArrayList<Song> arrayList;
    Context context;

    public ListViewAdapter(int layout, ArrayList<Song> arrayList, Context context) {
        this.layout = layout;
        this.arrayList = arrayList;
        this.context = context;
    }

    private class Viewholder{
        TextView artistTxt, songNameTxt;
        ImageView playB, stopB;
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

        final Viewholder viewholder;

        if (view == null){
            viewholder = new Viewholder();

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);

            viewholder.artistTxt = (TextView) view.findViewById(R.id.artistTxt);
            viewholder.songNameTxt = (TextView) view.findViewById(R.id.songNameTxt);
            viewholder.playB = (ImageView) view.findViewById(R.id.playB);
            viewholder.stopB = (ImageView) view.findViewById(R.id.stopB);
            view.setTag(viewholder);
        } else {
            viewholder = (Viewholder) view.getTag();
        }

        final Song song = arrayList.get(position);

        viewholder.artistTxt.setText(song.getArtist());
        viewholder.songNameTxt.setText(song.getSongName());

        viewholder.playB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentSong == null || song != currentSong){
                    currentSong = song;

                    mediaPlayer = MediaPlayer.create(context, song.getSong());
                }
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    viewholder.playB.setImageResource(R.drawable.play_black);
                } else {
                    mediaPlayer.start();
                    viewholder.playB.setImageResource(R.drawable.pause_black);
                }
            }
        });

        viewholder.stopB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentSong != null){
                    mediaPlayer.stop();
                    mediaPlayer.release();

                    currentSong = null;
                    viewholder.playB.setImageResource(R.drawable.play_black);
                }
            }
        });

        return view;
    }
}
