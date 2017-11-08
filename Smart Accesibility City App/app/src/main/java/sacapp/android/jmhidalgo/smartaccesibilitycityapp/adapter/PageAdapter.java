package sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.fragment.MapFragment;

public class PageAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;


    public PageAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }


    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 1:
            case 2:
            case 3:
            case 0:
                return new MapFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}