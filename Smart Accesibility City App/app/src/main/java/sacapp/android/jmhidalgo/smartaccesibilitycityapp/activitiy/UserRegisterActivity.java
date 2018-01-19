package sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.UserService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;

/** User register activity
 *
 * @author Juan Manuel Hidalgo Navarro
 */
public class UserRegisterActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextPass;
    private EditText editTextPassConfirmed;
    private EditText editTextEmail;
    private Button buttonAccept;
    private Spinner spinnerReason;

    /** OnCreate inhered method
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        // Getting view components
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSurname = (EditText) findViewById(R.id.editTextSurname);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        editTextPassConfirmed = (EditText) findViewById(R.id.editTextPassConfirmed);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonAccept = (Button) findViewById(R.id.buttonAccept);
        spinnerReason = (Spinner) findViewById(R.id.spinnerReason);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.reduced_movility, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerReason.setAdapter(adapter);

        // Adding click listener to save the user
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String surname = editTextSurname.getText().toString();
                String pass1 = editTextPass.getText().toString();
                String pass2 = editTextPassConfirmed.getText().toString();
                String email = editTextEmail.getText().toString();
                String reducedMovility = spinnerReason.getSelectedItem().toString();

                if(!pass1.equals(pass2)){
                    Toast.makeText(UserRegisterActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                    editTextPass.requestFocus();
                    return ;
                }

                User user = new User("", name, surname, reducedMovility, email, pass1, "ROLE_USER", "");

                UserService userService = API.getApi().create(UserService.class);
                Call<User> userCall = userService.register(user);

                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        if(API.METHOD_NOT_ALLOWED == response.code()){
                            Toast.makeText(UserRegisterActivity.this, response.message() + ": Email ya registrado en la aplicación", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(UserRegisterActivity.this, "Usuario registrado", Toast.LENGTH_LONG).show();
                            Intent intentBackLogin = new Intent(UserRegisterActivity.this, LoginActivity.class);
                            intentBackLogin.putExtra("email", editTextEmail.getText().toString());
                            intentBackLogin.putExtra("pass", editTextPass.getText().toString());
                            startActivity(intentBackLogin);
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(UserRegisterActivity.this, "Error al registrar usuario.", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
