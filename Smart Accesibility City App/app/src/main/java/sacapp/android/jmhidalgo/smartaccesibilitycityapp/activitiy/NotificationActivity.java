package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.EntityService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.UserService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.AdapterVisit;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item.VisitItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entities;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Users;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Visit;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.util.SACAPPControl;

public class NotificationActivity extends AppCompatActivity {

    private ListView listViewVisit;
    private ArrayList<VisitItem> visitItems;
    private AdapterVisit adapterVisit;
    private HashMap<String, User> nameuserMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        listViewVisit = (ListView) findViewById(R.id.listViewVisits);
        visitItems = new ArrayList<VisitItem>();
        adapterVisit = new AdapterVisit (NotificationActivity.this, R.layout.item_visit, visitItems);
        listViewVisit.setAdapter(adapterVisit);

        nameuserMap = new HashMap<>();

        for(int i=0; i<SACAPPControl.getEntityVisits().size(); ++i) {
            getUserByID (SACAPPControl.getEntityVisits().get(i));
        }
    }

    private void addListenerToListView(){
        listViewVisit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(i < 0 || i >= visitItems.size()){
                    return ;
                } else {

                }
            }
        });

    }

    private synchronized VisitItem getVisitItem(Visit v){

        VisitItem vItem = new VisitItem(
                getUserByID(v),
                v.getEntityId(),
                v.getStringDate(), false);
        adapterVisit.notifyDataSetChanged();

        return vItem;

    }

    private synchronized String getUserByID(final Visit v){
        String userId = v.getUserId();
        UserService userService = API.getApi().create(UserService.class);
        Call<Users> usersCall = userService.getUserById(userId);

        final String[] userName = new String[1];

        usersCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                nameuserMap.clear();

                int httpCode = response.code();

                switch(httpCode) {
                    case API.INTERNAL_SERVER_ERROR:
                        Toast.makeText(NotificationActivity.this, response.message() + ": Error en el servidor de datos", Toast.LENGTH_LONG).show();
                        break;
                    case API.NOT_FOUND:
                        Toast.makeText(NotificationActivity.this, response.message() + ": No encontrado", Toast.LENGTH_LONG).show();
                        break;
                    case API.OK:
                        User user = response.body().getUsers().get(0);
                        userName[0] = user.getName() + " " + user.getSurname();

                        nameuserMap.put(userName[0], user);

                        fillVisitWithName(v, userName[0]);

                        break;
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

        return userName[0];
    }

    public void fillVisitWithName(Visit v, String name){
        visitItems.add(new VisitItem(SACAPPControl.getEntity().getEntityname(), name, v.getStringDate(), false));
        adapterVisit.notifyDataSetChanged();
    }
}
