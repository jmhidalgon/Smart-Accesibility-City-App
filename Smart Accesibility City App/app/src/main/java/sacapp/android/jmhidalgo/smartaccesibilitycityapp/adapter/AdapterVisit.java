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

public class AdapterVisit extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<VisitItem> items;
    private int layout;

    public AdapterVisit(Activity activity, int layout, ArrayList<VisitItem> items) {
        this.activity = activity;
        this.items = items;
        this.layout = layout;
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<VisitItem> visitItems) {
        for (int i = 0; i < visitItems.size(); i++) {
            items.add(visitItems.get(i));
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

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
