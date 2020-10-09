package com.ardigitalsolutions.urbandesitv.service.apiClient;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public abstract class NetworkInterceptor implements Interceptor {

    public abstract void onInternetUnavailable();

    public abstract boolean isInternetAvailable();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!isInternetAvailable())
            onInternetUnavailable();

        return chain.proceed(request);
    }
}