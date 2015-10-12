package com.goldengateway.apps.mijnlocaties.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goldengateway.apps.mijnlocaties.R;
import com.goldengateway.apps.mijnlocaties.activities.MainActivity;
import com.goldengateway.apps.mijnlocaties.model.Locatie;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentAddCurrentLocation extends Fragment {

    Context ctx;

    EditText etCurLocDescription;
    TextView tvCurLocLongitudeValue;
    TextView tvCurLocLatitudeValue;


    public FragmentAddCurrentLocation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_current_location, container, false);

        etCurLocDescription    = (EditText) v.findViewById(R.id.etCurLocDescription);

        tvCurLocLongitudeValue = (TextView) v.findViewById(R.id.tvCurLocLongitudeValue);
        tvCurLocLatitudeValue  = (TextView) v.findViewById(R.id.tvCurLocLatitudeValue);

        final MainActivity currentActivity = (MainActivity) getActivity();
        tvCurLocLongitudeValue.setText(currentActivity.mLongitude + "");
        tvCurLocLatitudeValue.setText(currentActivity.mLatitude + "");


        Button btnAddCurLocToDb = (Button) v.findViewById(R.id.btnAddCurLocToDb);
        btnAddCurLocToDb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Locatie loc = new Locatie();
                loc.setOmschrijving(etCurLocDescription.getText().toString());
                loc.setNaamkort("");
                loc.setAdres("");
                loc.setPostcode("");
                loc.setBgraad( currentActivity.mLatitude);
                loc.setLgraad( currentActivity.mLongitude);

                currentActivity.mDbHelper.insertLocatie( loc);

                Toast.makeText( currentActivity, R.string.location_added, Toast.LENGTH_SHORT).show();

                // when done, return to main fragment
                currentActivity.showFragmentMain();
            }
        });

        return v;
    }


}
