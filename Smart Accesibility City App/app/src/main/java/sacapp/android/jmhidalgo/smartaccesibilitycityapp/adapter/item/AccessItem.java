package sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item;


public class AccessItem {

    private String nombre;
    private String idEntidad;

    public AccessItem() {
        super();
    }

    public AccessItem(String nombre, String idEntidad) {
        this.nombre = nombre;
        this.idEntidad = idEntidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdEntidad() {
        return idEntidad;
    }

    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
    }
}