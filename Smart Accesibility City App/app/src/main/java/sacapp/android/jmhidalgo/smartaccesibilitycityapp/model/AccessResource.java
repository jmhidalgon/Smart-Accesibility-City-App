package sacapp.android.jmhidalgo.smartaccesibilitycityapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AccessResource
{
    @SerializedName("nombreRecurso")
    private String resourceName;
    @SerializedName("idEntidad")
    private String entityId;

    public AccessResource(String resourceName, String entityId) {
        this.resourceName = resourceName;
        this.entityId = entityId;
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
