package com.vladan.bitcoinreviewsimple.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ResponseModel {
    public ResponseModel() {
    }

    @SerializedName("status")
    String status;
    @SerializedName("data")
    ResponseDataModel responseData;

    public ResponseDataModel getResponseData() {
        return responseData;
    }
}
