package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Users;


public interface UserService {

    @GET("loginUser")
    Call<User> login(@Header("email") String email, @Header("pass") String pass);

    @GET("get-usuario-id/{idUsuario}")
    Call<Users> getUserById(@Path("idUsuario") String idUsuario);

    @POST("registro")
    Call<User> register(@Body User user);

    @PUT("actualizar-usuario/{idUsuario}")
    Call<User> setUpdateData(@Body User user, @Header("autorizacion") String autorizacion, @Path("idUsuario") String idUsuario);
}
