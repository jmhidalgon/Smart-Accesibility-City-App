package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Visit;

public interface VisitService
{
    /*@GET("get-visitas/{idEntidad}")
    Call<Visits> getVisitsByEntity(@Path("idEntidad") String idEntidad);

    @GET("get-visitas/{idUsuario}")
    Call<Visits> getVisitsByUser(@Path("idUsuario") String idUsuario);*/

    @POST("registro-visita")
    Call<Visit> register(@Body Visit visit);
}
