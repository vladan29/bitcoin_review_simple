package com.example.udimitest.models;

import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Data
public class ResponseModel {

    @SerializedName("status")
    String status;
    @SerializedName("data")
    ResponseDataModel responseData;

}
