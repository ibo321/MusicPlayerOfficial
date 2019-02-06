package com.example.ibo.musicplayerofficialv2.Fragments;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ibo.musicplayerofficialv2.Classes.Song;
import com.example.ibo.musicplayerofficialv2.R;
import com.example.ibo.musicplayerofficialv2.ViewModel.SharedViewModel;

import java.util.ArrayList;

public class SongFragment extends Fragment {

    String TAG = "songfragviews";
    ScrollView scrollView;
    TextView songNameTV, lyricTxt, currentDur, songLenght;
    ImageView artistImg, artistImgBG, playBtn, playNextBtn, playPreviousBtn;
    SeekBar seekBar;

    MediaPlayer mediaPlayer;
    ObjectAnimator objectAnimator;
    ArrayList<Song> arrayList;
    String displayArtist;
    String getSong;
    Song currentSong;
    SharedViewModel viewModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        //find views
        songNameTV = view.findViewById(R.id.songNameTxt);
        artistImg = view.findViewById(R.id.songFrag_artistImg);
        artistImgBG = view.findViewById(R.id.songFrag_artistImgBG);
        lyricTxt = view.findViewById(R.id.lyricTxt);
        playBtn = view.findViewById(R.id.songFrag_playBtn);
        scrollView = view.findViewById(R.id.scrollView);
        seekBar = view.findViewById(R.id.songLenght);
        playNextBtn = view.findViewById(R.id.songFrag_playNextBtn);
        playPreviousBtn = view.findViewById(R.id.songFrag_previousBtn);
        songLenght = view.findViewById(R.id.songFrag_songLength);
        currentDur = view.findViewById(R.id.songFrag_currentDur);

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        //Call inner class methods
        playBtn.setOnClickListener(new OnClickPlaySong());
        playNextBtn.setOnClickListener(new OnClickNextSong());
        playPreviousBtn.setOnClickListener(new OnClickPreviousSong());
        seekBar.setOnSeekBarChangeListener(new SeekbarProgress());

        if (getArguments() != null) {

            Song song = new Song();
            song = (Song) getArguments().getSerializable("arg_song");

            Glide.with(getActivity())
                    .load(song.getArtistImg())
                    .into(artistImg);
            Glide.with(getActivity())
                    .load(song.getArtistImg())
                    .into(artistImgBG);
            getSong = song.getSong();
            songNameTV.setText(song.getArtist() + " " + song.getSongName());
            lyricTxt.setText(song.getLyrics());
        } else {
            Toast.makeText(getActivity(), "Bundle is null", Toast.LENGTH_SHORT).show();
        }
        displayArtist = "Now playing " + songNameTV.getText().toString();

