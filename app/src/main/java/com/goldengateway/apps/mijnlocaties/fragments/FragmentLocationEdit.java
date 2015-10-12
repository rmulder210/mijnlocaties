package com.goldengateway.apps.mijnlocaties.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.goldengateway.apps.mijnlocaties.R;
import com.goldengateway.apps.mijnlocaties.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentLocationEdit extends Fragment {

    TextView etLocatieId;
    EditText etOmschrijving;
    EditText etLgraad;
    EditText etBgraad;

    public FragmentLocationEdit() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_location_edit, container, false);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivity currentActivity = (MainActivity) getActivity();

        etLocatieId    = (TextView) view.findViewById(R.id.etLocatieId);
        etOmschrijving = (EditText) view.findViewById(R.id.etOmschrijving);
        etLgraad       = (EditText) view.findViewById(R.id.etLgraad);
        etBgraad       = (EditText) view.findViewById(R.id.etBgraad);

        etLocatieId.setText(currentActivity.editLoc.getLocatieid()+"");
        etOmschrijving.setText(currentActivity.editLoc.getOmschrijving());
        etLgraad.setText(currentActivity.editLoc.getLgraad()+"");
        etBgraad.setText(currentActivity.editLoc.getBgraad()+"");

        initButtons(view);

    }

    public void initButtons( View v) {

        final MainActivity currentActivity = (MainActivity) getActivity();
        Button btnSave = (Button) v.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                currentActivity.editLoc.setOmschrijving(etOmschrijving.getText().toString());
                currentActivity.editLoc.setLgraad(Double.parseDouble(etLgraad.getText().toString()));
                currentActivity.editLoc.setBgraad(Double.parseDouble(etBgraad.getText().toString()));

                currentActivity.mDbHelper.saveLocatie(currentActivity.editLoc);
                currentActivity.showFragmentMain();
            }
        });

        Button btnCancel = (Button) v.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentActivity.showFragmentMain();
            }
        });

        Button btnDelete = (Button) v.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                currentActivity.mDbHelper.deleteLocatie( currentActivity.editLoc.getLocatieid()+"");
                currentActivity.showFragmentMain();
            }
        });
    }


}
