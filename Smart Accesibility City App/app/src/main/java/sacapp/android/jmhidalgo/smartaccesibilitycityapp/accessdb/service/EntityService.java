package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entities;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;

/** Interface to access to our Entity web service methods
 *
 * @author Juan Manuel Hidalgo Navarro
 */
public interface EntityService {

    /** Method to check email and pass of a entity
     *
     * @param email Email of the entity
     * @param pass Pass of the entity
     * @return Entity with match with email and pass
     */
    @GET("loginEntidad")
    Call<Entity> login(@Header("email") String email, @Header("pass") String pass);


    /** Method to post a entity
     *
     * @param entity Entity to register
     * @return Registered entity
     */
    @POST("registro-entidad")
    Call<Entity> register(@Body Entity entity);

    /** Method to post a public entity
     *
     * @param entity Public entity to register
     * @return Registered public entity
     */
    @POST("registro-entidad-publica")
    Call<Entity> registerPublic(@Body Entity entity);

    /** Getter all entities
     *
     * @param auth Token to get access to the method
     * @return Entities object with all the entities
     */
    @GET("get-entidades")
    Call<Entities> getEntities(@Header("autorizacion") String auth);

    /** Getter method by entity ID
     *
     * @param idEntidad Entity ID
     * @return Entity witch match with entity ID
     */
    @GET("get-entidad-id/{idEntidad}")
    Call<Entities> getEntityById(@Path("idEntidad") String idEntidad);

    @PUT("actualizar-entidad/{idEntidad}")
    Call<Entity> setUpdateData(@Body Entity entity, @Path("idEntidad") String idEntidad);
}
