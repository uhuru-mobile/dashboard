package com.uhuru.dashboard;

/**
 * Created by dorian on 21/04/15.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
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
                return new StatsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 4;
    }

}



