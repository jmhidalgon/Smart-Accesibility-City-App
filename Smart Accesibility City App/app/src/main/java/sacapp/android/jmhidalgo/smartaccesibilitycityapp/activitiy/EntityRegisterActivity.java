package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.EntityService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;

public class EntityRegisterActivity extends AppCompatActivity implements LocationListener {

    private Button buttonAccept;
    private Button buttonUbication;
    private final int ACCESS_LOCATION = 1;

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPass;
    private EditText editTextPassConfirmed;
    private EditText editTextAddress;
    private EditText editTextPassWebsite;

    private Location entityLocation;
    private LocationManager locationManager;

    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_register);

        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        final Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_LOCATION);
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    ACCESS_LOCATION);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                new AlertDialog.Builder(EntityRegisterActivity.this).setTitle("GPS").setMessage("Si no acepta los permisos requeridos, no puede hacer uso de la aplicación");
                return;
            }
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 10, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 10, this);


        buttonAccept = (Button) findViewById(R.id.buttonAccept);
        buttonUbication = (Button) findViewById(R.id.buttonUbicacion);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPassConfirmed = (EditText) findViewById(R.id.editTextPassConfirmed);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        editTextPassWebsite = (EditText) findViewById(R.id.editTextWebsite);

        buttonUbication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLocationEntity();
                List<Address> addresses = null;

                try {
                    addresses = geocoder.getFromLocation(entityLocation.getLatitude(), entityLocation.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(addresses.isEmpty()){
                    new AlertDialog.Builder(EntityRegisterActivity.this)
                            .setTitle("Dirección")
                            .setMessage("No se ha podido obtener la dirección de la entidad")
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    editTextAddress.setText(addresses.get(0).getAddressLine(0));
                }
            }
        });


        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = editTextName.getText().toString();
                String address = editTextAddress.getText().toString();
                String pass1 = editTextPass.getText().toString();
                String pass2 = editTextPassConfirmed.getText().toString();
                String email = editTextEmail.getText().toString();
                String website = editTextPassWebsite.getText().toString();

                if (!pass1.equals(pass2)) {
                    Toast.makeText(EntityRegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                    editTextPass.requestFocus();
                    return;
                }
                // TODO GET LOCATION
                Entity entity = new Entity("", name, email, pass1, "ROLE_ENTITY", "", address, entityLocation.getLongitude(), entityLocation.getLatitude(), website);

                EntityService entityService = API.getApi().create(EntityService.class);
                Call<Entity> entityCall = entityService.register(entity);

                entityCall.enqueue(new Callback<Entity>() {
                    @Override
                    public void onResponse(Call<Entity> call, Response<Entity> response) {

                        if (API.METHOD_NOT_ALLOWED == response.code()) {
                            Toast.makeText(EntityRegisterActivity.this, response.message() + ": Email ya registrado en la aplicación", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(EntityRegisterActivity.this, "Entidad registrada", Toast.LENGTH_LONG).show();
                            Intent intentBackLogin = new Intent(EntityRegisterActivity.this, AccessibilityActivity.class);
                            intentBackLogin.putExtra("email", editTextEmail.getText().toString());
                            intentBackLogin.putExtra("pass", editTextPass.getText().toString());
                            startActivity(intentBackLogin);
                        }
                    }

                    @Override
                    public void onFailure(Call<Entity> call, Throwable t) {
                        Toast.makeText(EntityRegisterActivity.this, "Error al registrar la entidad.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private boolean checkGPSSignal() {
        // Check GPS
        int gpsSignal = 0;
        try {
            gpsSignal = Settings.Secure.getInt(EntityRegisterActivity.this.getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (gpsSignal == 0) { // No gsp signal
                showGPSAlert();
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return gpsSignal != 0;
    }

    private void showGPSAlert() {
        new AlertDialog.Builder(EntityRegisterActivity.this)
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

    private void getLocationEntity() {
        if (checkGPSSignal()) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            entityLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(entityLocation == null){
                entityLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
        }
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
