package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Comment;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Comments;

/** Interface to access to our Comment web service methods
 *
 * @author Juan Manuel Hidalgo Navarro
 */
public interface CommentService {
    /** Getter comment method
     *
     * @param idEntidad Entity ID which has the comment that we want
     * @return Comments with the Comment found
     */
    @GET("get-comentarios/{idEntidad}")
    Call<Comments> getComments(@Path("idEntidad") String idEntidad);

    /** Method to register an Comment object
     *
     * @param comment Comment to register
     * @return the comment registered
     */
    @POST("registro-comentario")
    Call<Comment> register(@Body Comment comment);
}
