package com.doordash.doordashlite.network;

import android.support.annotation.NonNull;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

public class RetrofitClient {
    private static final String BASE_URL = "https://api.doordash.com/v2/";
    private static final int CONNECTION_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 30;

    private static DoorDashApi doorDashApi;

    public static DoorDashApi getDoorDashApi() {
        if (doorDashApi == null) {
            doorDashApi = createRestApi(BASE_URL);
        }
        return doorDashApi;
    }

    private static DoorDashApi createRestApi(@NonNull String baseUrl) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        OkHttpClient httpClient = builder
                .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .connectionPool(new ConnectionPool(5, 8, TimeUnit.SECONDS))
                .build();

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(DoorDashApi.class);
    }
}
