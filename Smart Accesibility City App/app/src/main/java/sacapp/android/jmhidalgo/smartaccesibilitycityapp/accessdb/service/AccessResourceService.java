package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.AccessResource;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.AccessResources;

public interface AccessResourceService
{
    @GET("get-recursos")
    Call<AccessResources> getResources(@Header("idEntidad") String idEntidad);

    @POST("registro-recurso")
    Call<AccessResource> register(@Body AccessResource accessResource);
}
