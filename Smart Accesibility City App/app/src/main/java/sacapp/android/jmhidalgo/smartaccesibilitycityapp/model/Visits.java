package sacapp.android.jmhidalgo.smartaccesibilitycityapp.model;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Visits {
    @SerializedName("visitas")
    private List<Visit> visits = null;

    public Visits(List<Visit> visits) {
        this.visits = visits;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }

    public static Visit parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        Visit visit = gson.fromJson(response, Visit.class);
        return visit;
    }

}
