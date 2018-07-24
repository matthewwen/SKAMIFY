package com.skamify.matthewwen.skamify.fragments;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.skamify.matthewwen.skamify.R;
import com.skamify.matthewwen.skamify.ResultViewModel;
import com.skamify.matthewwen.skamify.TrackAdapter;
import com.skamify.matthewwen.skamify.network.SongLyrics;
import com.skamify.matthewwen.skamify.objects.Track;

import java.util.ArrayList;

public class ResultFragment extends Fragment {

    private static final String TAG = ResultFragment.class.getSimpleName();

    private static final String UGSG_REQUEST_URL =
            "https://api.genius.com/search";
    private static final String AUTHENTICATION =
            "access_token";
    private static final String MATT_WEN_AUTH =
            "VddgJV3y641gIQuZrseAQmYKUl7PPsjjmq9Wm27FnIf3Yz2hVAStYN6mJLOs63zV";
    private static final String QUERYING =
            "q";

    //The keys
    public static final String RESULT_KEY = "result-key";

    //The loaders
    private static final int LOADER_ID = 0;

    ResultViewModel viewModel;
    TrackAdapter mAdapter;

    ProgressBar progressBar;

    public ResultFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_result, container, false);
        assert getArguments() != null;
        String result = getArguments().getString(RESULT_KEY);

        viewModel = ViewModelProviders.of(this).get(ResultViewModel.class);

        progressBar = view.findViewById(R.id.loading_data_pb);

        mAdapter = new TrackAdapter(viewModel.getAllTracks());

        RecyclerView recyclerView = view.findViewById(R.id.result_rv);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);

        if (!viewModel.isSetUp()){
            GetTrackList tempLoad = new GetTrackList();
            tempLoad.execute(result);
        }

        return view;
    }

    @SuppressLint("StaticFieldLeak")
    class GetTrackList extends AsyncTask<String, Void, ArrayList<Track>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected ArrayList<Track> doInBackground(String... result) {
            if (result == null || result.length == 0){
                return null;
            }
            Uri.Builder builder = Uri.parse(UGSG_REQUEST_URL).buildUpon();
            builder.appendQueryParameter(AUTHENTICATION, MATT_WEN_AUTH);
            builder.appendQueryParameter(QUERYING, result[0]);
            //builder.appendQueryParameter(ORDER, ORDER_PREFERENCE);
            return SongLyrics.fetchTrackData(builder.build().toString());
        }

        @Override
        protected void onPostExecute(ArrayList<Track> tracks) {
            super.onPostExecute(tracks);
            progressBar.setVisibility(View.INVISIBLE);
            viewModel.setAllTracks(tracks);
            Log.v(TAG, "The size of the results: " + tracks.size());
            mAdapter.updateTrackList(tracks);
        }
    }
}
