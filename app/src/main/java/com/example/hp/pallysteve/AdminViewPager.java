package com.example.hp.pallysteve;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class AdminViewPager extends FragmentStatePagerAdapter {

    int noOfTabs;

    public AdminViewPager(FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }


    @Override
    public Fragment getItem(int i) {
        switch(i){
            case 0:
                AdminTab1 adminTab1 = new AdminTab1();
                return adminTab1;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }

}
