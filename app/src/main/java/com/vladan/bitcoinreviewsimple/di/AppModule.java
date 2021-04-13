package com.vladan.bitcoinreviewsimple.di;

import com.vladan.bitcoinreviewsimple.api.ApiService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@InstallIn(SingletonComponent.class)
@Module
public final class AppModule {
    @Singleton
    @Provides
    static OkHttpClient provideOkHttpClient(){
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(60, TimeUnit.SECONDS);
        client.connectTimeout(20, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);
        client.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader("x-access-token", "coinrankingd616c58fe7486f0871031f55e2e3f938c434c239da0f567a")
                        .build();
                return chain.proceed(request);
            }
        });

   return client.build();
    }

    @Provides
    Gson provideGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        gsonBuilder.setLenient();
        return gsonBuilder.create();
    }


    @Singleton
    @Provides
    static ApiService provideApiService(OkHttpClient client,Gson gson){
        Retrofit.Builder builder = new Retrofit.Builder();
        return builder
                .baseUrl("https://api.coinranking.com/v2/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
                .create(ApiService.class);
    }

}
