package com.vladan.bitcoinreviewsimple.api;

import com.vladan.bitcoinreviewsimple.models.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("coins")
    Call<ResponseModel> getCoins(@Query("orderBy") String orderBy);
}
