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

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.util.SACAPPControl;

public class UserDetailActivity extends AppCompatActivity {

    private TextView textViewUserName;
    private EditText editText;
    private TextView textViewMovility;
    private EditText editTextAdvise;
    private FloatingActionButton fabEmail;

    private User user;
    private String stringDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Intent intent = getIntent();
        if(intent != null){
            if(intent.getStringExtra("userId") != null){
                String userId = intent.getStringExtra("userId");
                stringDate = intent.getStringExtra("stringDate");

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
        editText = (EditText) findViewById(R.id.editText);
        textViewMovility = (TextView) findViewById(R.id.textViewMovility);
        editTextAdvise = (EditText) findViewById(R.id.editTextAdvise);
        fabEmail = (FloatingActionButton) findViewById(R.id.fabEmail);

        if(user == null){
            finishActivity(0);
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
            editText.setText(stringDate.split(" ")[0]);
            textViewMovility.setText(textViewMovility.getText() + " " + user.getReduceMovility());

            switch (user.getReduceMovility()){
                case "Muletas":
                case "Bastón":
                case "Silla de ruedas":
                case "Silla de ruedas electrica":
                case "Andador":
                case "Protesis en un miembro inferior":

            }

            editTextAdvise.setText("Consejo");
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

}
