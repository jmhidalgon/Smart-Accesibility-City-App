package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.UserService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Users;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.util.SACAPPControl;

public class UserDetailActivity extends AppCompatActivity {

    private TextView textViewUserName;
    private TextView textViewMovility;
    private EditText editTextAdvise;
    private FloatingActionButton fabEmail;
    private TextView textViewDate;
    private EditText editTextDate;

    private User user;
    private String stringDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        editTextDate = (EditText) findViewById(R.id.editTextDate);
        textViewDate = (TextView) findViewById(R.id.textViewDate);

        String userId = "";

        Intent intent = getIntent();
        if(intent != null){
            if(intent.getStringExtra("userId") != null){
                userId = intent.getStringExtra("userId");

                if(intent.getStringExtra("stringDate") == null){
                    editTextDate.setVisibility(editTextDate.INVISIBLE);
                    textViewDate.setVisibility(textViewDate.INVISIBLE);
                    stringDate = "";
                }else {
                    stringDate = intent.getStringExtra("stringDate");
                }

                Iterator it = SACAPPControl.nameuserMap.entrySet().iterator();
                while(it.hasNext()){
                    Map.Entry pair = (Map.Entry)it.next();
                    User u = (User)pair.getValue();
                    if(u.getId().equals(userId)){
                        user = u;
                        break;
                    }
                }
            }
        }


        textViewUserName = (TextView) findViewById(R.id.textViewUserName);
        textViewMovility = (TextView) findViewById(R.id.textViewMovility);
        editTextAdvise = (EditText) findViewById(R.id.editTextAdvise);
        fabEmail = (FloatingActionButton) findViewById(R.id.fabEmail);

        if(user == null){
            getUserById(userId);
        } else {
            fillUserData();
        }
        if(SACAPPControl.getUser() != null){
            addOnClickListenerFabEmail();
        }


    }

    private void fillUserData(){
        if(user != null){
            textViewUserName.setText(user.getName() + " " + user.getSurname());
            editTextDate.setText(stringDate.split(" ")[0]);
            textViewMovility.setText(textViewMovility.getText() + " " + user.getReduceMovility());
            String advise = "";
            if(user.getReduceMovility().equals("Muletas")){
                advise = "Guie a su visitante por rampas de acceso, evite las escaleras y proporcionele un ascensor en caso de necesitarlo.";
            }
            if(user.getReduceMovility().equals("Andador")){
                advise = "Guie a su visitante por rampas de acceso, evite las escaleras y proporcionele un ascensor en caso de necesitarlo.";
            }
            if(user.getReduceMovility().equals("Protesis en un miembro inferior")){
                advise = "Guie a su visitante por rampas de acceso, evite las escaleras y proporcionele un ascensor en caso de necesitarlo.";
            }
            if(user.getReduceMovility().equals("Bastón")){
                advise = "Guie a su visitante por rampas de acceso, evite las escaleras y proporcionele un ascensor en caso de necesitarlo.";
            }
            if(user.getReduceMovility().equals("Silla de ruedas")) {
                advise = "Guie a su visitante por pasillos suficientemente amplios. Inicie sistemas elevadores en caso de tenerlos y pare obstaculos como puertas giratorias";
            }
            if(user.getReduceMovility().equals("Silla de ruedas electrica")){
                advise = "Guie a su visitante por pasillos suficientemente amplios. Inicie sistemas elevadores en caso de tenerlos y pare obstaculos como puertas giratorias";
            }
            

            editTextAdvise.setText(advise);
        }

    }

    private void addOnClickListenerFabEmail(){
        if(fabEmail != null){
            fabEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String mailto = "mailto:" + SACAPPControl.getUser().getEmail() ;

                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse(mailto));

                    try {
                        startActivity(emailIntent);
                    }catch (ActivityNotFoundException e) {
                        Toast.makeText(UserDetailActivity.this, "Error. No se ha podido abrir aplicacion de mensajeria", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    private void getUserById(String userId){

        UserService userService = API.getApi().create(UserService.class);
        Call<Users> usersCall = userService.getUserById(userId);

        final String[] userName = new String[1];

        usersCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                SACAPPControl.nameuserMap.clear();

                int httpCode = response.code();

                switch(httpCode) {
                    case API.INTERNAL_SERVER_ERROR:
                        Toast.makeText(UserDetailActivity.this, response.message() + ": Error en el servidor de datos", Toast.LENGTH_LONG).show();
                        break;
                    case API.NOT_FOUND:
                        Toast.makeText(UserDetailActivity.this, response.message() + ": No encontrado", Toast.LENGTH_LONG).show();
                        break;
                    case API.OK:
                        user = response.body().getUsers().get(0);
                        fillUserData();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });

    }

}
