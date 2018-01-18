package sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item;

public class VisitItem
{
    private String userName;
    private String entityName;
    private String dateString;
    private Boolean outOfDate;

    public VisitItem(String userName, String entityName, String dateString, Boolean outOfDate) {
        this.userName = userName;
        this.entityName = entityName;
        this.dateString = dateString;
        this.outOfDate = outOfDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getDateString() {
        return dateString;
    }

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
