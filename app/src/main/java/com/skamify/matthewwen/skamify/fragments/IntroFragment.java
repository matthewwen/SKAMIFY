package com.skamify.matthewwen.skamify.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.skamify.matthewwen.skamify.Global;
import com.skamify.matthewwen.skamify.R;

import java.util.Locale;
import java.util.function.Function;

public class IntroFragment extends Fragment {

    private static final String TAG = IntroFragment.class.getSimpleName();

    public IntroFragment(){ }

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_intro, container, false);
        ImageButton imageButton = view.findViewById(R.id.listen_iv);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "fragment should update");
                assert getFragmentManager() != null;
                Global.addFragment(getFragmentManager(), new ListenFragment(), R.id.main_fl);
            }
        });
        return view;
    }

}
