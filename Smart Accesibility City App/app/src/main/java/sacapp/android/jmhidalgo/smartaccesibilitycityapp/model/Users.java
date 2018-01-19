package sacapp.android.jmhidalgo.smartaccesibilitycityapp.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Users {

    @SerializedName("usuarios")
    private List<User> users = null;
    @SerializedName("total")
    private int total;

    public Users(List<User> users, int total) {
        this.users = users;
        this.total = total;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public static User parseJSON(String response){
        Gson gson = new GsonBuilder().create();
        User user = gson.fromJson(response, User.class);
        return user;
    }

}
