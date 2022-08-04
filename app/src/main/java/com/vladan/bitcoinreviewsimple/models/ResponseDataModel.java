package com.vladan.bitcoinreviewsimple.models;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ResponseDataModel {
    public ResponseDataModel() {
    }

    ResponseStatsModel data;
    ArrayList<CoinModel> coins;

    public ArrayList<CoinModel> getCoins() {
        return coins;
    }
}
