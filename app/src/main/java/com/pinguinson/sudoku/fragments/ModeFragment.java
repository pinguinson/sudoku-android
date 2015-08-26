package com.pinguinson.sudoku.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.pinguinson.sudoku.MainActivity;
import com.pinguinson.sudoku.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by pinguinson on 8/25/15.
 */
public class ModeFragment extends Fragment {

    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mode, null);
        mainActivity = ((MainActivity) getActivity());

        Button buttonContinue = (Button) view.findViewById(R.id.buttonContinueGame);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadGame();
            }
        });
        Button newGame = (Button) view.findViewById(R.id.buttonNewGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.goToDifficultySelection();
            }
        });
        return view;
    }

    private void loadGame() {
        String filename = MainActivity.SAVE_FILE;
        File file = new File(mainActivity.getFilesDir(), filename);
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
        mainActivity.continueGame(load);
    }
}
