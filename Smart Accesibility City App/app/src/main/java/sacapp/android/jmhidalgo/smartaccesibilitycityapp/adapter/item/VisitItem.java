package sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item;

/** Represent the ListView item for Visit
 *
 * @author Juan Manuel Hidalgo Navarro
 */
public class VisitItem
{
    private String userName;
    private String entityName;
    private String dateString;
    private Boolean outOfDate;

    /** Constructor
     *
     * @param userName Name of the visit user
     * @param entityName Name of the entity user
     * @param dateString Date of the visit
     */
    public VisitItem(String userName, String entityName, String dateString, Boolean outOfDate) {
        this.userName = userName;
        this.entityName = entityName;
        this.dateString = dateString;
        this.outOfDate = outOfDate;
    }

    /** Getter user name
     *
     * @return user name
     */
    public String getUserName() {
        return userName;
    }

    /** Setter user name
     *
     * @param userName new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** Getter entity name
     *
     * @return entity name
     */
    public String getEntityName() {
        return entityName;
    }

    /** Setter entity name
     *
     * @param entityName new entity name
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /** Getter date string
     *
     * @return Date string
     */
    public String getDateString() {
        return dateString;
    }

    /** Setter date string
     *
     * @param dateString new date string
     */
    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Boolean getOutOfDate() {
        return outOfDate;
    }

    public void setOutOfDate(Boolean outOfDate) {
        this.outOfDate = outOfDate;
    }
}
