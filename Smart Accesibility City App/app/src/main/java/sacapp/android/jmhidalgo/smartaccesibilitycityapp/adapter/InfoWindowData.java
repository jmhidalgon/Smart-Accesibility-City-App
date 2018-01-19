package sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;

/** Adapter for google maps entity labels
 *
 */
public class InfoWindowData {
    private String image;
    private String website;
    private String email;

    /** Entity to show
     */
    private Entity entity;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /** Getter website
     *
     * @return entity website
     */
    public String getWebsite() {
        return website;
    }

    /** Setter website
     *
     * @param website entity website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /** Getter email
     *
     * @return entity email
     */
    public String getEmail() {
        return email;
    }

    /** Getter entity
     *
     * @return entity
     */
    public Entity getEntity() {
        return entity;
    }

    /** Setter entity
     *
     * @param entity entity
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /** Setter email
     *
     * @param email entity email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}