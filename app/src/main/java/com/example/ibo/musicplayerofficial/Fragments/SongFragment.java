package com.example.ibo.musicplayerofficial.Fragments;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ibo.musicplayerofficial.R;


public class SongFragment extends Fragment {

    ScrollView scrollView;
    TextView songNameTV, lyricTxt;
    ImageView artistImg, artistImgBG, playBtn;
    SeekBar seekBar;

    MediaPlayer mediaPlayer;
    ObjectAnimator objectAnimator;

    String displayArtist;

    int getSong;

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
        playBtn = view.findViewById(R.id.playBDetail);
        scrollView = view.findViewById(R.id.scrollView);
        seekBar = view.findViewById(R.id.songLenght);

        //Call methods
        playBtn.setOnClickListener(new ClickPlaySong());
        seekBar.setOnSeekBarChangeListener(new SeekbarProgress());

        if (getArguments() != null) {
            songNameTV.setText(getArguments().getString("arg_artist") + " " + getArguments().getString("arg_songname"));
            artistImg.setImageResource(getArguments().getInt("arg_artistimg"));
            artistImgBG.setImageResource(getArguments().getInt("arg_artistimg"));
            lyricTxt.setText(getArguments().getString("arg_lyrics"));
            getSong = getArguments().getInt("arg_song");
        } else {
            Toast.makeText(getActivity(), "Bundle is null", Toast.LENGTH_SHORT).show();
        }
        displayArtist = songNameTV.getText().toString();

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
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            mSeekbarUpdateHandler.postDelayed(this, 10);
        }

    };

    /*Seekbar listener*/
    private class SeekbarProgress implements SeekBar.OnSeekBarChangeListener {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mediaPlayer.seekTo(progress);
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

    //set the details of the getSong i clicked on in my list
    //Method is implemented in MainFragment.java (inside listViewOnClick)
    private class ClickPlaySong implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            /*Set scrolling text with ObjectAnimator*/
            objectAnimator = ObjectAnimator.ofInt(scrollView, "scrollY", scrollView.getChildAt(0).
                    getHeight() - scrollView.getHeight());


            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(getActivity(), getSong);

                //Get the duration of getSong
                seekBar.setMax(mediaPlayer.getDuration());
            }

            if (!mediaPlayer.isPlaying()) {

                /*Start getSong*/
                mediaPlayer.start();
                playBtn.setImageResource(R.drawable.pause_big_orange);

                /*Start scrolling*/
                objectAnimator.setDuration(300000).setInterpolator(new LinearInterpolator());
                objectAnimator.setStartDelay(10000);
                objectAnimator.start();

                /*Call my handler to update my seekbar*/
                mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);

            } else {
                mediaPlayer.pause();
                playBtn.setImageResource(R.drawable.play_big);

                /*Remove the callback of the handler*/
                mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
            }
        }
    }

    public void stopSong() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
    }

    @Override
    public void onDestroy() {

        if (mediaPlayer != null){
            stopSong();
            super.onDestroy();
        } else {
            super.onDestroy();
        }

    }

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
