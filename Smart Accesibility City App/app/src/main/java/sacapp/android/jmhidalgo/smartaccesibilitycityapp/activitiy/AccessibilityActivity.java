package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.AdapterAccessItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item.AccessItem;

public class AccessibilityActivity extends AppCompatActivity {

    private ListView listViewAccess;
    private Button buttonAdd;
    private Button buttonRemove;

    private int numberItems;
    private boolean answerRemove;
    private int selectedItem;
    private static String item;
    private List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accesibility);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonRemove = (Button) findViewById(R.id.buttonRemove);

        items = new ArrayList<String>();

        listViewAccess = (ListView) findViewById(R.id.listView);
        numberItems = 0;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AccessibilityActivity.this, android.R.layout.simple_list_item_activated_1, items);
        listViewAccess.setAdapter(adapter);

        addButtonListener();
        addListViewListener();
    }


    void addButtonListener(){

        if(buttonAdd != null && buttonRemove != null){
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
    }

    void addListViewListener(){

        listViewAccess.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i < 0 || i >= numberItems){
                    return ;
                } else {
                    selectedItem = i;
                    answerRemove = false;
                    AlertDialog.Builder builder = new AlertDialog.Builder(AccessibilityActivity.this);
                    builder.setTitle("¿Desea eliminar la entrada seleccionada?");
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            items.remove(selectedItem);
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
