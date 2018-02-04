package com.pronab.sunbank.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by delaroy on 7/3/17.
 */

object Client {

    private var service: Service? = null

    val client: Service
        get() {
            if (service == null) {
                val gson = GsonBuilder()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                        .create()

                val retrofit = Retrofit.Builder()
                        .baseUrl("https://private-ddc1b2-transactions14.apiary-mock.com")
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build()

                service = retrofit.create(Service::class.java)
            }
            return service!!
        }

}