package sacapp.android.jmhidalgo.smartaccesibilitycityapp.model;

import com.google.gson.annotations.SerializedName;

public class Visit {

    @SerializedName("idUsuario")
    private String userId;
    @SerializedName("idEntidad")
    private String entityId;
    @SerializedName("fecha")
    private String stringDate;

    public Visit(String userId, String entityId, String stringDate) {
        this.userId = userId;
        this.entityId = entityId;
        this.stringDate = stringDate;
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

    public String getStringDate() {
        return stringDate;
    }

    public void setStringDate(String stringDate) {
        this.stringDate = stringDate;
    }
}
