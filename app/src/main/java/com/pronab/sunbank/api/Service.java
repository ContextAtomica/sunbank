package com.pronab.sunbank.api;

import com.google.gson.JsonArray;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by delaroy on 7/3/17.
 */

public interface Service {

    @GET("/transactions")
    Call<JsonArray> readTranArray();

}
