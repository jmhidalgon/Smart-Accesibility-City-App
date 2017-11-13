package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

    private String item;
    private List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accesibility);

        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        buttonRemove = (Button) findViewById(R.id.buttonRemove);

        listViewAccess = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, items);
        listViewAccess.setAdapter(adapter);
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
                                    if (item.compareTo("") == 0) {
                                        Toast.makeText(getApplicationContext(), "Añadido", Toast.LENGTH_SHORT).show();
                                        items.add(item);
                                    }
                                }
                            })
                            .setNegativeButton("Cancelar", null)
                            .show();
                }
            });

        }
    }
}
