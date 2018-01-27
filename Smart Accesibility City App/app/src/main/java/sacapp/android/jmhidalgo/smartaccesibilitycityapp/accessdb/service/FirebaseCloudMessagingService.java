package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;


import retrofit2.Call;
import retrofit2.http.POST;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.FirebaseCloudMessagingModel;

public interface FirebaseCloudMessagingService {

    @POST("notify")
    Call<FirebaseCloudMessagingModel> getResources(/*@Path("Authorization") String Authorization, @Path("Content-Type") String contentType = "application/json"*/);

}
