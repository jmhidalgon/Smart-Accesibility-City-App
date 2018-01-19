package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.AccessResource;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.AccessResources;


/** Interface to access to our AccessResource web service methods
 *
 * @author Juan Manuel Hidalgo Navarro
 */
public interface AccessResourceService
{
    /** Getter AccessResource method.
     *
     * @param idEntidad ID of the Entity to get
     * @return AccessResources with one Entity if the server found it. A AccessResources empty in other case
     */
    @GET("get-recursos/{idEntidad}")
    Call<AccessResources> getResources(@Path("idEntidad") String idEntidad);

    /** Method to register an AccessResource object
     *
     * @param accessResource
     * @return AccessResource to post
     */
    @POST("registro-recurso")
    Call<AccessResource> register(@Body AccessResource accessResource);
}
