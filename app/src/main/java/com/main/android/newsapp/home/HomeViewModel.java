package com.main.android.newsapp.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.main.android.newsapp.models.Article;
import com.main.android.newsapp.network.ArticlesRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<List<Article>> articles;

    public MutableLiveData<List<Article>> getArticles() {
        return articles;
    }

    public void init(String category){
        if(articles != null){
            return;
        }
        ArticlesRepository articlesRepository = ArticlesRepository.getInstance();
        articles = articlesRepository.getArticles(category);
    }
}