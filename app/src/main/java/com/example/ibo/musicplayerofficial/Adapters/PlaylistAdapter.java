package com.example.ibo.musicplayerofficial.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ibo.musicplayerofficial.Classes.Playlist;
import com.example.ibo.musicplayerofficial.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.SongHolder> {

    private List<Playlist> playLists = new ArrayList<>();

    @NonNull
    @Override
    public SongHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_playlist_customlayout, parent, false);

        return new SongHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SongHolder holder, int position) {
        Playlist playlist = playLists.get(position);
        holder.playlistName.setText(playlist.getPlaylist_name());
        holder.playlistGenre.setText(playlist.getPlaylist_genre());
        holder.playlistSongCount.setText(playlist.getPlaylist_songs());
    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    public void setPlayLists(List<Playlist> playLists){
        this.playLists = playLists;
        notifyDataSetChanged();
    }
    class SongHolder extends RecyclerView.ViewHolder {
        private TextView playlistName;
        private TextView playlistGenre;
        private TextView playlistSongCount;
        private ImageView playlistImage;
        private ImageView playlistDelete;

        public SongHolder(@NonNull View itemView) {
            super(itemView);
            playlistName = itemView.findViewById(R.id.playlist_name);
            playlistGenre = itemView.findViewById(R.id.playlist_genre);
            playlistSongCount = itemView.findViewById(R.id.playlist_songCount);
            playlistImage = itemView.findViewById(R.id.playlist_image);
            playlistDelete = itemView.findViewById(R.id.playlist_delete);
        }
    }
}
