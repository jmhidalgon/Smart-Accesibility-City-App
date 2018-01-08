package sacapp.android.jmhidalgo.smartaccesibilitycityapp.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class AccessResources {
    @SerializedName("recursos")
    private List<AccessResource> accessResources = null;

    public AccessResources(List<AccessResource> accessResources) {
        this.accessResources = accessResources;
    }

    public List<AccessResource> getAccessResources() {
        return accessResources;
    }

    public void setAccessResources(List<AccessResource> accessResources) {
        this.accessResources = accessResources;
    }

    public static AccessResource parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        AccessResource accessResource = gson.fromJson(response, AccessResource.class);
        return accessResource;
    }
}
