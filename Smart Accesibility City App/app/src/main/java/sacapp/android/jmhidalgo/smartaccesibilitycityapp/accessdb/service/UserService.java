package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;


public interface UserService {

    @GET("loginUser")
    Call<User> login(@Header("email") String email, @Header("pass") String pass);

    @POST("registro")
    Call<User> register(@Body User user);
}
