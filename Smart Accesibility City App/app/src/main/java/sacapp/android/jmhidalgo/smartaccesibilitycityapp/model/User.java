package sacapp.android.jmhidalgo.smartaccesibilitycityapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class User implements Parcelable {
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

    protected User(Parcel in) {
        id = in.readString();
        name = in.readString();
        surname = in.readString();
        reduceMovility = in.readString();
        email = in.readString();
        pass = in.readString();
        rol = in.readString();
        image = in.readString();
        gethash = in.readByte() != 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 9;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeString(reduceMovility);
        parcel.writeString(email);
        parcel.writeString(pass);
        parcel.writeString(rol);
        parcel.writeString(image);
        parcel.writeByte((byte) (gethash ? 1 : 0));
    }
};