        arrayList = viewModel.getSongs().getValue();
        //Set the mediaplayer object
        mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(getSong));

        //Set the duration of the song
        songDuration(mediaPlayer.getDuration());

        //new SoapCall().execute();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(displayArtist);
        Log.d(TAG, "onCreateView: isCalled");
        return view;
    }

    //    @Override
    //    public boolean onOptionsItemSelected(MenuItem item) {
    //        switch (item.getItemId()){
    //            case R.id.action_search:
    //                item.setVisible(false);
    //                break;
    //        }
    //        return super.onOptionsItemSelected(item);
    //    }
    //
    //    @Override
    //    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    //        super.onCreateOptionsMenu(menu, inflater);
    //        inflater.inflate(R.menu.menu_actionbar, menu);
    //        MenuItem item = menu.findItem(R.id.action_search);
    //        item.setVisible(false);
    //    }
    //
    //    @Override
    //    public void onPrepareOptionsMenu(Menu menu) {
    //        super.onPrepareOptionsMenu(menu);
    //        menu.findItem(R.id.action_search).setVisible(false);
    //    }

    /*Constantly update the seek bar according to the media player object with the help of a Runnable.
        We are using this because only the main thread can update the UI.*/
    private Handler mSeekbarUpdateHandler = new Handler();

    private Runnable mUpdateSeekbar = new Runnable() {
        @Override
        public void run() {

            if (mediaPlayer != null) {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                updatePlayer(mediaPlayer.getCurrentPosition());
                mSeekbarUpdateHandler.postDelayed(this, 1000);
            } else {
                mSeekbarUpdateHandler.removeCallbacks(this);
            }
        }
    };

    /*Seekbar listener*/
    private class SeekbarProgress implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {

                if (mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                }
            }
        }

        //region Unused seekbar methods
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
        //endregion
    }

    //Inner class to play next song
    private class OnClickNextSong implements OnClickListener {
        @Override
        public void onClick(View v) {
            playNextSong();
        }
    }

    //Inner class to play previous song
    private class OnClickPreviousSong implements OnClickListener {
        @Override
        public void onClick(View v) {

            /*Reset the mediaplayer*/
            if (mediaPlayer != null || currentSong != null) {
                mediaPlayer.stop();

                mediaPlayer.release();
                mediaPlayer = null;
            }

            for (int i = 0; i < arrayList.size(); i++) {

                /*If the position in the arraylist is equal to my song*/
                if (arrayList.get(i).getSong() == getSong) {

                    /*Loop to the next index in arraylist*/
                    i--;

                    /*Reset the index to 0 if reached last index in arraylist*/
                    if (i < 0) {
                        i = 0;
                    }

                    /*Get next index in arraylist to currentSong variable*/
                    currentSong = arrayList.get(i);

                    mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(currentSong.getSong()));
                    seekBar.setMax(mediaPlayer.getDuration());

                    songDuration(mediaPlayer.getDuration());
                    songNameTV.setText(currentSong.getArtist() + " " + currentSong.getSongName());
                    artistImg.setImageURI(Uri.parse(currentSong.getArtistImg()));
                    artistImgBG.setImageURI(Uri.parse(currentSong.getArtistImg()));
                    lyricTxt.setText(currentSong.getLyrics());

                    Glide.with(getActivity()).load(currentSong.getArtistImg()).into(artistImg);
                    Glide.with(getActivity()).load(currentSong.getArtistImg()).into(artistImgBG);
                    mediaPlayer.start();

                    mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                    mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);

                    /*Assign the current song to getSong variable*/
                    getSong = currentSong.getSong();

                    break;
                }
            }
        }
    }

    /*set the details of the getSong i clicked on in my list
    Method is implemented in MainFragment.java (inside listViewOnClick)*/
    private class OnClickPlaySong implements OnClickListener {
        @Override
        public void onClick(View v) {

            /*Set scrolling text with ObjectAnimator*/
            objectAnimator = ObjectAnimator.ofInt(scrollView, "scrollY", scrollView.getChildAt(0).
                    getHeight() - scrollView.getHeight());
            //            if (mediaPlayer == null) {
            //
            //
            //                //Get the duration of getSong
            //                seekBar.setMax(mediaPlayer.getDuration());
            //            }
            seekBar.setMax(mediaPlayer.getDuration());
            if (!mediaPlayer.isPlaying()) {

                mediaPlayer.start();
                playBtn.setImageResource(R.drawable.pause_big_orange);

                /*Call my handler to update my seekbar*/
                mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);

                /*Start scrolling*/
                objectAnimator.setDuration(29000).setInterpolator(new LinearInterpolator());
                objectAnimator.setStartDelay(8000);
                objectAnimator.start();

            } else {
                pauseSong();
            }
        }
    }

    public void pauseSong() {
        mediaPlayer.pause();
        playBtn.setImageResource(R.drawable.play_big);

        /*Remove the callback of the handler*/
        mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
    }

    public void stopSong() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
        }
    }

    @SuppressLint("SetTextI18n")
    private void playNextSong() {

        /*Reset the mediaplayer*/
        if (mediaPlayer != null || currentSong != null) {
            mediaPlayer.stop();

            mediaPlayer.release();
            mediaPlayer = null;
        }

        for (int i = 0; i < arrayList.size(); i++) {

            /*If the position in the arraylist is equal to my song*/
            if (arrayList.get(i).getSong() == getSong) {

                /*Loop to the next index in arraylist*/
                i++;

                /*Reset the index to 0 if reached last index in arraylist*/
                if (i >= arrayList.size()) {
                    i = 0;
                }

                /*Get next index in arraylist to currentSong variable*/
                currentSong = arrayList.get(i);

                mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(currentSong.getSong()));
                seekBar.setMax(mediaPlayer.getDuration());
                songDuration(mediaPlayer.getDuration());
                songNameTV.setText(currentSong.getArtist() + " " + currentSong.getSongName());
                artistImg.setImageURI(Uri.parse(currentSong.getArtistImg()));
                artistImgBG.setImageURI(Uri.parse(currentSong.getArtistImg()));
                lyricTxt.setText(currentSong.getLyrics());
                Glide.with(getActivity()).load(currentSong.getArtistImg()).into(artistImg);
                Glide.with(getActivity()).load(currentSong.getArtistImg()).into(artistImgBG);

                mediaPlayer.start();
                playBtn.setImageResource(R.drawable.pause_big_orange);

                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
                mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);

                /*Assign the current song to getSong variable*/
                getSong = currentSong.getSong();

                break;
            }
        }
    }

    //Set the current song duration
    private void updatePlayer(int currentDuration) {
        currentDur.setText("" + milliSecondsToTimer((long) currentDuration));
    }

    //Set the song duration
    private void songDuration(int duration) {
        songLenght.setText("" + milliSecondsToTimer((long) duration));
    }

    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    //    @Override
    //    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    //        super.onCreateOptionsMenu(menu, inflater);
    //
    //        MenuItem item = menu.findItem(R.id.action_search);
    //        item.setVisible(false);
    //
    //    }

    //region Lifecycle-methods
    @Override
    public void onStop() {
        if (mediaPlayer != null) {
            stopSong();
            super.onStop();
        } else {
            super.onStop();
        }
        Log.d(TAG, "onStop: isCalled");
    }

    @Override
    public void onPause() {

        if (mediaPlayer != null) {
            stopSong();
            super.onPause();
        }
        super.onPause();
        Log.d(TAG, "onPause: isCalled");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: isCalled");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: isCalled");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: isCalled");
    }
    //endregion
    //    @Override
    //    public void onDestroy() {
    //
    //        if (mediaPlayer != null) {
    //            stopSong();
    //            super.onDestroy();
    //        } else {
    //            super.onDestroy();
    //        }
    //        Log.d(TAG, "onDestroy: isCalled");
    //    }
    //Get getSong details of  the getSong clicked on
    //refer to MainFragment for the implementation
    //    public void getSongDetails(String artist, String songName, int artistImg, int song, String lyric) {
    //        this.getArtistImg = artistImg;
    //        this.getSong = song;
    //        this.getSongName = songName;
    //        this.getLyric = lyric;
    //        this.getArtist = artist;
    //    }

    //region Lyrics Api (cant make it work)
    //    //Get Lyrics API
    //    private class SoapCall extends AsyncTask<String, Object, String> {
    //
    //        public static final String NAMESPACE = "http://api.chartlyrics.com/";
    //        public static final String URL = "http://api.chartlyrics.com/apiv1.asmx?WSDL";
    //        public static final String METHOD_NAME = "SearchLyricDirect";
    //        public static final String SOAP_ACTION = NAMESPACE + METHOD_NAME;
    //
    //        public int TimeOut = 30000;
    //        String response;
    //        String soapArtist, soapSong;
    //
    //        @Override
    //        protected String doInBackground(String... strings) {
    //            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
    //            // add paramaters and values
    //            request.addProperty("soapArtist", soapArtist);
    //            request.addProperty("soapSong", soapSong);
    //            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
    //            envelope.dotNet = true;
    //            envelope.setOutputSoapObject(request);
    //
    //            HttpTransportSE transportSe = new HttpTransportSE(URL, TimeOut);
    //            transportSe.debug = true;
    //
    //            try {
    //                transportSe.call(NAMESPACE + METHOD_NAME, envelope);
    //            } catch (Exception e) {
    //                e.printStackTrace();
    //                Log.e("Error", e.toString());
    //            }
    //            return response;
    //        }
    //
    //        @Override
    //        protected void onPostExecute(String result) {
    //            super.onPostExecute(result);
    //
    //            if (result != null) {
    //                testSoapTxt.setText(result);
    //            } else {
    //                Toast.makeText(getActivity(), "Something went wrong with API", Toast.LENGTH_LONG).show();
    //            }
    //        }
    //    }
    //endregion
}
