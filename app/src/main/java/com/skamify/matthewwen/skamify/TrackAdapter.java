package com.skamify.matthewwen.skamify;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skamify.matthewwen.skamify.network.SongLyrics;
import com.skamify.matthewwen.skamify.objects.Track;

import java.util.ArrayList;

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {

    private static final String TAG = TrackAdapter.class.getSimpleName();

    private ArrayList<Track> allTracks;

    public TrackAdapter(ArrayList<Track> allTracks){
        this.allTracks = allTracks;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycle_item_track_listing, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Track track = allTracks.get(i);
        viewHolder.theImage.setImageDrawable(
                track.getDrawable()
        );
        viewHolder.songName.setText(track.getSongName());
        viewHolder.artistName.setText(track.getArtistName());
    }

    @Override
    public int getItemCount() {
        return allTracks.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView theImage;
        TextView songName;
        TextView artistName;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            theImage = itemView.findViewById(R.id.track_iv);
            songName = itemView.findViewById(R.id.track_name_tv);
            artistName = itemView.findViewById(R.id.track_artist_tv);
        }
    }

    //set the data
    public void updateTrackList(ArrayList<Track> allTracks){
        this.allTracks = allTracks;
        notifyDataSetChanged();
    }
}
