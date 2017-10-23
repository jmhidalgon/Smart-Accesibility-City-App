package sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item;


import android.graphics.drawable.Drawable;

public class AccessItem {

    private String tittle;
    private String categoryId;
    private String description;
    private Drawable imagen;

    public AccessItem() {
        super();
    }

    public AccessItem(String categoryId, String title, String description, Drawable imagen) {
        super();
        this.tittle = title;
        this.description = description;
        this.imagen = imagen;
        this.categoryId = categoryId;
    }


    public String getTittle() {
        return tittle;
    }

    public void setTittle(String title) {
        this.tittle = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getImage() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }

    public String getCategoryId(){return categoryId;}

    public void setCategoryId(String categoryId){this.categoryId = categoryId;}

}