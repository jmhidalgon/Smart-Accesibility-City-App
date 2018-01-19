package sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item.CommentItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item.VisitItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Visit;

/** Adapter for Visit in ListView
 *
 */
public class AdapterVisit extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<VisitItem> items;
    private int layout;

    /** Constructor
     *
     * @param activity The current activity
     * @param layout Layout to represent the item
     * @param items List of visits items
     */
    public AdapterVisit(Activity activity, int layout, ArrayList<VisitItem> items) {
        this.activity = activity;
        this.items = items;
        this.layout = layout;
    }

    /**
     * @deprecated
     */
    public void clear() {
        items.clear();
    }

    /**
     * @deprecated
     */
    public void addAll(ArrayList<VisitItem> visitItems) {
        for (int i = 0; i < visitItems.size(); i++) {
            items.add(visitItems.get(i));
        }
    }

    /** Getter size
     *
     * @return number of items
     */
    @Override
    public int getCount() {
        return items.size();
    }

    /** Getter Item
     *
     * @param i index of the requested item
     * @return AccesItem
     */
    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    /** Getter Visit item format in a view
     *
     * @param i index of the requested item
     * @param v convert view
     * @param viewGroup Parent view group
     * @return item view ready for the ListView
     */
    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {

        if (v == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_visit, null);
        }

        VisitItem dir = items.get(i);

        TextView textViewEntityName = (TextView) v.findViewById(R.id.textViewEntity);
        textViewEntityName.setText(dir.getEntityName());

        TextView textViewDate = (TextView) v.findViewById(R.id.textViewDate);
        textViewDate.setText("fecha de visita: " + dir.getDateString());

        ImageView imageViewOutOfDate = (ImageView) v.findViewById(R.id.imageViewOutOfDate);
        imageViewOutOfDate.setImageResource(dir.getOutOfDate() ? android.R.drawable.ic_menu_close_clear_cancel : android.R.drawable.ic_menu_myplaces);

        return v;
    }

}
