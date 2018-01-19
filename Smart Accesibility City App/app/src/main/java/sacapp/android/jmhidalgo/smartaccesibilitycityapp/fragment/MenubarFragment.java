package sacapp.android.jmhidalgo.smartaccesibilitycityapp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.R;

/**
 *  @deprecated
 */
public class MenubarFragment extends Fragment {

    private Button buttonUserSlide;
    private Button buttonExplorer;
    private Button buttonSite;
    private Button buttonRatings;

    private MenuListener menuCallback;

    public MenubarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            menuCallback = (MenuListener) context;
        } catch (Exception e){
            Log.i(e.getMessage(), e.getMessage());
            throw new ClassCastException(context.toString() + " should implement menu listener");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menubar, container, false);

        buttonUserSlide = view.findViewById(R.id.buttonUserSlide);
        buttonExplorer = view.findViewById(R.id.buttonExplorer);
        buttonSite = view.findViewById(R.id.buttonSites);
        buttonRatings = view.findViewById(R.id.buttonRatings);

        return view;
    }

    public interface MenuListener{
        void openSlide();
        void openExplorer();
        void openSites();
        void openRatings();

        void setButtonActivated();
    }
}
