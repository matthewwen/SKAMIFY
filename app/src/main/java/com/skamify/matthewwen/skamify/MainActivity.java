package com.skamify.matthewwen.skamify;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.skamify.matthewwen.skamify.fragments.IntroFragment;

public class MainActivity extends AppCompatActivity {

    private static final String ADDED_TO_BACK_KEY = "added-to-back-key";

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Global.addFragmentBack(getSupportFragmentManager(), new IntroFragment(), R.id.main_fl);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount() == 0){
            finish();
        }
    }
}
