package com.softmasters.dawuro.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.softmasters.dawuro.R;
import com.softmasters.dawuro.controllers.BasicinformationController;
import com.softmasters.dawuro.controllers.BusinessInformationController;
import com.softmasters.dawuro.controllers.ClientidentificationController;
import com.softmasters.dawuro.controllers.ContactinformationController;
import com.softmasters.dawuro.controllers.GalleryController;
import com.softmasters.dawuro.controllers.IdentificationController;
import com.softmasters.dawuro.controllers.LocationController;
import com.softmasters.dawuro.controllers.MessagingController;
import com.softmasters.dawuro.controllers.RelativesController;
import com.softmasters.dawuro.controllers.UserbiometricsController;
import com.softmasters.dawuro.controllers.UserfingerprintController;
import com.softmasters.dawuro.umid.Basicinformation;
import com.softmasters.dawuro.umid.Businessinformation;
import com.softmasters.dawuro.umid.Clientidentification;
import com.softmasters.dawuro.umid.Contactinformation;
import com.softmasters.dawuro.umid.Gallery;
import com.softmasters.dawuro.umid.Identification;
import com.softmasters.dawuro.umid.Location;
import com.softmasters.dawuro.umid.Messaging;
import com.softmasters.dawuro.umid.Relatives;
import com.softmasters.dawuro.umid.Userbiometrics;
import com.softmasters.dawuro.umid.Userfingerprint;
import com.softmasters.dawuro.utils.AddressResults;
import com.softmasters.dawuro.utils.Config;
import com.softmasters.dawuro.utils.MonitorUtils;
import com.softmasters.dawuro.utils.PostData;
import com.softmasters.dawuro.utils.ScrollSupportMapFragment;
import com.softmasters.dawuro.utils.UMIDDatabaseHelper;
import com.softmasters.dawuro.utils.Uploader;

import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Softmasters on 23-May-17.
 */

public class LoginActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleMap.OnMapClickListener, View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {

    BasicinformationController basicinformationController;
    BusinessInformationController businessInformationController;
    ClientidentificationController clientidentificationController;
    ContactinformationController contactinformationController;
    GalleryController galleryController;
    IdentificationController identificationController;
    LocationController locationController;
    MessagingController messagingController;
    RelativesController relativesController;
    UserbiometricsController userbiometricsController;
    UserfingerprintController userfingerprintController;

    Dao<Basicinformation, Integer> basicinformationDao;
    Dao<Businessinformation, Integer> businessinformationDao;
    Dao<Clientidentification, Integer> clientidentificationDao;
    Dao<Contactinformation, Integer> contactinformationDao;
    Dao<Gallery, Integer> galleryDao;
    Dao<Identification, Integer> identificationDao;
    Dao<Location, Integer> locationDao;
    Dao<Messaging, Integer> messagingDao;
    Dao<Relatives, Integer> relativesDao;
    Dao<Userbiometrics, Integer> userbiometricsDao;
    Dao<Userfingerprint, Integer> userfingerprintDao;
    UMIDDatabaseHelper umidDBHelper;
    Context context;

    EditText editTinno;
    EditText editName;
    EditText editPhone;
    EditText editWebsite;
    EditText editHseNo;
    TextView textCoords;
    TextView textAddress;
    ImageView imageSend;

    Uploader uploader;
    PostData postData;

    GoogleApiClient googleApiClient;
    MapFragment mapfragment;
    GoogleMap map;
    LocationRequest locationRequest;
    LocationSettingsRequest.Builder builder;
    PendingResult<LocationSettingsResult> result;
    android.location.Location location;
    CameraUpdate update;
    Thread thread;
    Gson gson;

    String street, city, region, assembly, country;

    private final static int GPS_CHECK = 1;
    private static final int REQUEST_CHECK_SETTINGS = 11;
    private static final int MY_LOCATION_REQUEST_CODE = 2;
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 10000;
    final int ZOOM = 17;
    private LatLng currentLatLng;

