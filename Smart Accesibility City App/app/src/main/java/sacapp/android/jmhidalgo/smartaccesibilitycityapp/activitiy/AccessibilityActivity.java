package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.nio.file.Files;
import java.util.ArrayList;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.AdapterAccessItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item.AccessItem;

public class AccessibilityActivity extends AppCompatActivity {

    private ListView listViewAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accesibility);

        listViewAccess = (ListView) findViewById(R.id.listView);

        ArrayList<AccessItem> accessItems = new ArrayList<AccessItem>();
        AdapterAccessItem adapterAccessItem = new AdapterAccessItem(this, accessItems);

        listViewAccess.setAdapter(adapterAccessItem);

        AccessItem accessItem = new AccessItem("Ji", "Jo", "Ja", getResources().getDrawable(android.R.drawable.ic_dialog_email));
        accessItems.add(accessItem);
    }
}
