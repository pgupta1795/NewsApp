package com.main.android.newsapp.network.endpoints;

import com.main.android.newsapp.models.ArticleRoot;
import com.main.android.newsapp.utils.URLUtil;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * An Api interface to search news using network request
 */
public interface SearchNewsEndpoint {

    @Headers("X-Api-Key:" + URLUtil.API_KEY)
    @GET(URLUtil.SEARCH_NEWS)
    Call<ArticleRoot> getSearchResults(
            @Query("q") String query,
            @Query("sortBy") String sortBy,
            @Query("pageSize") int pageSize,
            @Query("language") String language);
}