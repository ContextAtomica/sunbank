package com.pronab.sunbank.adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pronab.sunbank.R;
import com.pronab.sunbank.model.Transaction;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Pronab
 */

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Transaction> transactionList = new ArrayList<>();
    private  float totalbal ;

    public  static   ArrayList  sortTransactionList(List<Transaction> transactionList) {
        TreeMap tm = new TreeMap<String, Transaction>();

        for (Transaction tr : transactionList) {
            tm.put(tr.getEffectiveDate(), tr);

        }
        Set tk = tm.keySet();
        Iterator mk = (tk.iterator());
         ArrayList  tranList = new ArrayList();
        while (mk.hasNext()) {
            tranList.add((Transaction) tm.get(mk.next()));

        }
        return  tranList ;
    }
    public TransactionsAdapter(Context mContext, List<Transaction> transactionList, float total ) {

        if (transactionList.size() > 0) {
            this.transactionList =  sortTransactionList(transactionList);

        }
        this.mContext = mContext;

    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trans_card, parent, false);
      //  View viewt = LayoutInflater.from(parent.getContext())
          //      .inflate(R.layout.activity_main, parent, false);
        return new MyViewHolder(view);
    }



    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Transaction tran = transactionList.get(position);
        holder.transaction.setText(tran.getDescription() + " :" + tran.getAmount());

    }


    @Override
    public int getItemCount(){
        return transactionList.size();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView transaction;
        ImageView image;

        public MyViewHolder(View view){

            super(view);
            transaction  = (TextView) view.findViewById(R.id.content);
            //image = (ImageView) view.findViewById(R.id.thumbnail);

            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){

                        AlertDialog.Builder alert = new AlertDialog.Builder (mContext);
                        alert.setTitle( "Transaction Detail");
                        Transaction tr  = (Transaction) transactionList.get(pos);
                        alert.setMessage( "Full Transaction: \n" +"Description:" + tr.getDescription() + "\n"
                        +"Effective Date:" +  tr.getEffectiveDate() + "\n" +
                           "Amount:" +              tr.getAmount()

                        );

                        alert.show() ;
                    }
                }
            });
        }
    }
}
