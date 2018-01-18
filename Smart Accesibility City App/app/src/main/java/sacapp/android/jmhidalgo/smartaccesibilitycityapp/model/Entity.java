package sacapp.android.jmhidalgo.smartaccesibilitycityapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Entity implements Parcelable{
    @SerializedName("_id")
    private String id;
    @SerializedName("nombreEntidad")
    private String entityname;
    @SerializedName("email")
    private String email;
    @SerializedName("pass")
    private String pass;
    @SerializedName("rol")
    private String rol;
    @SerializedName("imagen")
    private String image;
    @SerializedName("direccion")
    private String adress;
    @SerializedName("longitud")
    private double longitud;
    @SerializedName("latitud")
    private double latitud;
    @SerializedName("website")
    private String website;
    private boolean gethash;

    public Entity(String id, String entityname, String email, String pass, String rol, String image, String adress, double longitud, double latitud, String website) {
        this.id = id;
        this.entityname = entityname;
        this.email = email;
        this.pass = pass;
        this.rol = rol;
        this.image = image;
        this.adress = adress;
        this.longitud = longitud;
        this.latitud = latitud;
        this.website = website;
    }


    protected Entity(Parcel in) {
        id = in.readString();
        entityname = in.readString();
        email = in.readString();
        pass = in.readString();
        rol = in.readString();
        image = in.readString();
        adress = in.readString();
        longitud = in.readDouble();
        latitud = in.readDouble();
        website = in.readString();
        gethash = in.readByte() != 0;
    }

    public static final Creator<Entity> CREATOR = new Creator<Entity>() {
        @Override
        public Entity createFromParcel(Parcel in) {
            return new Entity(in);
        }

        @Override
        public Entity[] newArray(int size) {
            return new Entity[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntityname() {
        return entityname;
    }

    public void setEntityname(String entityname) {
        this.entityname = entityname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isGethash() {
        return gethash;
    }

    public void setGethash(boolean gethash) {
        this.gethash = gethash;
    }


    @Override
    public int describeContents() {
        return 10;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(entityname);
        parcel.writeString(email);
        parcel.writeString(pass);
        parcel.writeString(rol);
        parcel.writeString(image);
        parcel.writeString(adress);
        parcel.writeDouble(longitud);
        parcel.writeDouble(latitud);
        parcel.writeString(website);
    }
}
