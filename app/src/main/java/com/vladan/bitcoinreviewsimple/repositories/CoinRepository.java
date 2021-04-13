package com.vladan.bitcoinreviewsimple.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.vladan.bitcoinreviewsimple.api.ApiService;
import com.vladan.bitcoinreviewsimple.models.CoinModel;
import com.vladan.bitcoinreviewsimple.models.ResponseModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoinRepository {
    private static final String TAG = "CoinRepository";
    private final ApiService apiService;

    @Inject
    CoinRepository(
            ApiService apiService
    ) {
        this.apiService = apiService;
    }

    public MutableLiveData<ArrayList<CoinModel>> fetchCoins(String sort) {
        MutableLiveData<ArrayList<CoinModel>> responseData = new MutableLiveData<>();
        apiService.getCoins(sort).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ResponseModel> call, @NotNull Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    ResponseModel model = response.body();
                    assert model != null;
                    responseData.setValue(model.getResponseData().getCoins());
                    Log.d(TAG, "onResponse");
                }

            }

            @Override
            public void onFailure(@NotNull Call<ResponseModel> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure");
            }
        });
        return responseData;
    }
}
