package com.skamify.matthewwen.skamify.fragments;

import android.Manifest;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.skamify.matthewwen.skamify.Global;
import com.skamify.matthewwen.skamify.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ListenFragment extends Fragment implements RecognitionListener{

    static final int REQUEST_PERMISSION_KEY = 1;

    private Intent recognizerIntent;
    private SpeechRecognizer speech;
    private TextView mTextView;

    private static final String TAG = ListenFragment.class.getSimpleName();

    private String result;

    public ListenFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_listen, container, false);

        ImageButton imageButton = view.findViewById(R.id.listen_cancel_ib);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getFragmentManager() != null;
                Global.removeFragment(getFragmentManager(), ListenFragment.this);
            }
        });

        mTextView = view.findViewById(R.id.append_result_tv);

        startVoiceInput();
        speech.startListening(recognizerIntent);


        return view;
    }

    //error with permission
    private void startVoiceInput(){
        String[] PERMISSIONS = {Manifest.permission.RECORD_AUDIO};
        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Objects.requireNonNull(getActivity()), PERMISSIONS, REQUEST_PERMISSION_KEY);
        }

        if (ContextCompat.checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            return;
        }

        speech = SpeechRecognizer.createSpeechRecognizer(getContext());
        speech.setRecognitionListener(this);

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                getContext().getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true);

        /*
        Minimum time to listen in millis. Here 5 seconds
         */
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, TimeUnit.SECONDS.toMillis(5));
        recognizerIntent.putExtra("android.speech.extra.DICTATION_MODE", true);

    }


    @Override
    public void onReadyForSpeech(Bundle bundle) {

    }

    @Override
    public void onBeginningOfSpeech() {
    }

    @Override
    public void onRmsChanged(float v) {

    }

    @Override
    public void onBufferReceived(byte[] bytes) {

    }

    @Override
    public void onEndOfSpeech() {
        Log.v(TAG, "The speech ended");
        Fragment newFragment = new ResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ResultFragment.RESULT_KEY, result);
        Log.v(TAG, "This is result: " + result); 
        newFragment.setArguments(bundle);
        assert getFragmentManager() != null;
        Global.addFragment(getFragmentManager(), newFragment, R.id.main_fl);
        Global.removeFragment(getFragmentManager(), this);

    }

    @Override
    public void onError(int i) {

    }

    @Override
    public void onResults(Bundle bundle) {

    }

    @Override
    public void onPartialResults(Bundle bundle) {
        ArrayList<String> matches = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches == null){
            return;
        }
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < matches.size(); i++){
            text.append(matches.get(i)).append(" ");
        }
        result = text.toString();
        mTextView.setText(result);

    }

    @Override
    public void onEvent(int i, Bundle bundle) {

    }
}
