package com.themescoder.androidstore.adapters;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * ViewPagerAdapter is the adapter class of ViewPager holding Number of Pages in HomePage
 **/

public class ViewPagerSimpleAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragmentList = new ArrayList<>();
    private final List<String> fragmentTitleList = new ArrayList<>();


    public ViewPagerSimpleAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }


    //********** Returns the Fragment associated with a specified Position *********//

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }



    //********** Returns the number of Views available *********//

    @Override
    public int getCount() {
        return fragmentList.size();
    }



    //********** Called by the ViewPager to obtain the Title of specified Page *********//

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }


    //********** Adds the new Fragment to the ViewPager *********//

    public void addFragment(Fragment fragment, String title) {
        // Add the given Fragment to FragmentList
        fragmentList.add(fragment);
        
        // Add the Title of a given Fragment to FragmentTitleList
        fragmentTitleList.add(title);
    }
    
}