    UMIDDatabaseHelper getUMIDHelper() {
        try {
            if (umidDBHelper == null) {
                umidDBHelper = OpenHelperManager.getHelper(context,
                        UMIDDatabaseHelper.class);
            }
            return umidDBHelper;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.softmasters.dawuro.R.layout.activity_login);

        context = LoginActivity.this;
        gson = new Gson();

        Log.d("", "MacAddress: " + MonitorUtils.getMacAddress(context));

        buildGoogleApiClient();
        initDaos();
        initDaoControllers();

        if (checkPlayServices()) {
            if (map == null) {

                mapfragment = (ScrollSupportMapFragment) getFragmentManager()
                        .findFragmentById(R.id.mapFrame);
                mapfragment.getMapAsync(this);

            }
        } else {
            finish();
        }

        textAddress = (TextView) findViewById(R.id.addressLbl);
        textCoords = (TextView) findViewById(R.id.coordinatesLbl);
        editHseNo = (EditText) findViewById(R.id.edit_hse_no);
        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.edit_tel);
        editWebsite = (EditText) findViewById(R.id.editWebsite);
        editTinno = (EditText) findViewById(R.id.editTinno);
        imageSend = (ImageView) findViewById(R.id.imgSend);

        uploader = new Uploader(Config.REGISTRATION_PROTOCOL, Config.REGISTRATION_SERVER);

    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPlayServices();
        if (googleApiClient.isConnected()) {
            startLocationUpdates();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case GPS_CHECK:

                    plotFirstLocation();
                    break;
                case REQUEST_CHECK_SETTINGS:
                    plotFirstLocation();
                    break;
                default:
                    break;
            }
        }
    }

    //database connectors
    void initDaos() {
        try {
            basicinformationDao = getUMIDHelper().getBasicinformationDao();
            businessinformationDao = getUMIDHelper()
                    .getBusinessinformationDao();
            clientidentificationDao = getUMIDHelper()
                    .getClientidentificationDao();
            contactinformationDao = getUMIDHelper().getContactinformationDao();
            galleryDao = getUMIDHelper().getGalleryDao();
            identificationDao = getUMIDHelper().getIdentificationDao();
            locationDao = getUMIDHelper().getLocationDao();
            messagingDao = getUMIDHelper().getMessagingDao();
            relativesDao = getUMIDHelper().getRelativesDao();
            userbiometricsDao = getUMIDHelper().getUserbiometricsDao();
            userfingerprintDao = getUMIDHelper().getUserfingerprintDao();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //database connectors
    void initDaoControllers() {
        try {
            basicinformationController = new BasicinformationController(
                    context, basicinformationDao);
            businessInformationController = new BusinessInformationController(
                    context, businessinformationDao);
            clientidentificationController = new ClientidentificationController(
                    context, clientidentificationDao);
            contactinformationController = new ContactinformationController(
                    context, contactinformationDao);
            galleryController = new GalleryController(context, galleryDao);
            identificationController = new IdentificationController(context,
                    identificationDao);
            locationController = new LocationController(context, locationDao);
            messagingController = new MessagingController(context, messagingDao);
            relativesController = new RelativesController(context, relativesDao);
            userbiometricsController = new UserbiometricsController(context,
                    userbiometricsDao);
            userfingerprintController = new UserfingerprintController(context,
                    userfingerprintDao);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected synchronized void buildGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case GPS_CHECK:
                if (grantResults.length > 0) { //permission given
                    googleApiClient.connect();
                    checkLocationSettings();
                } else {//permission not given
                    //so deny this functionality
                    finish();
                }
                break;
            case MY_LOCATION_REQUEST_CODE:
                if (permissions.length == 1 &&
                        permissions[0] == android.Manifest.permission.ACCESS_FINE_LOCATION &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }map.setMyLocationEnabled(true);
                break;
        }
    }

    private void checkLocationSettings() {
        createLocationRequest();

        //add location request
        builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);
        //check whether the current location settings are satisfied
        result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        //Prompt the User to Change Location Settings
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {
                final Status status = locationSettingsResult.getStatus();
                final LocationSettingsStates locationSettingsStates =
                        locationSettingsResult.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        plotFirstLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(LoginActivity.this,
                                    REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            e.printStackTrace();
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        Toast.makeText(context, "unavailable", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
    }

    protected void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    //starts the location updates
    protected void startLocationUpdates() {
        checkPermission();
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
    }

    private void stopLocationUpdates() {
        if (googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                    Toast.makeText(context, "This Permission is required in order to access " +
                            "your current location coordinates", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            GPS_CHECK);
                } else
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            GPS_CHECK);
            } else {

            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //checkPermission();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            //
        }
        //map.setMyLocationEnabled(true);
        map.setIndoorEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        map.animateCamera(CameraUpdateFactory.zoomTo(ZOOM));
        map.setOnMapClickListener(this);
    }

    //plots the first current location when gps settings have been checked
    protected void plotFirstLocation() {
        checkPermission();

        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
        //setLocation(location);
        if (location != null) {

            currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            Log.i("", "CurrentLatLng : " + currentLatLng.latitude + ", " + currentLatLng.longitude);
            update = CameraUpdateFactory.newLatLngZoom(currentLatLng, ZOOM);
            map.clear();
            map.moveCamera(update);
            map.animateCamera(update);
        }

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        checkLocationSettings();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        this.location = location;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void startAddressThread() {
        thread = new Thread(new Runnable() {

            @Override
            public void run() {
                String data = "";

                try {

                    Log.i("LatLng", "CurrentLng : " + location.getLatitude() + ","
                            + location.getLongitude());

                    // encode for special characters
                    String url = "http://maps.googleapis.com/maps/api/geocode/json"
                            + "?latlng="
                            + URLEncoder.encode(location.getLatitude() + ","
                            + location.getLongitude(), "utf-8")
                            + "&sensor=true";

                    // Fetching the data from web service
                    data = MonitorUtils.downloadUrl(url);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                threadMsg(data);
            }

            private void threadMsg(String msg) {

                if (!msg.equals(null) && !msg.equals("")) {
                    android.os.Message msgObj = handler.obtainMessage();
                    Bundle b = new Bundle();
                    b.putString("address", msg);
                    msgObj.setData(b);
                    handler.sendMessage(msgObj);
                }
            }

            Handler handler = new Handler() {
                @Override
                public void handleMessage(android.os.Message msg) {
                    String dataMsg = msg.getData().getString("address");
                    if (!TextUtils.isEmpty(dataMsg)) {
                        AddressResults addressResults = gson.fromJson(dataMsg,
                                AddressResults.class);
                        if (TextUtils.isEmpty(addressResults
                                .getError_message())) {
                            try {
                                List<AddressResults.Results> results = addressResults.getResults();
                                List<AddressResults.Results.AddressComponents> addressComponents = results
                                        .get(0).getAddress_components();

                                for (int i = addressComponents.size(); i > 0; i--) {

                                    for (String st : addressComponents.get(i - 1)
                                            .getTypes()) {
                                        if (st.equalsIgnoreCase("route")) {
                                            street = addressComponents.get(i - 1)
                                                    .getLong_name();
                                        } else if (st.equalsIgnoreCase("locality")) {
                                            city = addressComponents.get(i - 1)
                                                    .getLong_name();
                                        } else if (st
                                                .equalsIgnoreCase("administrative_area_level_1")) {
                                            region = addressComponents.get(i - 1)
                                                    .getLong_name();
                                        } else if (st
                                                .equalsIgnoreCase("administrative_area_level_2")) {
                                            assembly = addressComponents.get(i - 1)
                                                    .getLong_name();
                                        } else if (st.equalsIgnoreCase("country")) {
                                            country = addressComponents.get(i - 1)
                                                    .getLong_name();
                                        }

                                    }
                                }

                                Log.i("", "Location Thread: " + ""
                                        + location.getLatitude() + ", "
                                        + location.getLongitude());
                                textAddress.setText("" + street + ", " + city + ", "
                                        + region);
                                textAddress.setVisibility(View.VISIBLE);

                                textCoords.setText(""
                                        + location.getLatitude() + ", "
                                        + location.getLongitude());
                                textCoords.setVisibility(View.VISIBLE);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Log.d("Empty", "Empty");
                        }

                    }
                }
            };
        });
        thread.start();
    }
}
