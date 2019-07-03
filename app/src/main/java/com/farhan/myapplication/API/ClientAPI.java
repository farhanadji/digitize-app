package com.farhan.myapplication.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientAPI {
    private static final String BASE_URL = "http://mylibraryapi.000webhostapp.com/api/";
    private BookAPI api;
    private static ClientAPI instance = null;

    private ClientAPI(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        api = retrofit.create(BookAPI.class);
    }

    public static ClientAPI getInstance(){
        if(instance == null){
            instance = new ClientAPI();
        }
        return instance;
    }

    public BookAPI getApi(){
        return  api;
    }
}
