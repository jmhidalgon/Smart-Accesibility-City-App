package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.FirebaseCloudMessagingModel;

public interface FirebaseCloudMessagingService {

    @GET("notify/{entityTokenKey}/{userid}")
    Call<FirebaseCloudMessagingModel> sendNotification(@Path("entityTokenKey") String entityTokenKey,
                                                       @Path("userid") String userid);
}
