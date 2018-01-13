package sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item;

import android.graphics.drawable.Drawable;

public class CommentItem {

    private String userName;
    private String entityName;
    private String content;
    private int rating;

    public CommentItem(String userName, String entityName, String content, int rating) {
        this.userName = userName;
        this.entityName = entityName;
        this.content = content;
        this.rating = rating;

    }

    public CommentItem() {
        super();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
