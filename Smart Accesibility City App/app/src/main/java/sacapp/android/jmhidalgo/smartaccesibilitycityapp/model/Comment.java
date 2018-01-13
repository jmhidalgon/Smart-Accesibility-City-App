package sacapp.android.jmhidalgo.smartaccesibilitycityapp.model;


import com.google.gson.annotations.SerializedName;

public class Comment
{
    @SerializedName("nombreUsuario")
    private String userName;
    @SerializedName("idEntidad")
    private String entityId;
    @SerializedName("rating")
    private int rating;
    @SerializedName("contenido")
    private String content;

    public Comment(String userName, String entityId, int rating, String content) {
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
}
