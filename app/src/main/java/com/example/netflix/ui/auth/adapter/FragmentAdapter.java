package com.example.netflix.ui.auth.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentStateAdapter {

    List<Fragment> fragments = new ArrayList<>();

    public FragmentAdapter(FragmentActivity fa) {
        super(fa);
    }

    public void add(Fragment fragment){
        fragments.add(fragment);
    }

    public Fragment getFragmentAt(int position){
        return fragments.get(position);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }
}
