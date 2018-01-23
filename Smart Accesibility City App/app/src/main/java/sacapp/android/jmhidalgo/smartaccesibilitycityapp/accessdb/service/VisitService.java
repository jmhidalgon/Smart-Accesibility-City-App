package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Visit;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Visits;

/** Interface to access to our Token web service methods
 *
 * @author Juan Manuel Hidalgo Navarro
 */
public interface VisitService
{
    /** Getter method to get Visits by Entity ID
     *
     * @param idEntidad Entity ID which the visits are
     * @return The visits that the entity has
     */
    @GET("get-visita-entidad/{idEntidad}")
    Call<Visits> getVisitsByEntity(@Path("idEntidad") String idEntidad);

    /** Visits Getter method by the user ID
     *
     * @param idUsuario User ID which the visits are
     * @return The visits that the user has
     */
    @GET("get-visita-usuario/{idUsuario}")
    Call<Visits> getVisitsByUser(@Path("idUsuario") String idUsuario);

    /** Method to register an Visit object
     *
     * @param visit The visit object to register
     * @return Registered visit
     */
    @POST("registro-visita")
    Call<Visit> register(@Body Visit visit);

    /** Method to update a Visit object
     *
     * @param visit The visit object to update
     * @param idVisita the id of the visit to update
     * @return Registered visit
     */
    @POST("actualizar-token-visita/{idVisita}")
    Call<Visit> updateVisit(@Body Visit visit, @Path("idVisita") String idVisita);

}
