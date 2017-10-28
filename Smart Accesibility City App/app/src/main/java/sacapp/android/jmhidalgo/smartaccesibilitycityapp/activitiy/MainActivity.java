package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Explorar"));
        tabLayout.addTab(tabLayout.newTab().setText("Sitios"));
        tabLayout.addTab(tabLayout.newTab().setText("Valoraciones"));



    }
}
