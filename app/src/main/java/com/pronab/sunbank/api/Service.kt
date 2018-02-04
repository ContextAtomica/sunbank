package com.pronab.sunbank.api

import com.google.gson.JsonArray

import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by delaroy on 7/3/17.
 */

interface Service {

    @GET("/transactions")
    fun readTranArray(): Call<JsonArray>

}
