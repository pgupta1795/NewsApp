package com.main.android.newsapp.network;

import androidx.lifecycle.MutableLiveData;

import com.main.android.newsapp.models.Article;
import com.main.android.newsapp.models.ArticleRoot;
import com.main.android.newsapp.network.endpoints.NewsEndpoint;
import com.main.android.newsapp.network.endpoints.SearchNewsEndpoint;
import com.main.android.newsapp.utils.URLUtil;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesRepository {

    private static ArticlesRepository articlesRepository;

    public static ArticlesRepository getInstance(){
        if(articlesRepository ==  null){
            articlesRepository = new ArticlesRepository();
        }
        return articlesRepository;
    }

    public MutableLiveData<List<Article>> getArticles(String category) {
        NewsEndpoint newsEndpoint = NewsAPI.getRetrofit().create(NewsEndpoint.class);
        Call<ArticleRoot> networkCall = newsEndpoint.getHeadlines(category.toLowerCase(),URLUtil.COUNTRY,URLUtil.PAGE_SIZE);
        return enqueue(networkCall);
    }

    public MutableLiveData<List<Article>> getArticlesByQuery(String query) {
        SearchNewsEndpoint newsEndpoint = NewsAPI.getRetrofit().create(SearchNewsEndpoint.class);
        Call<ArticleRoot> networkCall = newsEndpoint.getSearchResults(query,URLUtil.SORT_BY, URLUtil.PAGE_SIZE,URLUtil.LANGUAGE);
        return enqueue(networkCall);
    }

    private static MutableLiveData<List<Article>> enqueue(Call<ArticleRoot> networkCall){
        final MutableLiveData<List<Article>> networkArticleLiveData = new MutableLiveData<>();

        networkCall.enqueue(new Callback<ArticleRoot>() {

            @Override
            public void onResponse(@NotNull Call<ArticleRoot> call, @NotNull Response<ArticleRoot> response) {
                if (response.body() != null) {
                    List<Article> responseArticles = response.body().getArticles();
                    networkArticleLiveData.setValue(responseArticles);
                }
            }

            @Override
            public void onFailure(@NotNull Call<ArticleRoot> call, @NotNull Throwable t) {
                //Empty
            }
        });
        return networkArticleLiveData;
    }
}
