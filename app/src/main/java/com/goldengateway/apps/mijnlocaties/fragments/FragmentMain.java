package com.goldengateway.apps.mijnlocaties.fragments;

import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.goldengateway.apps.mijnlocaties.R;
import com.goldengateway.apps.mijnlocaties.activities.MainActivity;
import com.goldengateway.apps.mijnlocaties.model.Locatie;
import com.goldengateway.apps.mijnlocaties.utils.DbHelper;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class FragmentMain extends Fragment {

    private DbHelper mDbHelper;
    ArrayList<Locatie> locaties;
    ListView locatieList;
    LocatieItemAdapter adapter;


    public FragmentMain() {
    }

    @Override
    public View onCreateView(LayoutInflater
                                     inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        ImageButton button = (ImageButton) v.findViewById(R.id.btnShowFragmentB);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity currentActivity = (MainActivity) getActivity();

                // count number of items checked.
                // if 1 or 2, store id(s) in variable(s), and proceed to other fragment.
                // otherwise, show message

                LinearLayout lin1, lin2;
                CheckBox cb;
                TableLayout tl;
                TableRow tr;
                TextView textView;
                String strId1 = "";
                String strId2 = "";

                int countChecked = 0;

                for( int i=0; i < locatieList.getCount(); i++) {

                    lin1     = (LinearLayout) locatieList.getChildAt(i);
                    tl       = (TableLayout) lin1.getChildAt(0);
                    lin2     = (LinearLayout) lin1.getChildAt(1);
                    cb       = (CheckBox) lin2.getChildAt(0);
                    tr       = (TableRow) tl.getChildAt(0);
                    textView = (TextView) tr.getChildAt(1); // positie 1 bevat het id

                    if (cb.isChecked()) {
                        countChecked++;
                        if ( countChecked == 1) {
                            strId1 = textView.getText().toString();
                        }
                        if ( countChecked == 2) {
                            strId2 = textView.getText().toString();
                        }
                    }
                }

                if (( countChecked == 1) || ( countChecked == 2)) {
                    if (countChecked == 1) {
                        currentActivity.showFragmentUberProducts( strId1);
                    }
                    if (countChecked == 2) {
//                        Toast.makeText(getActivity(), "strId1, strId2 = " + strId1 + ", " + strId2, Toast.LENGTH_LONG).show();
                        currentActivity.showFragmentB();  //TODO: rename FragmentB and implement time request
                    }

                } else {
                    Toast.makeText(getActivity(), R.string.check_one_or_two, Toast.LENGTH_LONG).show();
                }

            }
        });

        Button btnAddLocation = (Button) v.findViewById(R.id.btnAddLocation);
        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MainActivity currentActivity = (MainActivity) getActivity();
                currentActivity.showFragmentAddCurrentLocation();
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initData(view);

    }

    private void initData( View v) {
        locaties = new ArrayList<Locatie>();
        fetchData(); // vul de lijst vanuit de database

        locatieList = (ListView) v.findViewById(R.id.listViewLocaties);
        adapter   = new LocatieItemAdapter(getActivity(), R.layout.locatie_item, locaties);
        locatieList.setAdapter(adapter);
        locatieList.setItemsCanFocus(true);

    }


    private void fetchData() {

        mDbHelper = new DbHelper( getActivity());
        Cursor cur;
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String sel = "SELECT LOCATIEID, OMSCHRIJVING, LGRAAD, BGRAAD FROM LOCATIES";

        cur = db.rawQuery(sel, null);

        cur.moveToFirst();
        while (cur.isAfterLast() == false) {
            locaties.add( new Locatie( Integer.parseInt(cur.getString(0)),
                    cur.getString(1),
                    Double.parseDouble(cur.getString(2)),
                    Double.parseDouble(cur.getString(3))));
            cur.moveToNext();
        }
        cur.close();
    }

    public class LocatieItemAdapter extends ArrayAdapter<Locatie> {
        private ArrayList<Locatie> locaties;

        public LocatieItemAdapter(Context context, int textViewResourceId,
                                ArrayList<Locatie> locaties) {
            super(context, textViewResourceId, locaties);
            this.locaties = locaties;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;



            if (v == null) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                v = inflater.inflate(R.layout.locatie_item,null);
                v.setClickable(true); // necessary for making clickable in combination with checkbox
                v.setFocusable(true); // idem
            }

            Locatie locatie = locaties.get(position);
            if (locatie != null) {
                TextView tvLocatieid    = (TextView) v.findViewById(R.id.tvLocatie_id);
                TextView tvOmschrijving = (TextView) v.findViewById(R.id.tvLocatie_omschrijving);
                TextView tvLgraad       = (TextView) v.findViewById(R.id.tvLocatie_lgraad);
                TextView tvBgraad       = (TextView) v.findViewById(R.id.tvLocatie_bgraad);
                if (tvLocatieid != null) {
                    tvLocatieid.setText(locatie.getLocatieid()+"");
                }
                if (tvOmschrijving != null) {
                    tvOmschrijving.setText(locatie.getOmschrijving());
                }
                if (tvLgraad != null) {
                    tvLgraad.setText(locatie.getLgraad()+"");
                }
                if (tvBgraad != null) {
                    tvBgraad.setText(locatie.getBgraad() + "");
                }

            }

            // onClickListener necessary for making item clickable in combination with checkbox
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    LinearLayout lin1 = (LinearLayout) v;
                    TableLayout tl    = (TableLayout) lin1.getChildAt(0);
                    TableRow tr = (TableRow) tl.getChildAt(0);
                    TextView textView = (TextView) tr.getChildAt(1); // positie 1 bevat het id
                    String strId = textView.getText().toString();

                    MainActivity currentActivity = (MainActivity) getActivity();
                    currentActivity.editLoc = currentActivity.mDbHelper.getLocatieById(strId);

                    currentActivity.showFragmentLocationEdit();

                }
            });


            return v;
        }
    }



}
