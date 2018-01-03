package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final Intent intent = getIntent();
        if(intent.getStringExtra("Entity") != null ) {
            Entity e = (Entity)intent.getSerializableExtra("Entity");
        }
    }
}
