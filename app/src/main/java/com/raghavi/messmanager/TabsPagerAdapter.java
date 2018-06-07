package com.raghavi.messmanager;

/**
 * Created by Raghavi on 6/2/2018.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();//fragment arraylist
    private final List<String> mFragmentTitleList = new ArrayList<>();//title arraylist

    int mNumOfTabs;

    public TabsPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                BreakFastFragment tab1 = new BreakFastFragment();
                return tab1;
            case 1:
                LunchFragment tab2 = new LunchFragment();
                return tab2;
            case 2:
                SnacksFragment tab3 = new SnacksFragment();
                return tab3;
            case 3:
                DinnerFragment tab4 = new DinnerFragment();
                return tab4;

            default:
                return null;
        }


    }
/*

    //adding fragments and title method
    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }
*/

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
