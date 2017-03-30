package com.roomiegh.roomie.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.roomiegh.roomie.tabs.Tab1In1;
import com.roomiegh.roomie.tabs.Tab2In1;
import com.roomiegh.roomie.tabs.Tab3In1;
import com.roomiegh.roomie.tabs.Tab4In1;
import com.roomiegh.roomie.tabs.TabBrowse;
import com.roomiegh.roomie.tabs.TabHome;
import com.roomiegh.roomie.tabs.TabProfile;
import com.roomiegh.roomie.util.PushUserUtil;

/**
 * Created by KayO on 23/03/2017.
 * handle viewpaging for room types activity
 */

public class RoomTypeAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    //Build constructor and assign passed values to appropriate values in class
    public RoomTypeAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabs) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabs;

    }


    //return the right fragment for every position in the view pager
    // TODO: 23/03/2017 OPTIMIZE CODE TO REUSE THE SAME FRAGMENT, POSSIBLY PASS SOME INDICATOR IN IF-ELSE BLOCK
    @Override
    public Fragment getItem(int position) {
        if(position == 0) // if the position is 0 we are returning the First tab
        {
            return new Tab4In1();
        }
        else if(position==1)
        {
            return new Tab3In1();
        }
        else if(position==2)
        {
            return new Tab2In1();
        }
        else
        {
            return new Tab1In1();
        }

    }

    //return titles for tabs in tab strip
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    //return the number of tabs for the tab strip
    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}