package com.pinguinson.sudoku;

import android.app.Activity;
import android.app.Fragment;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = (SudokuApplication) getApplication();
        setAds();
        createFragments();
        setDefaultFragment();
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

    private void setAds() {
        if (getResources().getBoolean(R.bool.is_admob_enabled)) {
            AdView mAdView = (AdView) findViewById(R.id.adMain);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);
        }
    }

    private void createFragments() {
        modeFragment = new ModeFragment();
        difficultyFragment = new DifficultyFragment();
        gameFragment = new GameFragment();
    }

    private void setDefaultFragment() {
        getFragmentManager().beginTransaction()
                .add(R.id.frameLayout, modeFragment)
                .addToBackStack(null)
                .commit();
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
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.normal_in,    R.animator.normal_out,
                                     R.animator.backstack_in, R.animator.backstack_out)
                .replace(R.id.frameLayout, difficultyFragment)
                .addToBackStack(null)
                .commit();
    }

    private void goToGame() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.normal_in,    R.animator.normal_out,
                                     R.animator.backstack_in, R.animator.backstack_out)
                .replace(R.id.frameLayout, gameFragment)
                .addToBackStack(null)
                .commit();
    }

    public void fromGameToMenu() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.backstack_in, R.animator.backstack_out,
                                     R.animator.normal_in,    R.animator.normal_out)
                .replace(R.id.frameLayout, modeFragment)
                .addToBackStack(null)
                .commit();
    }

    public void goToMainMenu() {
        getFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.normal_in,    R.animator.normal_out,
                                     R.animator.backstack_in, R.animator.backstack_out)
                .replace(R.id.frameLayout, modeFragment)
                .addToBackStack(null)
                .commit();
    }
}
