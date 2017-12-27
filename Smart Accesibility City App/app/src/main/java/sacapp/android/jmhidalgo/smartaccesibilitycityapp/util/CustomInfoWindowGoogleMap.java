package sacapp.android.jmhidalgo.smartaccesibilitycityapp.util;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter.InfoWindowData;

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.map_custom_infowindow, null);

        TextView name_tv = view.findViewById(R.id.name);
        TextView address_tv = view.findViewById(R.id.address);
        ImageView img = view.findViewById(R.id.pic);

        TextView website_tv = view.findViewById(R.id.website);
        TextView email_tv = view.findViewById(R.id.email);

        name_tv.setText(marker.getTitle());
        address_tv.setText(marker.getSnippet());

        InfoWindowData infoWindowData = (InfoWindowData) marker.getTag();

        //int imageId = context.getResources().getIdentifier(infoWindowData.getImage().toLowerCase(),
        //       "drawable", context.getPackageName());
        //img.setImageResource(imageId);

        website_tv.setText(infoWindowData.getWebsite());
        email_tv.setText(infoWindowData.getEmail());

        return view;
    }
}
