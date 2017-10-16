package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Token;

/**
 * Created by juanma on 5/10/17.
 */

public interface TokenService {
    @GET("loginUser")
    Call<Token> login(@Header("email") String email, @Header("pass") String pass, @Header("gethash") Boolean gethash);
}
