package com.pronab.sunbank

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.pronab.sunbank.adapters.TransactionsAdapter
import com.pronab.sunbank.api.Client
import com.pronab.sunbank.model.Transaction
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {
    /* the activity stores fetched  data and the calculation result  in the Sunbank static instance
      and thus avoid reloading or recalculating data when the activity is restarted by OS.
    */
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //  getSupportLoaderManager().initLoader(0, null, mLoaderCallbacks);
        recyclerView = findViewById(R.id.recycler_view) as RecyclerView
        texttotal = findViewById(R.id.bal) as TextView
        result = findViewById(R.id.result) as TextView

        recyclerView!!.layoutManager = LinearLayoutManager(this)
        if (Sunbank.trans == null) {
            loadJSON()
        } else {

            showGooddata()
        }

    }


    private fun loadJSON() {

        val serviceAPI = Client.client
        val loadTeamCall = serviceAPI.readTranArray()

        loadTeamCall.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {

                try {

                    val transString = response.body()!!.toString()

                    val listType = object : TypeToken<List<Transaction>>() {

                    }.type
                    if (getTransListFromJson(transString, listType) != null) {
                        Sunbank.trans = getTransListFromJson(transString, listType) as List<Transaction>?
                        showGooddata()
                    } else {
                        showErrorData()
                    }


                } catch (e: Exception) {
                    Log.d("onResponse", "There is an error")
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {

                Log.d("onFailure", t.toString())

            }


        })
    }

    fun showGooddata() {
        recyclerView!!.itemAnimator = DefaultItemAnimator()
        recyclerView!!.adapter = TransactionsAdapter(this, Sunbank.trans!!, Sunbank.total)
        texttotal!!.text = String.format("%.2f", Sunbank.total)
        var zr: Double = (Sunbank.total!!).toDouble()
        if (  zr  <0.0 )
        {
            texttotal!!.setTextColor(Color.RED)

        } else {
            texttotal!!.setTextColor(Color.BLUE)
        }
        result!!.text = "Transactions List"


    }

    fun showErrorData() {

        texttotal!!.text = "0"
        result!!.text = "Sorry,Network issue, Please restart the app"


    }


    override fun onStart() {
        super.onStart()
        if (Sunbank.trans == null) {
            loadJSON()
        }
    }

    companion object {

        private var texttotal: TextView? = null
        private var result: TextView? = null
        fun getTransListFromJson(jsonString: String, type: Type): List<*>? {
            return if (!isValid(jsonString)) {
                null
            } else Gson().fromJson<List<*>>(jsonString, type)
        }

        fun isValid(json: String): Boolean {

            var amnt = 0f
            try {
                val je = JsonParser().parse(json)

                val ja = je.asJsonArray
                for (jr in ja) {  //jr =  ja.get(0);
                    val jre = jr.asJsonObject
                    amnt = amnt + jre.get("amount").asFloat
                      Log.d("caltotal", jre.get("amount").asString);
                }

                Sunbank.total = amnt

                return true
            } catch (jse: JsonSyntaxException) {
                return false
            }

        }
    }


}


