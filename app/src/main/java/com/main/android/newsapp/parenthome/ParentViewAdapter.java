package com.main.android.newsapp.parenthome;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.main.android.newsapp.home.HomeFragment;
import com.main.android.newsapp.utils.Category;

public class ParentViewAdapter extends FragmentStateAdapter {

    private final HomeFragment[] homeFragments;

    public ParentViewAdapter(Fragment fragment,String[] categories) {
        super(fragment);
        homeFragments = new HomeFragment[categories.length];
        for(int i =0;i<categories.length;i++){
            homeFragments[i] = HomeFragment.newInstance(Category.valueOf(categories[i]));
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return homeFragments[position];
    }

    @Override
    public int getItemCount() {
        return homeFragments.length;
    }
}
