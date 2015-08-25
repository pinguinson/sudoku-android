package com.pinguinson.sudoku;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by pinguinson on 8/25/15.
 */
public class FragmentMode extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mode, null);

        Button buttonContinue = (Button) view.findViewById(R.id.buttonContinueGame);
        buttonContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainMenuActivity) getActivity()).continueGame();
            }
        });
        Button newGame = (Button) view.findViewById(R.id.buttonNewGame);
        newGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainMenuActivity) getActivity()).goToDifficultySelection();
            }
        });
        return view;
    }
}
