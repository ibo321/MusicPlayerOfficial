package com.example.ibo.musicplayerofficial.Adapters;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.Fragments.SongFragment;
import com.example.ibo.musicplayerofficial.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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

    private class Viewholder {
        TextView artistTxt, songNameTxt;
        ImageView playB, stopB;
        CircleImageView artistImg;
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

        if (view == null) {
            viewholder = new Viewholder();

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);

            viewholder.artistTxt = (TextView) view.findViewById(R.id.artistTxt);
            viewholder.songNameTxt = (TextView) view.findViewById(R.id.songNameTxt);
            viewholder.playB = (ImageView) view.findViewById(R.id.playB);
            viewholder.stopB = (ImageView) view.findViewById(R.id.stopB);
            viewholder.artistImg = (CircleImageView) view.findViewById(R.id.artistImg);

            view.setTag(viewholder);
        } else {
            viewholder = (Viewholder) view.getTag();
        }

        final Song song = arrayList.get(position);

        viewholder.artistImg.setImageResource(song.getArtistImg());
        viewholder.artistTxt.setText(song.getArtist());
        viewholder.songNameTxt.setText(song.getSongName());

        //get all songs
        mediaPlayer = MediaPlayer.create(context, song.getSong());

        viewholder.playB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (currentSong == null) {
                    mediaPlayer = MediaPlayer.create(context, song.getSong());
                }

                //if mediaplayer is not null and my current song is not equal to the new song i clicked on
                if (mediaPlayer != null && currentSong != song) {

                    //resets the mediaplayer and creates a new song from the position in the list
                    mediaPlayer.reset();

                    mediaPlayer = MediaPlayer.create(context, song.getSong());
                    viewholder.playB.setImageResource(R.drawable.play_black);

                    mediaPlayer.start();
                    viewholder.playB.setImageResource(R.drawable.pause_black);
                } else {
                    mediaPlayer.pause();
                    viewholder.playB.setImageResource(R.drawable.play_black);
                }

                //check if current song is null or the newly clicked song is equal to my current song
                //if true then assign the newly clicked song as my CURRENT one
                //--so it doesnt play the same song for every single one
                if (currentSong == null || song != currentSong) {
                    currentSong = song;
                }
            }
        });

        //stop song
        viewholder.stopB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //stops my current song and make it null
                if (currentSong != null) {
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
