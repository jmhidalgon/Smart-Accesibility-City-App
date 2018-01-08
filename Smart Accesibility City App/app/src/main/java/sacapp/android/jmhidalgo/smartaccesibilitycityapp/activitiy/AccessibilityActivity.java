package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.AccessResourceService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.UserService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.AdapterAccessItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item.AccessItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.AccessResource;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;

public class AccessibilityActivity extends AppCompatActivity {

    private Entity entity;

    private ListView listViewAccess;
    private Button buttonAdd;
    private Button buttonAcept;


    private int numberItems;
    private int selectedItem;
    private static String item;
    private List<String> items;

    private ArrayAdapter<String> adapter;

    static boolean everyOk = true;
    public static void registrerFailure(){
        everyOk = false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accesibility);

        final Intent intent = getIntent();
        if(intent.getParcelableExtra("Entity") != null ) {
            entity = (Entity)intent.getParcelableExtra("Entity");
        }

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonAcept = (Button) findViewById(R.id.buttonAcept);

        items = new ArrayList<String>();

        listViewAccess = (ListView) findViewById(R.id.listView);
        numberItems = 0;

        adapter = new ArrayAdapter<String>(AccessibilityActivity.this, android.R.layout.simple_list_item_activated_1, items);
        listViewAccess.setAdapter(adapter);

        addButtonListener();
        addListViewListener();
    }


    void addButtonListener(){

        if(buttonAdd != null){
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final EditText input = new EditText(AccessibilityActivity.this);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    input.setLayoutParams(lp);

                    new AlertDialog.Builder(AccessibilityActivity.this)
                            .setTitle("Añadir Accesibilidad")
                            .setMessage("Por favor, introduzca sus facilidades para evitar las barreras arquitectonicas")
                            .setView(input)
                            .setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    item = input.getText().toString();
                                    if (!item.equals(new String(""))) {
                                        //Toast.makeText(getApplicationContext(), "Añadido", Toast.LENGTH_SHORT).show();
                                        items.add(item);
                                        numberItems++;
                                    }
                                }
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();
                }
            });
        }

        if(buttonAcept != null) {
            buttonAcept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO AccesSResource
                    for(int i=0; i<adapter.getCount(); ++i){
                        AccessResource accessResource = new AccessResource(adapter.getItem(i).toString(), entity.getId());

                        AccessResourceService accessResourceService = API.getApi().create(AccessResourceService.class);
                        Call<AccessResource> accessResourceCall = accessResourceService.register(accessResource);

                        accessResourceCall.enqueue(new Callback<AccessResource>() {
                            @Override
                            public void onResponse(Call<AccessResource> call, Response<AccessResource> response) {
                                int httpCode = response.code();
                                switch(httpCode){
                                    case API.INTERNAL_SERVER_ERROR: {
                                        Toast.makeText(AccessibilityActivity.this, response.message() + ": Error interno del servidor", Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                    case API.BAD_REQUEST: {
                                        Toast.makeText(AccessibilityActivity.this, response.message() + ": No se ha podido registrar", Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                    case API.OK: {
                                        AccessResource accessResource = response.body();
                                        if(accessResource == null) {
                                            AccessibilityActivity.registrerFailure();
                                        }
                                        break;
                                    }
                                    default: {
                                        Toast.makeText(AccessibilityActivity.this, response.message() + ": Error desconocido", Toast.LENGTH_LONG).show();
                                        break;
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<AccessResource> call, Throwable t) {

                            }
                        });
                    }
                    if(AccessibilityActivity.everyOk){
                        Toast.makeText(AccessibilityActivity.this, "Registo de recursos accesibles correcto", Toast.LENGTH_LONG).show();

                    }

                }
            });
        }
    }

    void addListViewListener(){

        listViewAccess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i < 0 || i >= numberItems){
                    return ;
                } else {
                    selectedItem = i;
                    AlertDialog.Builder builder = new AlertDialog.Builder(AccessibilityActivity.this);
                    builder.setTitle("¿Desea eliminar la entrada seleccionada?");
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            adapter.remove(adapter.getItem(selectedItem));
                            adapter.notifyDataSetChanged();
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
            }
        });

    }
}
