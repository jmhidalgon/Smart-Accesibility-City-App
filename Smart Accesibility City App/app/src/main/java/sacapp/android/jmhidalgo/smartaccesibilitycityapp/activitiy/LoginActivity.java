package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.TokenService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.UserService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Token;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;

public class LoginActivity extends AppCompatActivity {

    private final String _NOMBRE = "Nombre de Usuario";
    private final boolean _GETHASH = true;

    private EditText editTextName;
    private EditText editTextPass;

    private Button buttonAccess;
    private Button buttonNewUser;

    private CheckBox checkBoxRememberme;

    private User user;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        buttonAccess = (Button) findViewById(R.id.buttonAccess);
        checkBoxRememberme = (CheckBox) findViewById(R.id.checkBoxRemenberme);

        editTextPass.setText("juanmaadmin");
        editTextName.setText("juanma@admin.com");

        buttonNewUser = (Button) findViewById(R.id.buttonNewUser);

        Intent intent = getIntent();
        if(intent.getStringExtra("email") != null || intent.getStringExtra("pass") != null) {
            editTextName.setText(intent.getStringExtra("email"));
            editTextPass.setText(intent.getStringExtra("pass"));
        }

        // Adding request to get the user
        buttonAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
                                LoginActivity.this.user = null;
                                Toast.makeText(LoginActivity.this, response.message() + ": No encontrado", Toast.LENGTH_LONG).show();
                                break;
                            }
                            case API.OK: {
                                User user = response.body();
                                if(user != null) {
                                    LoginActivity.this.user = user;
                                    if(getUserToken(user)) {
                                        Toast.makeText(LoginActivity.this, "Bienvenido " + LoginActivity.this.user.getName(), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "Email y/o contraseña no coinciden", Toast.LENGTH_LONG).show();
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
        });

        buttonNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentNewUser = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intentNewUser);
            }
        });
    }

    public Boolean getUserToken(User user){

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

    public void startMainActivity(){
        Intent intentMainActivity = new Intent(LoginActivity.this, ExplorerActivity.class);
        intentMainActivity.putExtra("token", token);
        startActivity(intentMainActivity);
    }
}