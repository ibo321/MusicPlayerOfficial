package com.example.ibo.musicplayerofficial.Fragments;

import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.ibo.musicplayerofficial.R;


public class SongFragment extends Fragment {

    ScrollView scrollView;
    TextView songNameVIEW, lyricTxt;
    ImageView artistImgVIEW, playBVIEW, addToFavoriteBtn;
    SeekBar seekBar;

    MediaPlayer mediaPlayer;
    ObjectAnimator objectAnimator;

    String getSongName, getLyric, getArtist;
    int getSong, getArtistImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song, container, false);

        //find views
        songNameVIEW = view.findViewById(R.id.songNameTxt);
        artistImgVIEW = view.findViewById(R.id.artistImgDetail);
        lyricTxt = view.findViewById(R.id.lyricTxt);
        playBVIEW = view.findViewById(R.id.playBDetail);
        scrollView = view.findViewById(R.id.scrollView);
        seekBar = view.findViewById(R.id.songLenght);
        addToFavoriteBtn = view.findViewById(R.id.addTo_favoritesBtn);

        //set the values to the correct singer
        artistImgVIEW.setImageResource(getArtistImg);
        songNameVIEW.setText(getSongName);
        lyricTxt.setText(getLyric);

        //Call methods
        playBVIEW.setOnClickListener(new ClickPlaySong());
        addToFavoriteBtn.setOnClickListener(new OnAddToFavoritesClick());
        seekBar.setOnSeekBarChangeListener(new SeekbarProgress());


//        new SoapCall().execute();
        return view;
    }

    //Constantly update the seek bar according to the media player object with the help of a Runnable.
    //We are using this because only the main thread can update the UI.
    private Handler mSeekbarUpdateHandler = new Handler();
    private Runnable mUpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            mSeekbarUpdateHandler.postDelayed(this, 10);
        }

    };

    //Seekbar listener
    private class SeekbarProgress implements SeekBar.OnSeekBarChangeListener{
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mediaPlayer.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    //set the details of the getSong i clicked on in my list
    //Method is implemented in MainFragment.java (inside listViewOnClick)
    private class ClickPlaySong implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            //Set scrolling text with ObjectAnimator
            objectAnimator = ObjectAnimator.ofInt(scrollView, "scrollY", scrollView.getChildAt(0).getHeight() - scrollView.getHeight());

            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(getActivity(), getSong);

                //Get the duration of getSong
                seekBar.setMax(mediaPlayer.getDuration());
            }

            if (!mediaPlayer.isPlaying()) {

                //Start scrolling
                objectAnimator.setDuration(300000).setInterpolator(new LinearInterpolator());
                objectAnimator.setStartDelay(10000);
                objectAnimator.start();

                //Start getSong
                mediaPlayer.start();
                playBVIEW.setImageResource(R.drawable.pause_black);

                //Call my handler to update my seekbar
                mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);

            } else {
                mediaPlayer.pause();
                playBVIEW.setImageResource(R.drawable.play_black);

                //Remove the callback of the handler
                mSeekbarUpdateHandler.removeCallbacks(mUpdateSeekbar);
            }
        }
    }

    //Get getSong details of  the getSong clicked on
    //refer to MainFragment for the implementation
    public void getSongDetails(String artist, String songName, int artistImg, int song, String lyric) {
        this.getArtistImg = artistImg;
        this.getSong = song;
        this.getSongName = songName;
        this.getLyric = lyric;
        this.getArtist = artist;
    }

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

    //TODO: It put the right properties but FavoriteFragment cannot retrieve bundle (null)
    public class OnAddToFavoritesClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            SongFragment songFragment = new SongFragment();

            Bundle bundle = new Bundle();

            bundle.putString("arg_artist", getArtist);
            bundle.putString("arg_songName", getSongName);
            bundle.putInt("arg_artistImg", getArtistImg);

            songFragment.setArguments(bundle);

            addToFavoriteBtn.setImageResource(R.drawable.favorite);

        }
    }
}
