package sacapp.android.jmhidalgo.smartaccesibilitycityapp.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Entities {

    @SerializedName("entidads")
    private List<Entity> entities = null;
    @SerializedName("total")
    private int total;

    public Entities(List<Entity> entities, int total) {
        this.entities = entities;
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<Entity> getEntities(){
        return entities;
    }

    public void setEntities(List<Entity> entities){
        this.entities = entities;
    }

    public static Entity parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        Entity entity = gson.fromJson(response, Entity.class);
        return entity;
    }
}
