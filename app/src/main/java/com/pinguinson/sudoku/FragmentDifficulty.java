package com.pinguinson.sudoku;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.flurry.android.FlurryAgent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by pinguinson on 8/25/15.
 */
public class FragmentDifficulty extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_difficulty, null);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button current = (Button) v;
                int difficulty = Integer.valueOf(current.getTag().toString());
                Intent intent = new Intent(getActivity(), GameActivity.class);
                intent.putExtra(MainMenuActivity.DIFFICULTY, difficulty);
                intent.putExtra(MainMenuActivity.MODE, MainMenuActivity.MODE_NEW);

                //Log event to Flurry
                Map<String, String> map = new HashMap<>();
                map.put(FlurryConstants.LOG_LEVEL_SELECTION, String.valueOf(difficulty));
                FlurryAgent.logEvent("Level selected", map);
                startActivity(intent);

            }
        };
        for (int BUTTON_ID : MainMenuActivity.BUTTON_IDS) {
            Button current = (Button) view.findViewById(BUTTON_ID);
            current.setOnClickListener(listener);
        }
        return view;
    }


}
