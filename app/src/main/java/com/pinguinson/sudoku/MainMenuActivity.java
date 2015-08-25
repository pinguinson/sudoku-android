package com.pinguinson.sudoku;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class MainMenuActivity extends Activity {
    public static final String DIFFICULTY = "difficulty";
    public static final int DIFFICULTY_EASY = 0;
    public static final int DIFFICULTY_NORMAL = 1;
    public static final int DIFFICULTY_HARD = 2;
    public static final int DIFFICULTY_INSANE = 3;

    public static final int[] BUTTON_IDS = {
            R.id.selectDifficulty1,
            R.id.selectDifficulty2,
            R.id.selectDifficulty3,
            R.id.selectDifficulty4,
            R.id.selectDifficulty5
    };

    private static final String[] level = {"Beginner", "Easy", "Medium", "Hard", "Evil"};

    public static final String MODE = "game mode";
    public static final int MODE_CONTINUE = 0;
    public static final int MODE_NEW = 1;

    public static final String LOAD = "loaded file";
    public static final String SAVE_FILE = "savedGame";

    protected SudokuApplication app;
    FragmentMode fragmentMode;
    FragmentDifficulty fragmentDifficulty;
    FragmentTransaction fTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        app = (SudokuApplication) getApplication();
        AdView mAdView = (AdView) findViewById(R.id.adMain);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        fragmentMode = new FragmentMode();
        fragmentDifficulty = new FragmentDifficulty();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frameLayout, fragmentMode);
        fTrans.commit();
    }



    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, FlurryConstants.API_KEY);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(this);
    }

    public void continueGame() {
        String filename = SAVE_FILE;
        File file = new File(getFilesDir(), filename);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
            }
            br.close();
        } catch (IOException e) {
            Log.d("LOAD", "failed to load saved game");
            return;
        }
        String load = text.toString();
        Log.d("LOAD", "loaded saved game");

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(MODE, MODE_CONTINUE);
        intent.putExtra(LOAD, load);
        startActivity(intent);
    }

    public void goToDifficultySelection() {
        fTrans = getFragmentManager().beginTransaction();
        fTrans.setCustomAnimations(R.animator.normal_in, R.animator.normal_out,
                R.animator.backstack_in, R.animator.backstack_out);
        fTrans.replace(R.id.frameLayout, fragmentDifficulty);
        fTrans.addToBackStack(null);
        fTrans.commit();
    }
}
