package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.AccessResourceService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.AccessResource;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;

/** Activity to register the AccessResource of a Entity
 *
 * @author Juan Manuel Hidalgo Navarro
 */
public class AccessibilityActivity extends AppCompatActivity {

    /** Entity to register AccessResources */
    private Entity entity;

    /** Listview to represent the AccessResources */
    private ListView listViewAccess;
    /** Button to add new AccessResource */
    private Button buttonAdd;
    /** Button to accept */
    private Button buttonAcept;

    /** Number of AccessResources */
    private int numberItems;
    /** Selected AccessResource */
    private int selectedItem;
    /** String AccessResource Item */
    private static String item;
    /** List of String AccessResource Item */
    private List<String> items;

    /** Adapter for AccessResource ListView */
    private ArrayAdapter<String> adapter;

    /** Error control boolean */
    static boolean everyOk = true;
    /** Method to register a failure in AccessResource registering process */
    public static void registerFailure(){
        everyOk = false;
    }

    /** OnCreate Activity method (Inherited method)
     *
     * @param savedInstanceState Bundle
     */
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

    /** Method to add a listener to the buttons
     *
     */
    private void addButtonListener(){

        // Add new AccessResource method
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

        // Accept Activity method
        if(buttonAcept != null) {
            buttonAcept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO AccesSResource
                    for(int i=0; i<adapter.getCount(); ++i){
                        AccessResource accessResource = new AccessResource("", adapter.getItem(i).toString(), entity.getId());

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
                                            AccessibilityActivity.registerFailure();
                                        } else {
                                            if(entity.getRol().equals("ROLE_ENTITY")){
                                                Intent intentBackLogin = new Intent(AccessibilityActivity.this, LoginActivity.class);
                                                intentBackLogin.putExtra("email", entity.getEmail());
                                                intentBackLogin.putExtra("pass", entity.getPass());
                                                startActivity(intentBackLogin);
                                            } else {
                                                AccessibilityActivity.this.finish();
                                            }
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

    /** Method to add a listener to the ListView
     *
     */
    private void addListViewListener(){

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
