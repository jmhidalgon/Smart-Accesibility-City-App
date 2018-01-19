package sacapp.android.jmhidalgo.smartaccesibilitycityapp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import sacapp.android.jmhidalgo.smartaccesibilitycityapp.fragment.HistoryFragment;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.fragment.MapFragment;
import sacapp.android.jmhidalgo.smartaccesibilitycityapp.fragment.UserConfigFragment;

/** Page adapter for main activity
 *
 */
public class PageAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    /** Constructor
     *
     * @param fm fragment manager
     * @param numberOfTabs number of tabs to show
     */
    public PageAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    /** Getter fragment
     *
     * @param position index of the requested fragment
     * @return Fragment selected
     */
    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 2:
                return new UserConfigFragment();
            case 0:
                return new MapFragment();
            case 1:
                return new HistoryFragment();
            default:
                return null;
        }
    }

    /** Getter number of fragment
     *
     * @return number of fragments
     */
    @Override
    public int getCount() {
        return numberOfTabs;
    }
}