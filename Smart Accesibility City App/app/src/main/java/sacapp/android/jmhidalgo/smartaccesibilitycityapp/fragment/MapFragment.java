package sacapp.android.jmhidalgo.smartaccesibilitycityapp.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.EntityService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.TokenService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy.DetailsActivity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy.ExplorerActivity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy.LoginActivity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy.MainActivity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.InfoWindowData;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entities;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Token;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.util.CustomInfoWindowGoogleMap;

import static android.content.Context.LOCATION_SERVICE;

public class MapFragment extends Fragment implements OnMapReadyCallback, View.OnClickListener, LocationListener {

    private final int ACCESS_LOCATION = 1;

    private View rootView;
    private MapView mapView;
    private GoogleMap gMap;

    private FloatingActionButton fab;

    private LocationManager locationManager;
    private Location currentLocation;

    private Marker marker;
    private CameraPosition camera;

    private List<Entity> entities;
    private Entity selectedEntity;

    public MapFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_map, container, false);

        fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapView = (MapView) rootView.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        gMap = googleMap;
        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_LOCATION);
            ActivityCompat.requestPermissions(this.getActivity(),
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    ACCESS_LOCATION);
            if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                new AlertDialog.Builder(this.getActivity()).setTitle("GPS").setMessage("Si no acepta los permisos requeridos, no puede hacer uso de la aplicación");
                return ;
            }
        }

        gMap.setMyLocationEnabled(true);
        // gMap.getUiSettings().setMyLocationButtonEnabled(false);

        // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
        // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);

        gMap.setMinZoomPreference(10);
        gMap.setMaxZoomPreference(20);

        gMap.setMyLocationEnabled(true);

        gMap.animateCamera(CameraUpdateFactory.zoomIn());

        /* SUSCRIBE LOCATION LISTENER*/
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if (location == null) {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }
        zoomToLocation(location);

        /* MAPS LISTENER */
        // Click on the map
        /*gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });

        // Long click on the map
        gMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

            }
        });*/

        gMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                selectedEntity = (Entity)marker.getTag();

                if(selectedEntity == null){
                    return false;
                }

                InfoWindowData info = new InfoWindowData();
                info.setEmail(selectedEntity.getEmail());
                info.setWebsite(selectedEntity.getWebsite());
                info.setEntity(selectedEntity);

                marker.setTag(info);

                gMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));

                marker.showInfoWindow();
                marker.setTag(selectedEntity);

                return true;
            }
        });

        getEntities();
    }

    private boolean isGPSEnabled() {
        try {
            int gpsSignal = Settings.Secure.getInt(getActivity().getContentResolver(), Settings.Secure.LOCATION_MODE);

            if (gpsSignal == 0) {
                return false;
            } else {
                return true;
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showInfoAlert() {
        new AlertDialog.Builder(getContext())
                .setTitle("GPS Signal")
                .setMessage("No tiene activada su localizacion GPS. ¿Desearía activarla ahora?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("CANCELAR", null)
                .show();
    }

    @Override
    public void onClick(View view) {
        if (!this.isGPSEnabled()) {
            showInfoAlert();
        } else {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_LOCATION);
                ActivityCompat.requestPermissions(this.getActivity(),
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        ACCESS_LOCATION);
                if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    new AlertDialog.Builder(this.getActivity()).setTitle("GPS").setMessage("Si no acepta los permisos requeridos, no puede hacer uso de la aplicación");
                    return ;
                }
            }
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location == null) {
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            currentLocation = location;

            if (currentLocation != null) {
                // createOrUpdateMarkerByLocation(location);
                zoomToLocation(location);
            }

        }
    }

    private void createOrUpdateMarkerByLocation(Location location) {
        if (marker == null) {
            marker = gMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).draggable(false));
        } else {
            marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
        }
    }

    private void createOrUpdateMarkerByLocation(double lon, double lat, Entity entity) {
        /*if (marker == null) {
            marker = gMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).draggable(true));
        } else {
            marker.setPosition(new LatLng(lat, lon));
        }*/
        /*Marker entityMarker = gMap.addMarker(new MarkerOptions()
                .position(new LatLng(lat, lon))
                .title(entity.getEntityname())
                .snippet(entity.getAdress()));*/
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(lat, lon))
                .title(entity.getEntityname())
                .snippet(entity.getAdress());

        CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(getContext());
        gMap.setInfoWindowAdapter(customInfoWindow);

        gMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);

                if(selectedEntity == null){
                    return ;
                }

                intent.putExtra("Entity", (Parcelable) selectedEntity);
                getContext().startActivity(intent);
            }
        });

        Marker entityMarker = gMap.addMarker(markerOptions);
        entityMarker.setTag(entity);
    }

    private void zoomToLocation(Location location) {
        camera = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))
                .zoom(15)           // limit -> 21
                .bearing(0)         // 0 - 365º
                .tilt(30)           // limit -> 90
                .build();
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(camera));

    }

    @Override
    public void onLocationChanged(Location location) {
        /*Toast.makeText(getContext(), "Changed! -> " + location.getProvider(), Toast.LENGTH_LONG).show();
        createOrUpdateMarkerByLocation(location);*/
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

    public void getEntities()
    {
        String token = ((MainActivity)getActivity()).getToken();
        if(token != ""){

            EntityService entityService = API.getApi().create(EntityService.class);
            Call<Entities> entitySCall = entityService.getEntities(token);

            entitySCall.enqueue(new Callback<Entities>() {

                @Override
                public void onResponse(Call<Entities> call, Response<Entities> response) {
                    int httpCode = response.code();

                    switch(httpCode) {
                        case API.INTERNAL_SERVER_ERROR:
                            Toast.makeText(getActivity(), response.message() + ": Error en el servidor de datos", Toast.LENGTH_LONG).show();
                            break;
                        case API.NOT_FOUND:
                            Toast.makeText(getActivity(), response.message() + ": No encontrado", Toast.LENGTH_LONG).show();
                            break;
                        case API.OK:
                            Entities entitiesResponse = response.body();
                            entities = entitiesResponse.getEntities();
                            break;
                    }

                    if(entities == null){
                        Toast.makeText(getActivity(), "No se ha encontrado ninguna entidad", Toast.LENGTH_LONG).show();
                    } else {
                        for(int i=0; i<entities.size(); ++i){
                            double lat = entities.get(i).getLatitud();
                            double lon = entities.get(i).getLongitud();

                            createOrUpdateMarkerByLocation(lon, lat, entities.get(i));
                        }
                    }
                }

                @Override
                public void onFailure(Call<Entities> call, Throwable t) {
                    Toast.makeText(getActivity(), "Error de conexion", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}


