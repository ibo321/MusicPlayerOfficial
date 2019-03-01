package com.example.ibo.musicplayerofficial.Fragments;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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

import com.bumptech.glide.Glide;
import com.example.ibo.musicplayerofficial.Classes.Song;
import com.example.ibo.musicplayerofficial.R;
import com.example.ibo.musicplayerofficial.ViewModel.SharedViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class SongFragment extends Fragment {

    String TAG = "timecalculator";
    ScrollView scrollView;
    TextView songNameTV, lyricTxt, currentDur, songLenght;
    ImageView artistImg, artistImgBG, playBtn, playNextBtn, playPreviousBtn;
    SeekBar seekBar;

    MediaPlayer mediaPlayer;
    ObjectAnimator objectAnimator;
    List<Song> arrayList;
    String displayArtist;
    String getSong;
    Song currentSong;
    SharedViewModel viewModel;
    Song mySong;

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

        Log.d(TAG, "Finding views: " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));

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
        Log.d(TAG, "Views found: " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        //Call inner class methods
        playBtn.setOnClickListener(new OnClickPlaySong());
        playNextBtn.setOnClickListener(new OnClickNextSong());
        playPreviousBtn.setOnClickListener(new OnClickPreviousSong());
        seekBar.setOnSeekBarChangeListener(new SeekbarProgress());

        //region Replaced Bundle with Viewmodel
        //        if (getArguments() != null) {
        //
        //            song = (Song) getArguments().getSerializable("arg_song");
        //
        //            Glide.with(getActivity()).load(song.getArtistImg()).into(artistImg);
        //            Glide.with(getActivity()).load(song.getArtistImg()).into(artistImgBG);
        //            getSong = song.getSong();
        //            songNameTV.setText(song.getArtist() + " " + song.getSongName());
        //            lyricTxt.setText(song.getLyrics());
        //        } else {
        //            Toast.makeText(getActivity(), "Bundle is null", Toast.LENGTH_SHORT).show();
        //        }
        //endregion

        Log.d(TAG, "Reading song from viewmodel: " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
        mySong = viewModel.getClickedSong().getValue();

        Glide.with(getActivity()).load(mySong.getArtistImg()).into(artistImg);
        Log.d(TAG, "Inserting artist image background: " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
        Glide.with(getActivity()).load(mySong.getArtistImg()).into(artistImgBG);
        Log.d(TAG, "Inserted both artist images: " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + " " + "Fetching song..");

        getSong = mySong.getSong();
        songNameTV.setText(mySong.getArtist() + " " + mySong.getSongName());
        lyricTxt.setText(mySong.getLyrics());
        displayArtist = "Now playing " + songNameTV.getText().toString();

        /*Get whole list of songs from my viewmodel*/
        arrayList = viewModel.getAllSongs().getValue();

        //TODO: Creating the MediaPlayer object takes too long!
        /*Get audio-file*/
        mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(getSong));
        Log.d(TAG, "Mediaplayer object set. Getting song duration..: " + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));

        /*Set the duration of the song*/
        songDuration(mediaPlayer.getDuration());

        //new SoapCall().execute();

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(displayArtist);
        return view;
    }

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

            seekBar.setMax(mediaPlayer.getDuration());

            if (!mediaPlayer.isPlaying()) {

                mediaPlayer.start();
                playBtn.setImageResource(R.drawable.ic_pause);

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
        playBtn.setImageResource(R.drawable.ic_action_play);

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
                playBtn.setImageResource(R.drawable.ic_pause);

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
}