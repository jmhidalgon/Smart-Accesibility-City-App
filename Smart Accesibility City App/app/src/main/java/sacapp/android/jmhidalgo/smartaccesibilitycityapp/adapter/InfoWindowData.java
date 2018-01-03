package sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Entity;

public class InfoWindowData {
    private String image;
    private String website;
    private String email;

    private Entity entity;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEmail() {
        return email;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}