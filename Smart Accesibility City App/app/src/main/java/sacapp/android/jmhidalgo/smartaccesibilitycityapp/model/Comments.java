package sacapp.android.jmhidalgo.smartaccesibilitycityapp.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Comments
{
    @SerializedName("comentarios")
    private List<Comment> comments = null;

    public Comments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static Comment parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        Comment comment = gson.fromJson(response, Comment.class);
        return comment;
    }
}
