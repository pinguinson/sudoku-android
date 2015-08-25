package com.pinguinson.sudoku;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.flurry.android.FlurryAgent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.pinguinson.sudoku.flurry.FlurryConstants;
import com.pinguinson.sudoku.fragments.DifficultyFragment;
import com.pinguinson.sudoku.fragments.GameFragment;
import com.pinguinson.sudoku.fragments.ModeFragment;

public class MainActivity extends Activity {

    public static final String SAVE_FILE = "savedGame";

    protected SudokuApplication app;
    public ModeFragment modeFragment;
    public DifficultyFragment difficultyFragment;
    public GameFragment gameFragment;
    private FragmentTransaction fTrans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (SudokuApplication) getApplication();
        AdView mAdView = (AdView) findViewById(R.id.adMain);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        modeFragment = new ModeFragment();
        difficultyFragment = new DifficultyFragment();
        gameFragment = new GameFragment();
        fTrans = getFragmentManager().beginTransaction();
        fTrans.add(R.id.frameLayout, modeFragment);
        fTrans.addToBackStack(null);
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

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();
        if (count == 0) {
            super.onBackPressed();
        } else {
            Fragment fragment = getFragmentManager().findFragmentById(R.id.frameLayout);
            if (fragment instanceof GameFragment) {
                fromGameToMenu();
            } else {
                goToMainMenu();
            }
        }
    }

    public void continueGame(String load) {
        goToGame();
        gameFragment.continueGame(load);
    }

    public void startNewGame(int level) {
        goToGame();
        gameFragment.startNewGame(level);
    }

    public void goToDifficultySelection() {
        fTrans = getFragmentManager().beginTransaction();
        fTrans.setCustomAnimations(R.animator.normal_in, R.animator.normal_out,
                R.animator.backstack_in, R.animator.backstack_out);
        fTrans.replace(R.id.frameLayout, difficultyFragment);
        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    private void goToGame() {
        fTrans = getFragmentManager().beginTransaction();
        fTrans.setCustomAnimations(R.animator.normal_in, R.animator.normal_out,
                R.animator.backstack_in, R.animator.backstack_out);
        fTrans.replace(R.id.frameLayout, gameFragment);
        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    public void fromGameToMenu() {
        fTrans = getFragmentManager().beginTransaction();
        fTrans.setCustomAnimations(R.animator.backstack_in, R.animator.backstack_out,
                R.animator.normal_in, R.animator.normal_out);
        fTrans.replace(R.id.frameLayout, modeFragment);
        fTrans.addToBackStack(null);
        fTrans.commit();
    }

    public void goToMainMenu() {
        fTrans = getFragmentManager().beginTransaction();
        fTrans.setCustomAnimations(R.animator.normal_in, R.animator.normal_out,
                R.animator.backstack_in, R.animator.backstack_out);
        fTrans.replace(R.id.frameLayout, modeFragment);
        fTrans.addToBackStack(null);
        fTrans.commit();
    }
}
