package com.main.android.newsapp.network;

import com.main.android.newsapp.utils.URLUtil;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsAPI {

    private static Retrofit retrofit;

    public static Retrofit getRetrofit() {
        if(retrofit == null){
            OkHttpClient client = new OkHttpClient.Builder().
                    addInterceptor(new HttpLoggingInterceptor()
                            .setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build();

            retrofit = new Retrofit.Builder().baseUrl(URLUtil.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
