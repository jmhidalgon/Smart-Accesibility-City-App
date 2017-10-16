package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;

public interface EntityService {
    @GET("loginEntidad")
    Call<Entity> login(@Header("email") String email, @Header("pass") String pass);

    @POST("registro-entidad")
    Call<Entity> register(@Body User user);
}
