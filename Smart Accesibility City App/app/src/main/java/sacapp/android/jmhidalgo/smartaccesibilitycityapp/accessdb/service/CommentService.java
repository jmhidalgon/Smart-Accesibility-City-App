package sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Comment;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Comments;


public interface CommentService {
    @GET("get-comentarios/{idEntidad}")
    Call<Comments> getComments(@Path("idEntidad") String idEntidad);

    @POST("registro-recurso")
    Call<Comment> register(@Body Comment comment);
}
