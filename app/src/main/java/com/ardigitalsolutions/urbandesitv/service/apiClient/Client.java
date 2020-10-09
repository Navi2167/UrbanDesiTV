package com.ardigitalsolutions.urbandesitv.service.apiClient;

import android.content.Context;

import com.ardigitalsolutions.urbandesitv.service.allModels.news.NewsModel;

import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

import static com.ardigitalsolutions.urbandesitv.service.apiClient.Endpoints.SITE;

public class Client {
    private ApiInterface apiInterface;
    private String TAG = this.getClass().getSimpleName();

    public Client(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder oktHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new NetworkConnectionInterceptor(context))
                .addInterceptor(logging);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(SITE)
                .client(oktHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create()).build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public interface ApiInterface {
        @GET(Endpoints.POST)
        Call<List<NewsModel>> news(@QueryMap Map<String, Integer> map);
    }

    public void getNews(ListCallback fetched, Map<String, Integer> map) {
        Call<List<NewsModel>> call = apiInterface.news(map);
        call.enqueue(new Callback<List<NewsModel>>() {
            @Override
            public void onResponse(Call<List<NewsModel>> call, Response<List<NewsModel>> response) {
                if (response.code() == 200)
                    fetched.callback(response.body(), null, response.headers().get("x-wp-totalpages"));
                else
                    fetched.callback(null, response.code() + "", null);
            }

            @Override
            public void onFailure(Call<List<NewsModel>> call, Throwable t) {
                if (t instanceof NoConnectivityException)
                    fetched.callback(null, "NetCon", null);
                else
                    fetched.callback(null, t.getMessage() + "", null);


            }
        });
    }
}
