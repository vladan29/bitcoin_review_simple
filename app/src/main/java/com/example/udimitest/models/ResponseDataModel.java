package com.example.udimitest.models;

import java.util.ArrayList;

import lombok.Data;

@Data
public class ResponseDataModel {
    ResponseStatsModel data;
    ArrayList<CoinModel> coins;
}
