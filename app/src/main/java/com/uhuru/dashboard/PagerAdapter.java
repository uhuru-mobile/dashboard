package com.uhuru.dashboard;

/**
 * Created by Thibaut on 18/01/16.
 */
import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // System notifications fragment activity
                return new SystemFragment();
            case 1:
                // Application notifications fragment activity
                return new AppsFragment();
            case 2:
                // Stats fragment activity
                return new StatsFragment();
            case 3:
                // Settings fragment activity
                return new SettingsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}