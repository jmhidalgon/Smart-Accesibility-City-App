package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.AccessResourceService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.EntityService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.AccessResource;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.AccessResources;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entities;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;

public class DetailsActivity extends AppCompatActivity {

    private Entity entity;
    private List<AccessResource> accessResourcesEntity;

    private TextView nameTextView;
    private TextView addressTextView;

    private FloatingActionButton googlemapButton;
    private FloatingActionButton emailButton;
    private FloatingActionButton gotoButton;

    private ListView listViewAccessResource;
    private static AccessResource item;
    private List<String> items;

    private ArrayAdapter<String> adapterAccessResource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Intent intent = getIntent();
        if(intent.getParcelableExtra("Entity") != null ) {
            entity = (Entity)intent.getParcelableExtra("Entity");
        }

        listViewAccessResource = (ListView) findViewById(R.id.listViewResource);
        items = new ArrayList<String>();
        adapterAccessResource = new ArrayAdapter<String>(DetailsActivity.this, android.R.layout.simple_list_item_activated_1, items);
        listViewAccessResource.setAdapter(adapterAccessResource);

        getAccessResources();

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

    public void getAccessResources()
    {
        if(entity != null){
            AccessResourceService accessResourceService = API.getApi().create(AccessResourceService.class);
            Call<AccessResources> accessResourcesCall = accessResourceService.getResources(entity.getId());

            accessResourcesCall.enqueue(new Callback<AccessResources>() {
                @Override
                public void onResponse(Call<AccessResources> call, Response<AccessResources> response) {
                    int httpCode = response.code();

                    switch(httpCode) {
                        case API.INTERNAL_SERVER_ERROR:
                            Toast.makeText(DetailsActivity.this, response.message() + ": Error en el servidor de datos", Toast.LENGTH_LONG).show();
                            break;
                        case API.NOT_FOUND:
                            Toast.makeText(DetailsActivity.this, response.message() + ": No encontrado", Toast.LENGTH_LONG).show();
                            break;
                        case API.OK:
                            AccessResources accessResources = response.body();
                            accessResourcesEntity = accessResources.getAccessResources();
                            fillListViewAccessResource();
                            break;
                    }

                }

                @Override
                public void onFailure(Call<AccessResources> call, Throwable t) {
                    Toast.makeText(DetailsActivity.this, "Error de conexion", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void fillListViewAccessResource(){

        if(accessResourcesEntity != null){
            for(int i=0; i<accessResourcesEntity.size(); ++i){
                items.add(accessResourcesEntity.get(i).getResourceName());
            }
            adapterAccessResource.notifyDataSetChanged();
        }
    }


}




























