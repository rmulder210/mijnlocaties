package com.goldengateway.apps.mijnlocaties.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.goldengateway.apps.mijnlocaties.R;
import com.goldengateway.apps.mijnlocaties.fragments.FragmentAddCurrentLocation;
import com.goldengateway.apps.mijnlocaties.fragments.FragmentB;
import com.goldengateway.apps.mijnlocaties.fragments.FragmentLocationEdit;
import com.goldengateway.apps.mijnlocaties.fragments.FragmentMain;
import com.goldengateway.apps.mijnlocaties.fragments.FragmentUberProducts;
import com.goldengateway.apps.mijnlocaties.model.Locatie;
import com.goldengateway.apps.mijnlocaties.utils.DbHelper;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;

/*
  My Locations aims to
  - enter locations and to store in sqlite database.
  - export XML for use in other apps.
  - try out Uber API.

  After starting up a list is shown with (earlier) entered locations
  Each location in the list can be checked.
  If you have checked two locations, you can use the button Uber determine the price for the ride (it does not matter which location starting point is and what destination)
  If you checked one location, you can view the products at that location
  If you checked 0, 3 or more, then the uber button will not work.

*/

public class MainActivity extends Activity implements
        ConnectionCallbacks, OnConnectionFailedListener {

    // Location-related code is taken from https://github.com/googlesamples/android-play-location/blob/master/BasicLocationSample/app/src/main/java/com/google/android/gms/location/sample/basiclocationsample/MainActivity.java
    protected static final String TAG = "basic-location";
    protected GoogleApiClient mGoogleApiClient; // Provides the entry point to Google Play services.
    protected Location mLastLocation; // Represents a geographical location.
    public double mLatitude;
    public double mLongitude;
    public DbHelper mDbHelper;
    public Locatie editLoc; // created in onCreate of MainActivity; filled when clicked on a location in the list
    public static int mCountChecked = 0;

    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper = new DbHelper(getApplicationContext());

        editLoc = new Locatie();

        buildGoogleApiClient();

        showFragmentMain();
    }

    //----------------------------------------------------------------------------------------------
    public void showFragmentMain() {
        FragmentMain fragmentmain = new FragmentMain();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.layout_container, fragmentmain);
        ft.addToBackStack("fragment main");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    //----------------------------------------------------------------------------------------------
    public void showFragmentB() {
        FragmentB fragmentB = new FragmentB();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.layout_container, fragmentB);
        ft.addToBackStack("fragment b");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    //----------------------------------------------------------------------------------------------
    public void showFragmentUberProducts( String strId) {
        FragmentUberProducts fragmentUberProducts = new FragmentUberProducts();
        fragmentUberProducts.strId = strId;
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.layout_container, fragmentUberProducts);
        ft.addToBackStack("fragment uber products");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }


    //----------------------------------------------------------------------------------------------
    public void showFragmentAddCurrentLocation() {
        FragmentAddCurrentLocation fragmentAddCurrentLocation = new FragmentAddCurrentLocation();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.layout_container, fragmentAddCurrentLocation);
        ft.addToBackStack("fragment add current location");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    //----------------------------------------------------------------------------------------------
    public void showFragmentLocationEdit() {
        FragmentLocationEdit fragmentLocationEdit = new FragmentLocationEdit();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.layout_container, fragmentLocationEdit);
        ft.addToBackStack("fragment location edit");
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }



    //==============================================================================================
    //
    // Code below from Google, to determine last location.
    // see https://developer.android.com/training/location/retrieve-current.html
    //
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Runs when a GoogleApiClient object successfully connects.
     */
    @Override
    public void onConnected(Bundle connectionHint) {
        // Provides a simple way of getting a device's location and is well suited for
        // applications that do not require a fine-grained location and that do not need location
        // updates. Gets the best and most recent location currently available, which may be null
        // in rare cases when a location is not available.
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitude  = mLastLocation.getLatitude();
            mLongitude = mLastLocation.getLongitude();
        } else {
            Toast.makeText(this, R.string.no_location_detected, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        // Refer to the javadoc for ConnectionResult to see what error codes might be returned in
        // onConnectionFailed.
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnectionSuspended(int cause) {
        // The connection to Google Play services was lost for some reason. We call connect() to
        // attempt to re-establish the connection.
        Log.i(TAG, "Connection suspended");
        mGoogleApiClient.connect();
    }
    //==============================================================================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
