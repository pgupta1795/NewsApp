package com.main.android.newsapp.utils;

import java.util.Locale;

public interface URLUtil {

    String API_KEY = "2b1ad75adda0442caca70b8a4d8ed407";

    String BASE_URL = "https://newsapi.org/";

    String TOP_HEADLINES = "/v2/top-headlines";

    String SEARCH_NEWS = "/v2/everything";

    int PAGE_SIZE = 100;

    String COUNTRY = "in";

    String SORT_BY = "publishedAt";

    String LANGUAGE = "en";
}
