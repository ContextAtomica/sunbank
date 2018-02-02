package com.pronab.sunbank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.pronab.sunbank.adapters.TransactionsAdapter;
import com.pronab.sunbank.api.Client;
import com.pronab.sunbank.api.Service;
import com.pronab.sunbank.model.Transaction;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private static TextView texttotal;
    private static TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       //  getSupportLoaderManager().initLoader(0, null, mLoaderCallbacks);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        texttotal = (TextView) findViewById(R.id.bal);
        result    =(TextView) findViewById(R.id.result);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        if (Sunbank.trans == null) {
        loadJSON();  }
         else {

            showGooddata();
        }

    }


    private void loadJSON() {

        Service serviceAPI = Client.getClient();
        Call<JsonArray> loadTeamCall = serviceAPI.readTranArray();

        loadTeamCall.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                try {

                    String transString = response.body().toString();

                    Type listType = new TypeToken<List<Transaction>>() {
                    }.getType();
                   if ( getTransListFromJson(transString, listType) != null ) {
                    Sunbank.trans =  getTransListFromJson(transString, listType);
                     showGooddata();
                   }
                    else
                    {
                     showErrorData();
                    }


                } catch (Exception e) {
                    Log.d("onResponse", "There is an error");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

                Log.d("onFailure", t.toString());

            }


        });
    }
   public void showGooddata ()  {  recyclerView.setItemAnimator(new DefaultItemAnimator());
       recyclerView.setAdapter(new TransactionsAdapter(this, Sunbank.trans, Sunbank.total));
       texttotal.setText(String.format("%.2f", Sunbank.total));
       result.setText("Transactions");


   }
   public void showErrorData() {

       texttotal.setText("0"   );
       result.setText("Sorry,Network issue, Please restart the app");


   }
    public static   List getTransListFromJson(String jsonString, Type type) {
        if (!isValid(jsonString)) {
            return null;
        }
        return new Gson().fromJson(jsonString, type);
    }

    public static boolean isValid(String json) {

            float amnt = 0;
            try {
                JsonElement je = new JsonParser().parse(json);

                JsonArray ja = je.getAsJsonArray();
                for (JsonElement jr : ja) {  //jr =  ja.get(0);
                    JsonObject jre = jr.getAsJsonObject();
                    amnt = amnt + (float) (jre.get("amount")).getAsFloat();
                  //  Log.d("caltotal", String.valueOf(amnt));
                }

                Sunbank.total = amnt;

                return true;
            } catch (JsonSyntaxException jse) {
                return false;
            }
        }


    @Override
    protected void onStart() {
        super.onStart();

        loadJSON();
    }


}