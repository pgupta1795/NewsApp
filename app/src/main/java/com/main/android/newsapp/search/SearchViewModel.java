package com.main.android.newsapp.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.main.android.newsapp.models.Article;
import com.main.android.newsapp.network.ArticlesRepository;

import java.util.List;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<Article>> articles;

    public MutableLiveData<List<Article>> getArticles() {
        return articles;
    }

    public void setArticles(MutableLiveData<List<Article>> articles) {
        this.articles = articles;
    }

    public void init(String query){
        ArticlesRepository articlesRepository = ArticlesRepository.getInstance();
        articles = articlesRepository.getArticlesByQuery(query);
    }
}