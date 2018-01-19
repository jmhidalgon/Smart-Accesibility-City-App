package sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item;

import android.graphics.drawable.Drawable;

/** Represent the ListView item for Comment
 *
 * @author Juan Manuel Hidalgo Navarro
 */
public class CommentItem {

    private String userName;
    private String entityName;
    private String content;
    private int rating;

    /** Constructor
     *
     * @param userName User comment name
     * @param entityName Entity comment name
     * @param content Comment content
     * @param rating Comment rating
     */
    public CommentItem(String userName, String entityName, String content, int rating) {
        this.userName = userName;
        this.entityName = entityName;
        this.content = content;
        this.rating = rating;

    }

    /** Empty contructor
     */
    public CommentItem() {
        super();
    }

    /** Getter User name
     *
     * @return User comment name
     */
    public String getUserName() {
        return userName;
    }

    /** Setter User name
     *
     * @param userName new user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** Getter Entity name
     *
     * @return Entity name
     */
    public String getEntityName() {
        return entityName;
    }

    /** Setter Entity name
     *
     * @param entityName
     */
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    /** Getter comment  content
     *
     * @return comment content
     */
    public String getContent() {
        return content;
    }

    /** Setter comment content
     *
     * @param content comment content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /** Getter comment ratting
     *
     * @return comment ratting
     */
    public int getRating() {
        return rating;
    }

    /** Setter comment ratting
     *
     * @param rating comment ratting
     */
    public void setRating(int rating) {
        this.rating = rating;
    }
}
