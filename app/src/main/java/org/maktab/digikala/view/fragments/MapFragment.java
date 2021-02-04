package org.maktab.digikala.view.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;
import org.maktab.digikala.DirectionsJSONParser;
import org.maktab.digikala.R;
import org.maktab.digikala.model.MapAddress;
import org.maktab.digikala.viewmodel.SettingViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends SupportMapFragment {

    public static final String TAG = "LocatrFragment";
    private static final int REQUEST_CODE_PERMISSION_LOCATION = 0;
    private static final int NUMBER_OF_IMAGES = 3;

    private SettingViewModel mSettingViewModel;
    private String mAddress;
    private GoogleMap mMap;
    private LatLng mLatLng;
    private ArrayList markerPoints = new ArrayList();
    private MarkerOptions mMarkerOptions;
    private Marker mMarker;

    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mSettingViewModel = new ViewModelProvider(this).get(SettingViewModel.class);
        mMarkerOptions = new MarkerOptions();

        mSettingViewModel.getMyLocation().observe(this, new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                updateUI();
            }
        });

        getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                updateUI();
                listeners();
            }
        });
    }

    private void listeners() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (mMarker != null) {

                    mMarker.setPosition(latLng);
                }else {
                    mMarkerOptions
                            .position(latLng)
                            .title("My Location");
                    mMarker = mMap.addMarker(mMarkerOptions);
                }
                getAddress(latLng.latitude,latLng.longitude);
            }
        });
    }

    /*private void listeners() {
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mEnd = latLng;
                mMap.addMarker(new MarkerOptions().position(mEnd).title("destination"));
            }
        });
    }*/

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_fragment_location, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_location:
                if (hasLocationAccess()) {
                    requestLocation();
                } else {
                    //request Location access permission
                    requestLocationAccessPermission();
                }
                return true;

            case R.id.menu_item_done:
                if (mLatLng != null && mAddress != null){
                    MapAddress mapAddress = new MapAddress(mAddress,mLatLng.latitude,mLatLng.longitude);
                    mSettingViewModel.insertAddress(mapAddress);
                    Toast.makeText(getActivity(), "Done", Toast.LENGTH_SHORT).show();
                }

                else
                    Toast.makeText(getActivity(), "Select Place", Toast.LENGTH_SHORT).show();

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_PERMISSION_LOCATION:
                if (grantResults == null || grantResults.length == 0)
                    return;

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    requestLocation();
                /*else
                    Toast.makeText(
                            getContext(),
                            "We do not have the location permission",
                            Toast.LENGTH_LONG).show();*/

                return;
        }
    }

    private boolean hasLocationAccess() {
        boolean isFineLocation = ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        boolean isCoarseLocation = ContextCompat.checkSelfPermission(
                getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        return isFineLocation && isCoarseLocation;
    }

    private void requestLocationAccessPermission() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };
        requestPermissions(permissions, REQUEST_CODE_PERMISSION_LOCATION);
    }

    @SuppressLint("MissingPermission")
    private void requestLocation() {
        if (!hasLocationAccess())
            return;

        mSettingViewModel.requestLocation();
    }

    private void updateUI() {
        Location location = mSettingViewModel.getMyLocation().getValue();
        if (location == null || mMap == null)
            return;

        LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .include(myLatLng)
                .build();

        mMarkerOptions
                .position(myLatLng)
                .title("My Location");

        /*MarkerOptions mMarkerOptions = new MarkerOptions()
                .position(myLatLng)
                .title("My location");*/

        if (mMarker == null) {
            mMap.addMarker(mMarkerOptions);
        }else {
            mMarker.setPosition(myLatLng);
        }

        getAddress(myLatLng.latitude,myLatLng.longitude);

        int margin = getResources().getDimensionPixelSize(R.dimen.map_inset_margin);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(latLngBounds, margin);
        mMap.animateCamera(cameraUpdate);
    }

    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            Address obj = addresses.get(0);
            mAddress = obj.getAddressLine(0);
            mAddress = mAddress + "\n" + obj.getCountryName();
            mAddress = mAddress + "\n" + obj.getCountryCode();
            mAddress = mAddress + "\n" + obj.getAdminArea();
            mAddress = mAddress + "\n" + obj.getPostalCode();
            mAddress = mAddress + "\n" + obj.getSubAdminArea();
            mAddress = mAddress + "\n" + obj.getLocality();
            mAddress = mAddress + "\n" + obj.getSubThoroughfare();

            mLatLng = new LatLng(lat,lng);

        } catch (IOException e) {

            e.printStackTrace();
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}