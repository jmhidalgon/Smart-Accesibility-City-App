package sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item.AccessItem;


public class AdapterAccessItem extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<AccessItem> items;

    public AdapterAccessItem (Activity activity, ArrayList<AccessItem> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<AccessItem> accessItems) {
        for (int i = 0; i < accessItems.size(); i++) {
            items.add(accessItems.get(i));
        }
    }

    @Override
    public Object getItem(int arg0) {
        return items.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inf.inflate(R.layout.item_access_layout, null);
        }

        AccessItem dir = items.get(position);
        TextView description = (TextView) v.findViewById(R.id.texto);
        description.setText(dir.getNombre());

        return v;
    }
}