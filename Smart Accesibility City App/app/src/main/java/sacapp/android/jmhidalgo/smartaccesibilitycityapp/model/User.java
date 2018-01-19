package sacapp.android.jmhidalgo.smartaccesibilitycityapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("_id")
    private String id;
    @SerializedName("nombre")
    private String name;
    @SerializedName("apellidos")
    private String surname;
    @SerializedName("tipoMovilidadReducida")
    private String reduceMovility;
    @SerializedName("email")
    private String email;
    @SerializedName("pass")
    private String pass;
    @SerializedName("rol")
    private String rol;
    @SerializedName("imagen")
    private String image;
    private boolean gethash;

    public User(String id, String name, String surname, String reduceMovility, String email, String pass, String rol, String image) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.reduceMovility = reduceMovility;
        this.email = email;
        this.pass = pass;
        this.rol = rol;
        this.image = image;
        this.gethash = false;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getReduceMovility() {
        return reduceMovility;
    }

    public String getEmail() {
        return email;
    }

    public String getPass() {
        return pass;
    }

    public String getRol() {
        return rol;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isGethash() {
        return gethash;
    }

    public void setGethash(boolean gethash) {
        this.gethash = gethash;
    }

    public void setReduceMovility(String reduceMovility) {
        this.reduceMovility = reduceMovility;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public void setImage(String image) {
        this.image = image;
    }

};

