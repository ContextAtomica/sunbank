package com.pronab.sunbank

import android.app.Application

import com.pronab.sunbank.model.Transaction

/**
 * Created by pronabpal on 2/2/18.
 */

class Sunbank : Application() {
    companion object {
      // these fields stores the fetched data as well as the calculation total
      // accross activity life cycle.  Data is reloaded only when trans = null
        var trans: List<Transaction>? = null
        var total = -99999f
    }


}
