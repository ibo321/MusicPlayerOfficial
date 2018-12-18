package com.example.ibo.musicplayerofficial.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class ListViewAdapter extends BaseAdapter {

    //Create variables
    int layout;
    ArrayList<Song> arrayList;
    Context context;
    FileOutputStream file;
    ObjectOutputStream object;
    int counter = 0;
    ArrayList<Song> list;

    String filePath;
    File getFile;

    //Constructor
    public ListViewAdapter(int layout, ArrayList<Song> arrayList, Context context) {
        this.layout = layout;
        this.arrayList = arrayList;
        this.context = context;
    }

    //ViewHolder class holding my views
    private class Viewholder {
        TextView artistTxt, songNameTxt;
        CircleImageView artistImg;
        ImageView favBtn;
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

            //Set my view to viewholder
            view.setTag(viewholder);
        } else {
            viewholder = (Viewholder) view.getTag();
        }

        //Assign song to my arraylist
        final Song song = arrayList.get(position);

        //Set my views to their resources
        viewholder.artistImg.setImageResource(song.getArtistImg());
        viewholder.artistTxt.setText(song.getArtist());
        viewholder.songNameTxt.setText(song.getSongName());

        list = new ArrayList<>();

        //Create filepath
        filePath = context.getFilesDir().getPath() + "/test.txt";
        getFile = new File(filePath);

        viewholder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //Create fileoutputstream and objectoutputstream
                    file = new FileOutputStream(getFile);
                    object = new ObjectOutputStream(file);

                    // write object to file

                    //TODO: Why doesnt this work? ==> FIXED BY NEXT LINE OF CODE!
//                  arrayList.add(object.writeObject(song));

                    list.add(song);
                    object.writeObject(list);

                    //region Can also use this to write data (instead of class object)
//                    fileout = context.openFileOutput("mytextfile.txt", MODE_PRIVATE);
//                    outputWriter = new OutputStreamWriter(fileout);
//                    outputWriter.write(song.getSongName());
//                    outputWriter.close();
                    //endregion

                    // closing resources
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

                    //display file saved message
                    counter++;
                    Toast.makeText(context, "File(s) saved: " + counter,
                            Toast.LENGTH_SHORT).show();
                    Log.d("list", "Added to the list: " + song.getArtist() + " " + song.getSongName());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                viewholder.favBtn.setImageResource(R.drawable.favorite);
            }
        });

        return view;
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