package com.vladan.bitcoinreviewsimple.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.vladan.bitcoinreviewsimple.models.CoinModel;
import com.vladan.bitcoinreviewsimple.repositories.CoinRepository;

import java.util.ArrayList;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CoinViewModel extends ViewModel {

    private final CoinRepository repository;
    private final SavedStateHandle savedStateHandle;
    public MutableLiveData<ArrayList<CoinModel>> coins = new MutableLiveData<>();

    @Inject
    CoinViewModel(
            SavedStateHandle savedStateHandle,
            CoinRepository repository) {
        this.savedStateHandle = savedStateHandle;
        this.repository = repository;
        //this.coins = repository.fetchCoins("marketCap");
    }

    public void init() {
        coins = repository.fetchCoins("marketCap");
    }

    public void sortByPrice() {
        coins = repository.fetchCoins("price");

    }

    public void sortBy24hChange() {
        coins = repository.fetchCoins("24hVolume");
    }
}
