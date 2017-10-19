package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.fragment.MenubarFragment;

public class ExplorerActivity extends FragmentActivity implements OnMapReadyCallback, MenubarFragment.MenuListener, LocationListener {

    private GoogleMap mMap;

    private LocationManager locationManager;
    private Location currentLocation;

    private final int ACCESS_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explorer);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        checkGPSSignal();
    }


    // OnMapReadyCallback Implementation
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_LOCATION);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    ACCESS_LOCATION);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                new AlertDialog.Builder(ExplorerActivity.this).setTitle("GPS").setMessage("Si no acepta los permisos requeridos, no puede hacer uso de la aplicación");
                return ;
            }
        }

        mMap.setMinZoomPreference(10);
        mMap.setMaxZoomPreference(20);

        mMap.setMyLocationEnabled(true);

        mMap.animateCamera(CameraUpdateFactory.zoomIn());

        /* SUSCRIBE LOCATION LISTENER*/
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);

        /* MAPS LISTENER */
        // Click on the map
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });

        // Long click on the map
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // MenubarFragment.MenuListener Implementation
    @Override
    public void openSlide() {

    }

    @Override
    public void openExplorer() {

    }

    @Override
    public void openSites() {

    }

    @Override
    public void openRatings() {

    }

    @Override
    public void setButtonActivated() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        this.checkGPSSignal();
    }

    private boolean checkGPSSignal(){
        // Check GPS
        int gpsSignal = 0;
        try {
            gpsSignal = Settings.Secure.getInt(ExplorerActivity.this.getContentResolver(), Settings.Secure.LOCATION_MODE);
            if(gpsSignal == 0){ // No gsp signal
                showGPSAlert();
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return gpsSignal != 0;
    }

    private void showGPSAlert(){
        new AlertDialog.Builder(ExplorerActivity.this)
                .setTitle("GPS")
                .setMessage("Debe activar la localización GPS, ¿desea activarla ahora?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
