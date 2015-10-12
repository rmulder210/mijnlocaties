package com.goldengateway.apps.mijnlocaties.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.goldengateway.apps.mijnlocaties.R;
import com.goldengateway.apps.mijnlocaties.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 *
 * TODO: time request van Uber implementeren
 *
 *
 *
 */
public class FragmentB extends Fragment {


    public FragmentB() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater
                                     inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_b, container, false);
        Button button = (Button) v.findViewById(R.id.btnBackToMain);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity currentActivity = (MainActivity) getActivity();
                currentActivity.showFragmentMain();
            }
        });


        return v;
    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


}
