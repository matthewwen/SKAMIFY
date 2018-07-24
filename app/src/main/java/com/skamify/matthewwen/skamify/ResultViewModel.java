package com.skamify.matthewwen.skamify;

import android.arch.lifecycle.ViewModel;
import com.skamify.matthewwen.skamify.objects.Track;

import java.util.ArrayList;

public class ResultViewModel extends ViewModel {

    private ArrayList<Track> allTracks;

    @Override
    protected void onCleared() {
        super.onCleared();
        allTracks.clear();
    }

    public void setAllTracks(ArrayList<Track> allTracks){
        this.allTracks = allTracks;
    }

    public ArrayList<Track> getAllTracks(){
        if (allTracks == null){
            return new ArrayList<>();
        }
        return allTracks;
    }

    public boolean isSetUp(){
        if (allTracks == null){
            return false;
        }
        return allTracks.size() != 0;
    }
}
