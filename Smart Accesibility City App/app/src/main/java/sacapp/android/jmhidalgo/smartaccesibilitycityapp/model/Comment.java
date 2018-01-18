package sacapp.android.jmhidalgo.smartaccesibilitycityapp.model;


import android.content.Intent;
import android.widget.Toast;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.API;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.CommentService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.accessdb.service.UserService;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy.LoginActivity;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.activitiy.UserRegisterActivity;

public class Comment
{
    @SerializedName("_id")
    private String id;
    @SerializedName("nombreUsuario")
    private String userName;
    @SerializedName("idEntidad")
    private String entityId;
    @SerializedName("rating")
    private int rating;
    @SerializedName("contenido")
    private String content;
    @Expose
    private static String responseMessage;

    public Comment(String id, String userName, String entityId, int rating, String content) {
        this.id = id;
        this.userName = userName;
        this.entityId = entityId;
        this.rating = rating;

        this.content = content;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public static boolean registrerComment(Comment comment){
        CommentService commentService = API.getApi().create(CommentService.class);
        Call<Comment> commentCall = commentService.register(comment);
        final boolean[] success = new boolean[1];
        final String[] message = new String[1];


        commentCall.enqueue(new Callback<Comment>() {
            @Override
            public void onResponse(Call<Comment> call, Response<Comment> response) {
                if(API.METHOD_NOT_ALLOWED == response.code()){
                    message[0] = response.message();
                    success[0] = false;
                } else {
                    message[0] = response.message();
                    success[0] = true;
                }
            }

            @Override
            public void onFailure(Call<Comment> call, Throwable t) {
                success[0] = false;
            }
        });

        responseMessage = message[0];
        return success[0];
    }

    public static String getResponseMessage() { return responseMessage; }

}
