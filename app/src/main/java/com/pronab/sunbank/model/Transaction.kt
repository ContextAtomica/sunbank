package com.pronab.sunbank.model

/**
 * Created by delaroy on 7/3/17.
 */

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Transaction {
    @SerializedName("id")
    @Expose
    var id: Int = 0

    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("amount")
    @Expose
    var amount: Float? = null
    @SerializedName("effectiveDate")
    @Expose
    var effectiveDate: String? = null
}
