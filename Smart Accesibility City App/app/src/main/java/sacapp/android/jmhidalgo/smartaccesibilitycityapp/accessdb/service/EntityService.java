package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entities;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Visits;

public interface EntityService {
    @GET("loginEntidad")
    Call<Entity> login(@Header("email") String email, @Header("pass") String pass);

    @POST("registro-entidad")
    Call<Entity> register(@Body Entity entity);

    @POST("registro-entidad-publica")
    Call<Entity> registerPublic(@Body Entity entity);

    @GET("get-entidades")
    Call<Entities> getEntities(@Header("autorizacion") String auth);

    @GET("get-entidad-id/{idEntidad}")
    Call<Entities> getEntityById(@Path("idEntidad") String idEntidad);
}
