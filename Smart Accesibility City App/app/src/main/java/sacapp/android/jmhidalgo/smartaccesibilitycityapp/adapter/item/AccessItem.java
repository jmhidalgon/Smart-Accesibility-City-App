package sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item;

/** Represent the ListView item for AccessResource
 *
 * @author Juan Manuel Hidalgo Navarro
 */
public class AccessItem {

    /** AccessItem name
     */
    private String nombre;
    /** AccessItem Entity
     *
     */
    private String idEntidad;

    /** Empty Constructor
     *
     */
    public AccessItem() {
        super();
    }

    /** Constructor
     *
     * @param nombre name of the access resource
     * @param idEntidad entity id
     */
    public AccessItem(String nombre, String idEntidad) {
        this.nombre = nombre;
        this.idEntidad = idEntidad;
    }

    /** Name getter
     *
     * @return access resource name
     */
    public String getNombre() {
        return nombre;
    }

    /** Name setter
     *
     * @param nombre new access resource setter
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** Entity ID getter
     *
     * @return Entity ID of the resource
     */
    public String getIdEntidad() {
        return idEntidad;
    }

    /** Entity ID setter
     *
     * @param idEntidad new id entity setter
     */
    public void setIdEntidad(String idEntidad) {
        this.idEntidad = idEntidad;
    }
}