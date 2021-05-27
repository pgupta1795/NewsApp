package com.main.android.newsapp.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.main.android.newsapp.R;
import com.main.android.newsapp.home.HomeRecyclerAdapter;

public class SearchFragment extends Fragment {

    private SearchViewModel searchViewModel;
    private View searchFragmentView;
    private SearchView searchView;
    TextView emptyView;
    private RecyclerView searchRecyclerView;
    private String lastQuery = "";
    private HomeRecyclerAdapter mAdapter;
    SwipeRefreshLayout swiperefreshlayout;

    public SearchFragment() {
        //necessary empty
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        searchFragmentView = inflater.inflate(R.layout.fragment_search, container, false);
        emptyView = searchFragmentView.findViewById(R.id.empty_view);
        searchView = searchFragmentView.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchRecyclerView = searchFragmentView.findViewById(R.id.fragment_search);
        handleRefreshSwipe();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                lastQuery = query;
                startLoading();
                emptyView.setVisibility(View.GONE);

                //background request
                searchViewModel.init(lastQuery);
                initRecyclerView();
                observerChangeData();

                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                lastQuery = "";
                stopLoading();
                searchRecyclerView.setVisibility(View.INVISIBLE);
                emptyView.setText(R.string.no_news);
                emptyView.setVisibility(View.VISIBLE);
                return false;
            }
        });
        return searchFragmentView;
    }

    private void handleRefreshSwipe() {
        swiperefreshlayout = searchFragmentView.findViewById(R.id.swiperefresh);
        swiperefreshlayout.setOnRefreshListener(() -> {
            emptyView.setVisibility(View.GONE);
            //background request
            searchViewModel.init(lastQuery);
            initRecyclerView();
            observerChangeData();
            searchView.clearFocus();
            swiperefreshlayout.setRefreshing(false);
        });
    }


    private void stopLoading() {
        searchFragmentView.findViewById(R.id.progress_loader).setVisibility(View.GONE);
    }

    public void startLoading() {
        searchFragmentView.findViewById(R.id.progress_loader).setVisibility(View.VISIBLE);
    }

    private void initRecyclerView(){
        mAdapter = new HomeRecyclerAdapter(searchFragmentView.getContext(), searchViewModel.getArticles().getValue(),searchRecyclerView);
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(searchFragmentView.getContext());
        searchRecyclerView.setLayoutManager(linearLayoutManager);
        searchRecyclerView.setAdapter(mAdapter);
        swiperefreshlayout.setRefreshing(false);
    }

    private void observerChangeData() {
        searchViewModel.getArticles().observe(getViewLifecycleOwner(),articles -> {
            mAdapter.notifyDataSetChanged();
            stopLoading();
            searchRecyclerView.setVisibility(View.VISIBLE);
            if (articles != null) {
                mAdapter.setArticles(articles);
            }
            mAdapter.notifyDataSetChanged();
        });
    }

}