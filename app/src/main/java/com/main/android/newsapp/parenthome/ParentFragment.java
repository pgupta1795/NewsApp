package com.main.android.newsapp.parenthome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.main.android.newsapp.R;
import com.main.android.newsapp.utils.Category;

public class ParentFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private final String[] categories =  Category.getAllCategories();
    private final int[] categoryIcons =  Category.getAllCategoryIcons();

    public ParentFragment() {
        // Required empty public constructor
    }

    public static ParentFragment newInstance() {
        return new ParentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_parent, container, false);
        tabLayout = view.findViewById(R.id.tablayout_headlines);
        viewPager = view.findViewById(R.id.viewpager_headlines);
        FragmentStateAdapter pagerAdapter = new ParentViewAdapter(this, categories);
        viewPager.setAdapter(pagerAdapter);
        setUpTabs();
        return view;
    }

    //Attach tabLayout to view-adapter class, so fragments are attached as tabs inside it
    private void setUpTabs() {
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> {
                    int id ;
                    switch (position) {
                        case 0 :
                            id = R.id.GENERAL;
                            break;
                        case 1 :
                            id = R.id.BUSINESS;
                            break;
                        case 2 :
                            id = R.id.ENTERTAINMENT;
                            break;
                        case 3 :
                            id = R.id.HEALTH;
                            break;
                        case 4 :
                            id = R.id.SCIENCE;
                            break;
                        case 5 :
                            id = R.id.SPORTS;
                            break;
                        default:
                            id = R.id.TECHNOLOGY;
                            break;
                    }
                    tab.setId(id);
                    tab.setText(categories[position]);
                    tab.setIcon(categoryIcons[position]);
                }
        ).attach();
    }

}