package com.softmasters.dawuro.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.softmasters.dawuro.R;
import com.softmasters.dawuro.utils.PlaceAutocompleteAdapter;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.softmasters.dawuro.utils.PlacesInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LocationActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = android.Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private Boolean isPermissionGranted = false;
    private static final String TAG = "LocationActivity";
    private GoogleMap gglMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    AutoCompleteTextView searchBox;
    private PlaceAutocompleteAdapter placesAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private static final LatLngBounds LAT_LNG_BOUNDS = new
            LatLngBounds(new LatLng(-40, -168), new LatLng(70, 130)
    );

    private PlacesInfo placesInfo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.location_layout);
        locationPermission();

        searchBox = (AutoCompleteTextView) findViewById(R.id.searchbox);


    }

    private void init() {
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API).
                        enableAutoManage(this, this)
                .build();
        placesAutocompleteAdapter = new PlaceAutocompleteAdapter(
                this, Places.getGeoDataClient(this, null), LAT_LNG_BOUNDS, null
        );

        searchBox.setAdapter(placesAutocompleteAdapter);
        searchBox.setOnItemClickListener(ItemClickListener);
        searchBox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        || event.getAction() == KeyEvent.KEYCODE_ENTER) {

                    //execute search
                    geoLocate();
                }


                return false;
            }
        });
        hideKeyboard();
    }

    private void geoLocate() {
        String text = searchBox.getText().toString();
        Geocoder geocoder = new Geocoder(LocationActivity.this);
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(text, 1);
        } catch (IOException e) {
            Log.d("Location Search", "Here :" + e.getMessage());
        }
        if (list.size() > 0) {
            Address address = list.get(0);
            Log.d(TAG, "Details: " + address.toString());
            Toast.makeText(this, address.toString(),
                    Toast.LENGTH_LONG).show();

            moveCamera(new LatLng(address.getLatitude(),
                    address.getLongitude()), DEFAULT_ZOOM, address.getAddressLine(0));
        }
    }

    private void locationPermission() {
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                isPermissionGranted = true;
                initMap();
            }
        } else {
            ActivityCompat.requestPermissions(this, permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        isPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int a = 0; a < grantResults.length; a++) {
                        if (grantResults[a] != PackageManager.PERMISSION_GRANTED) {
                            isPermissionGranted = false;
                            return;
                        }
                    }
                    isPermissionGranted = true;
                    initMap();

                }

            }
        }

    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(LocationActivity.this);


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gglMap = googleMap;

        if (isPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {

                return;
            }
//            gglMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            gglMap.setMyLocationEnabled(true);
            gglMap.getUiSettings().setMyLocationButtonEnabled(true);
            gglMap.getUiSettings().setScrollGesturesEnabled(true);
            init();
        }
    }

    private void getDeviceLocation() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            if (isPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Maps Permission not allowed",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                Task location = fusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            try {
                                Location currnetLocation = (Location) task.getResult();
                                LatLng latlng = new LatLng(currnetLocation.getLatitude(),
                                        currnetLocation.getLongitude());
                                moveCamera(latlng, DEFAULT_ZOOM, "My Location");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
            hideKeyboard();

        } catch (SecurityException e) {

        }
    }

    private void moveCamera(LatLng latLng, float zoom, String title) {
        gglMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions().position(latLng)
                    .title(title);
            gglMap.addMarker(options);
        }
    }

    private void hideKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
//    Add onclick listener to auto-complete list

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private AdapterView.OnItemClickListener ItemClickListener = new
            AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    hideKeyboard();
                    final AutocompletePrediction item = placesAutocompleteAdapter.getItem(position);
                    final String placeID = item.getPlaceId();

                    PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi.getPlaceById(mGoogleApiClient,
                            placeID);
                    placeResult.setResultCallback(updatePlaceDetailsCallback);
                }
            };
    private ResultCallback<PlaceBuffer> updatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
                Toast.makeText(getBaseContext(), "Oops ! Cannot fetch result," +
                        places.getStatus().toString(), Toast.LENGTH_SHORT).show();
                places.release();
                return;
            }
            final Place place = places.get(0);

            try {
                placesInfo = new PlacesInfo();
                placesInfo.setPhoneNumber(place.getPhoneNumber().toString());
                placesInfo.setName(place.getName().toString());
                placesInfo.setWebsiteUrl(place.getWebsiteUri());

                Toast.makeText(getBaseContext(), placesInfo.toString(), Toast.LENGTH_LONG).show();
            }catch (NullPointerException e){
                Log.d(TAG,e.getMessage());
            }
            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude),DEFAULT_ZOOM,placesInfo.getName());
        }
    };
}
