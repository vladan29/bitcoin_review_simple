package com.vladan.bitcoinreviewsimple.models;

import com.google.gson.annotations.SerializedName;

public class ResponseStatsModel {

    @SerializedName("total")
    Long total;
    @SerializedName("totalMarkets")
    Long totalMarkets;
    @SerializedName("totalExchanges")
    Long totalExchanges;
    @SerializedName("totalMarketCap")
    String totalMarketCap;
    @SerializedName("total24hVolume")
    String total24hVolume;

}
