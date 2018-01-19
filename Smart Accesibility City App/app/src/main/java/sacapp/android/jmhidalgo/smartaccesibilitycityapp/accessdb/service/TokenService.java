package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Token;

/** Interface to access to our Token web service methods
 *
 * @author Juan Manuel Hidalgo Navarro
 */
public interface TokenService {
    /** Getter method to get the token for authorization methods by the email and pass
     *
     * @param email User email
     * @param pass User pass
     * @param gethash That must be true to get the token
     * @return The token string for authorization methods
     */
    @GET("loginUser")
    Call<Token> login(@Header("email") String email, @Header("pass") String pass, @Header("gethash") Boolean gethash);
}
