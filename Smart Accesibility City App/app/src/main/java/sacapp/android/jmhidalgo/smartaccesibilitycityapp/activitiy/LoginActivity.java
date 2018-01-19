package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.internal.ParcelableSparseArray;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.EntityService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.util.SACAPPControl;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.util.Util;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.TokenService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.UserService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Token;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;

/** Activity to Login in the app
 *
 * @author Juan Manuel Hidalgo Navarro
 */
public class LoginActivity extends AppCompatActivity {

    private SharedPreferences prefs;

    private final String _NOMBRE = "Nombre de Usuario";
    private final boolean _GETHASH = true;
    private Toolbar toolbar;

    private EditText editTextName;
    private EditText editTextPass;

    private Button buttonAccess;
    private Button buttonNewUser;
    private Button buttonNewEntity;

    private CheckBox checkBoxRememberme;

    private User user;
    private Entity entity;

    private String token;

    /** OnCreate Inhered Method
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Load the toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        buttonAccess = (Button) findViewById(R.id.buttonAccess);
        checkBoxRememberme = (CheckBox) findViewById(R.id.checkBoxRemenberme);

        /*editTextPass.setText("juanmaadmin");
        editTextName.setText("juanma@admin.com");*/

        prefs = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        setCredentialsIfExist();


        buttonNewUser = (Button) findViewById(R.id.buttonNewUser);
        buttonNewEntity = (Button) findViewById(R.id.buttonNewEntity);

        final Intent intent = getIntent();
        if(intent.getStringExtra("email") != null || intent.getStringExtra("pass") != null) {
            editTextName.setText(intent.getStringExtra("email"));
            editTextPass.setText(intent.getStringExtra("pass"));
        }

        // Adding request to get the user
        buttonAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            appAccess();
            }
        });

        buttonNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intentNewUser = new Intent(LoginActivity.this, UserRegisterActivity.class);
            startActivity(intentNewUser);
            }
        });

        buttonNewEntity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intentNewEntity = new Intent(LoginActivity.this, EntityRegisterActivity.class);
            startActivity(intentNewEntity);
            }
        });
    }

    /** Set email and password if they exist
     */
    private void setCredentialsIfExist(){
        String email = Util.getUserMailPrefs(prefs);
        String password = Util.getUserPassPrefs(prefs);
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
            editTextName.setText(email);
            editTextPass.setText(password);
            checkBoxRememberme.setChecked(true);
        }
    }

    /** Remember checkbox functionality
     *
     * @param email Login email
     * @param password Login pass
     */
    private void saveOnPreferences(String email, String password) {
        if (checkBoxRememberme.isChecked()) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("email", email);
            editor.putString("pass", password);
            editor.apply();
        }
    }

    /** Method to get the user authorization token
     *
     * @param user user to get the token
     * @return
     */
    private Boolean getUserToken(User user){

        TokenService tokenService = API.getApi().create(TokenService.class);
        Call<Token> tokenSCall = tokenService.login(user.getEmail(), user.getPass(), _GETHASH);

        tokenSCall.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                int httpCode = response.code();
                switch(httpCode){
                    case API.INTERNAL_SERVER_ERROR: {
                        LoginActivity.this.token = "";
                        Toast.makeText(LoginActivity.this, response.message() + ": Error interno del servidor", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case API.NOT_FOUND: {
                        LoginActivity.this.token = "";
                        Toast.makeText(LoginActivity.this, response.message() + ": No encontrado", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case API.OK: {
                        String tokenResponse = response.body().getStringToken();
                        if(tokenResponse != "") {
                            LoginActivity.this.token = tokenResponse;
                            Toast.makeText(LoginActivity.this, "Bienvenido " + LoginActivity.this.user.getName(), Toast.LENGTH_LONG).show();
                            startMainActivity();
                        } else {
                            Toast.makeText(LoginActivity.this, "Email y/o contraseña no coinciden", Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                    default: {
                        LoginActivity.this.token = "";
                        Toast.makeText(LoginActivity.this, response.message() + ": Error desconocido", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Ha habido un fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        return LoginActivity.this.token != null;
    }

    /** Method to start the main activity
     */
    public void startMainActivity(){
        if(user == null){
            Toast.makeText(LoginActivity.this, "Ha habido un fallo de autenticación", Toast.LENGTH_LONG).show();
            return ;
        }
        SACAPPControl.setUser(user);

        saveOnPreferences(editTextName.getText().toString(), editTextPass.getText().toString());
        Intent intentMainActivity = new Intent(LoginActivity.this, MainActivity.class);
        intentMainActivity.putExtra("token", token);
        intentMainActivity.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intentMainActivity);
    }

    /** Method to app access
     */
    public void appAccess(){
        String userName = editTextName.getText().toString();
        String pass = editTextPass.getText().toString();

        UserService userService = API.getApi().create(UserService.class);
        Call<User> userCall = userService.login(userName, pass);

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {

                int httpCode = response.code();
                switch(httpCode){
                    case API.INTERNAL_SERVER_ERROR: {
                        LoginActivity.this.user = null;
                        Toast.makeText(LoginActivity.this, response.message() + ": Error interno del servidor", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case API.NOT_FOUND: {
                        entityAccess();
                        /*LoginActivity.this.user = null;
                        Toast.makeText(LoginActivity.this, response.message() + ": No encontrado", Toast.LENGTH_LONG).show();*/
                        break;
                    }
                    case API.OK: {
                        User user = response.body();
                        if(user != null) {
                            LoginActivity.this.user = user;
                            if(getUserToken(user)) {
                                Toast.makeText(LoginActivity.this, "Bienvenido " + LoginActivity.this.user.getName(), Toast.LENGTH_LONG).show();
                            }
                        }
                        break;
                    }
                    default: {
                        LoginActivity.this.user = null;
                        Toast.makeText(LoginActivity.this, response.message() + ": Error desconocido", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Ha habido un fallo de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /** Method for entities to app access
     */
    private void entityAccess(){
        String email = editTextName.getText().toString();
        String pass = editTextPass.getText().toString();


        EntityService entityService = API.getApi().create(EntityService.class);
        Call<Entity> entityCall = entityService.login(email, pass);

        entityCall.enqueue(new Callback<Entity>() {
            @Override
            public void onResponse(Call<Entity> call, Response<Entity> response) {

                int httpCode = response.code();
                switch(httpCode){
                    case API.INTERNAL_SERVER_ERROR: {
                        LoginActivity.this.user = null;
                        Toast.makeText(LoginActivity.this, response.message() + ": Error interno del servidor", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case API.NOT_FOUND: {
                        LoginActivity.this.user = null;
                        Toast.makeText(LoginActivity.this, response.message() + ": No encontrado", Toast.LENGTH_LONG).show();
                        break;
                    }
                    case API.OK: {
                        Entity entity = response.body();
                        if(entity != null) {
                            LoginActivity.this.entity = entity;
                            Toast.makeText(LoginActivity.this, "Bienvenido " + LoginActivity.this.entity.getEntityname(), Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(LoginActivity.this, "Email y/o contraseña no coinciden", Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                    default: {
                        LoginActivity.this.entity = null;
                        Toast.makeText(LoginActivity.this, response.message() + ": Error desconocido", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<Entity> call, Throwable t) {

            }
        });
    }
}
