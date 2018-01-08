package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;

public class DetailsActivity extends AppCompatActivity {

    private Entity entity;

    private TextView nameTextView;
    private TextView addressTextView;

    private FloatingActionButton googlemapButton;
    private FloatingActionButton emailButton;
    private FloatingActionButton gotoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Intent intent = getIntent();
        if(intent.getParcelableExtra("Entity") != null ) {
            entity = (Entity)intent.getParcelableExtra("Entity");
        }

        nameTextView = (TextView) findViewById(R.id.tvName);
        addressTextView = (TextView) findViewById(R.id.tvAddress);

        nameTextView.setText(entity.getEntityname());
        addressTextView.setText(entity.getAdress());

        googlemapButton = (FloatingActionButton) findViewById(R.id.fabGoogleMaps);
        googlemapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uriBegin = "geo:12,34";
                String query = entity.getLatitud() + "," + entity.getLongitud() + "(" + entity.getEntityname() + ")";
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery;
                Uri uri = Uri.parse( uriString );

                // Uri gmmIntentUri = Uri.parse("geo:"+entity.getLatitud()+","+entity.getLongitud()+","+entity.getEntityname());

                Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);
                mapIntent.setPackage("com.google.android.apps.maps");

                try {
                    startActivity(mapIntent);
                }catch (ActivityNotFoundException e) {
                    Toast.makeText(DetailsActivity.this, "Error. No se ha podido abrir google maps", Toast.LENGTH_LONG).show();
                }
            }
        });

        emailButton = (FloatingActionButton) findViewById(R.id.fabEmail);
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mailto = "mailto:" +entity.getEmail() +
                        "?cc=" + "info@sacapp.com" +
                        "&subject=" + Uri.encode("Contacto mediante SACAPP") +
                        "&body=" + Uri.encode("Contacto mediante SACAPP");

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse(mailto));

                try {
                    startActivity(emailIntent);
                }catch (ActivityNotFoundException e) {
                    Toast.makeText(DetailsActivity.this, "Error. No se ha podido abrir aplicacion de mensajeria", Toast.LENGTH_LONG).show();
                }

            }
        });

        gotoButton = (FloatingActionButton) findViewById(R.id.fabGoTo);
        gotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });
    }


}




























