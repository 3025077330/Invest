package com.bw.invest.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fraglist;
    private ArrayList<String> strlist;

    public FragmentAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fraglist, ArrayList<String> strlist) {
        super(fm);
        this.fraglist = fraglist;
        this.strlist = strlist;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fraglist.get(position);
    }

    @Override
    public int getCount() {
        return fraglist.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return strlist.get(position);
    }
}
