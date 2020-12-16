package com.dbuzin.hashcalculator.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.dbuzin.hashcalculator.fragments.HashFragment;
import com.dbuzin.hashcalculator.fragments.PasswordFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    private int tabsNums;

    public PagerAdapter(FragmentManager fm, int tabsNums){
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabsNums = tabsNums;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HashFragment();
            case 1:
                return new PasswordFragment();
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return tabsNums;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Hash Calculator";
            case 1:
                return "Password Generator";
            default:
                return  null;
        }
    }
}
