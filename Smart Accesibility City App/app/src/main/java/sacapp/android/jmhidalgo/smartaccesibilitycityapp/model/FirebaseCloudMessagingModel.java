package sacapp.android.jmhidalgo.smartaccesibilitycityapp.model;


import com.google.gson.annotations.SerializedName;

public class FirebaseCloudMessagingModel {

    @SerializedName("userId")
    private String userId;
    @SerializedName("entityId")
    private String entityId;
    @SerializedName("entityTokenKey")
    private String  entityToken;
    @SerializedName("messaging")
    private String messaging;
    @SerializedName("content")
    private String content;

    public FirebaseCloudMessagingModel(String userId, String entityId, String messaging, String content, String  entityToken) {
        this.userId = userId;
        this.entityId = entityId;
        this.messaging = messaging;
        this.content = content;
        this.entityToken = entityToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getMessaging() {
        return messaging;
    }

    public void setMessaging(String messaging) {
        this.messaging = messaging;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEntityToken() {
        return entityToken;
    }

    public void setEntityToken(String entityToken) {
        this.entityToken = entityToken;
    }
}
