package sacapp.android.jmhidalgo.smartaccesibilitycityapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccessResource
{
    @SerializedName("_id")
    private String id;
    @SerializedName("nombreRecurso")
    private String resourceName;
    @SerializedName("idEntidad")
    private String entityId;

    public AccessResource(String id, String resourceName, String entityId) {
        this.id = id;
        this.resourceName = resourceName;
        this.entityId = entityId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }
}
