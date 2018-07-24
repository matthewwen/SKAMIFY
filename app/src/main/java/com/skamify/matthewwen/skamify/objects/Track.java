package com.skamify.matthewwen.skamify.objects;

import android.graphics.drawable.Drawable;

public class Track {

    private String artistName;
    private String songName;

    private Drawable drawable;

    public Track(Drawable drawable, String songName, String artistName){
        this.drawable = drawable;  this.songName = songName; this.artistName = artistName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getSongName() {
        return songName;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
