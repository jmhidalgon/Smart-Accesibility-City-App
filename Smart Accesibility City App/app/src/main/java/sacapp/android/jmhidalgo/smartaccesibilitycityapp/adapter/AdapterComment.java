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
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item.AccessItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.item.CommentItem;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.Comment;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.model.User;

public class AdapterComment extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<CommentItem> items;
    private int layout;

    public AdapterComment (Activity activity, int layout, ArrayList<CommentItem> items) {
        this.activity = activity;
        this.items = items;
        this.layout = layout;
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<CommentItem> commentItems) {
        for (int i = 0; i < commentItems.size(); i++) {
            items.add(commentItems.get(i));
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
            v = inf.inflate(R.layout.comment_item, null);
        }

        CommentItem dir = items.get(i);

        TextView title = (TextView) v.findViewById(R.id.textViewUsarName);
        title.setText(dir.getUserName());

        TextView description = (TextView) v.findViewById(R.id.textViewComment);
        description.setText(dir.getContent());

        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.ratingBar);
        ratingBar.setMax(5);
        ratingBar.setRating(dir.getRating());

        return v;
    }
}
