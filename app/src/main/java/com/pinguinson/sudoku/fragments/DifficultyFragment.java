package com.pinguinson.sudoku.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.flurry.android.FlurryAgent;
import com.pinguinson.sudoku.flurry.FlurryConstants;
import com.pinguinson.sudoku.MainActivity;
import com.pinguinson.sudoku.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pinguinson on 8/25/15.
 */
public class DifficultyFragment extends Fragment {

    private static final int[] BUTTON_IDS = {
            R.id.selectDifficulty1,
            R.id.selectDifficulty2,
            R.id.selectDifficulty3,
            R.id.selectDifficulty4,
            R.id.selectDifficulty5
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_difficulty, null);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button current = (Button) v;
                int level = Integer.valueOf(current.getTag().toString());
                //Log event to Flurry
                Map<String, String> map = new HashMap<>();
                map.put(FlurryConstants.LOG_LEVEL_SELECTION, String.valueOf(level));
                FlurryAgent.logEvent("Level selected", map);
                ((MainActivity) getActivity()).startNewGame(level);

            }
        };
        for (int BUTTON_ID : BUTTON_IDS) {
            Button current = (Button) view.findViewById(BUTTON_ID);
            current.setOnClickListener(listener);
        }
        return view;
    }
}
