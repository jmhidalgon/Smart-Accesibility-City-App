package sacapp.android.jmhidalgo.smartaccesibilitycityapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Api;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.UserService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy.MainActivity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.util.SACAPPControl;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserConfigFragment extends Fragment {

    User user;

    private EditText editTextName;
    private EditText editTextSurname;
    private EditText editTextPass;
    private EditText editTextPassConfirm;
    private EditText editTextNewPass;
    private EditText editTextEmail;
    private Spinner spinner;

    private Button buttonUpdate;

    public UserConfigFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_user_config, container, false);

        editTextName = (EditText) rootView.findViewById(R.id.editTextName);
        editTextSurname = (EditText) rootView.findViewById(R.id.editTextSurname);
        editTextPass = (EditText) rootView.findViewById(R.id.editTextPass);
        editTextPassConfirm = (EditText) rootView.findViewById(R.id.editTextPassConfirm);
        editTextNewPass = (EditText) rootView.findViewById(R.id.editTextNewPass);
        editTextEmail = (EditText) rootView.findViewById(R.id.editTextEmail);
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        buttonUpdate = (Button) rootView.findViewById(R.id.buttonUpdate);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.reduced_movility, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        fillUserData();
        addListener();

        return rootView;
    }

    private void fillUserData(){
        user = SACAPPControl.getUser();

        if(user != null){
            editTextName.setText(user.getName());
            editTextSurname.setText(user.getSurname());
            editTextPass.setText("");
            editTextPass.setText("");
            editTextEmail.setText(user.getEmail());

            for(int i=0; i<spinner.getAdapter().getCount();++i){
                if(spinner.getItemAtPosition(i).equals(user.getName())){
                    spinner.setSelection(i);
                }
            }
        }
    }

    private void addListener(){

        if(buttonUpdate == null){
            return ;
        }

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextPass.getText().toString().isEmpty() || editTextPassConfirm.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Confirme su contraseña", Toast.LENGTH_LONG).show();
                    return ;
                }

                if(editTextEmail.getText().toString().isEmpty() || editTextName.getText().toString().isEmpty() || editTextSurname.getText().toString().isEmpty()){
                    Toast.makeText(getContext(), "Rellena todos los datos", Toast.LENGTH_LONG).show();
                    return ;
                }

                user.setEmail(editTextEmail.getText().toString());
                user.setName(editTextName.getText().toString());
                user.setSurname(editTextSurname.getText().toString());

                if(!editTextNewPass.getText().toString().isEmpty()){
                    user.setPass(editTextNewPass.getText().toString());
                }

                updateUser();
            }
        });
    }

    private void updateUser(){
        UserService userService = API.getApi().create(UserService.class);
        Call<User> userCall = userService.setUpdateData(user, ((MainActivity)getActivity()).getToken(), user.getId()); // AQUI PETA TODO

        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                int httpCode = response.code();

                switch(httpCode) {
                    case API.INTERNAL_SERVER_ERROR:
                        Toast.makeText(getContext(), response.message() + ": Error en el servidor de datos", Toast.LENGTH_LONG).show();
                        break;
                    case API.NOT_FOUND:
                        Toast.makeText(getContext(), response.message() + ": No encontrado", Toast.LENGTH_LONG).show();
                        break;
                    case API.OK:
                        User user = response.body();

                        if(user!= null){
                            Toast.makeText(getContext(), "Usuario actualizado correctamente", Toast.LENGTH_LONG).show();
                        }

                        break;
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getContext(), "Error al actualizar usuario", Toast.LENGTH_LONG).show();
            }
        });

    }


}
