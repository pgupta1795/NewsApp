package com.main.android.newsapp.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.main.android.newsapp.R;
import com.main.android.newsapp.utils.Category;

public class HomeFragment extends Fragment {
    private View homeView;
    private HomeViewModel homeViewModel;
    private RecyclerView homeRecyclerView;
    private HomeRecyclerAdapter mAdapter;
    SwipeRefreshLayout swiperefreshlayout;

    public HomeFragment() {
        //Empty constructor
    }

    public static HomeFragment newInstance(Category category) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString("CATEGORY", category.name());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        homeView = inflater.inflate(R.layout.fragment_home, container, false);
        startLoading();
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        assert getArguments() != null;
        homeViewModel.init(getArguments().getString("CATEGORY"));
        homeRecyclerView  = homeView.findViewById(R.id.fragment_home);
        initRecyclerView();
        observerChangeData();
        handleRefreshSwipe();
        return homeView;
    }

    private void handleRefreshSwipe() {
        swiperefreshlayout = homeView.findViewById(R.id.swiperefreshhome);
        swiperefreshlayout.setOnRefreshListener(() -> {
            initRecyclerView();
            observerChangeData();
            swiperefreshlayout.setRefreshing(false);
        });
    }

    private void initRecyclerView(){
        mAdapter = new HomeRecyclerAdapter(homeView.getContext(), homeViewModel.getArticles().getValue(),homeRecyclerView);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(homeView.getContext());
        homeRecyclerView.setLayoutManager(linearLayoutManager);
        homeRecyclerView.setAdapter(mAdapter);
    }

    private void observerChangeData() {
        homeViewModel.getArticles().observe(getViewLifecycleOwner(),articles -> {
            mAdapter.notifyDataSetChanged();
            stopLoading();
            if (articles != null) {
                mAdapter.setArticles(articles);
                mAdapter.notifyDataSetChanged();
            } else {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void stopLoading() {
        homeView.findViewById(R.id.progressBar).setVisibility(View.GONE);
    }

    public void startLoading() {
        homeView.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }

}