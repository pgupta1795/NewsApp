package com.main.android.newsapp.network.endpoints;


import com.main.android.newsapp.models.ArticleRoot;
import com.main.android.newsapp.utils.URLUtil;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * An Api interface to send network requests
 * Includes Category enum that provides category names for requests
 */
public interface NewsEndpoint {

    @Headers("X-Api-Key:" + URLUtil.API_KEY)
    @GET(URLUtil.TOP_HEADLINES)
    Call<ArticleRoot> getHeadlines(
            @Query("category") String category,
            @Query("country") String country,
            @Query("pageSize") int pageSize
    );
}