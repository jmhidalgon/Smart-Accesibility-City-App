package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Visit;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Visits;

public interface VisitService
{
    @GET("get-visita-entidad/{idEntidad}")
    Call<Visits> getVisitsByEntity(@Path("idEntidad") String idEntidad);

    @GET("get-visita-usuario/{idUsuario}")
    Call<Visits> getVisitsByUser(@Path("idUsuario") String idUsuario);

    @POST("registro-visita")
    Call<Visit> register(@Body Visit visit);
}
