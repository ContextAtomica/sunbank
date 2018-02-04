package com.pronab.sunbank.adapters

import android.content.Context
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pronab.sunbank.R
import com.pronab.sunbank.model.Transaction
import java.util.*

/**
 * Created by Pronab
 */

class TransactionsAdapter(private val mContext: Context, transactionList: List<Transaction>, total: Float) : RecyclerView.Adapter<TransactionsAdapter.MyViewHolder>() {
    private var transactionList: List<Transaction> = ArrayList()
    private val totalbal: Float = 0.toFloat()

    init {

        if (transactionList.size > 0) {
            this.transactionList = sortTransactionList(transactionList)

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.trans_card, parent, false)

        return MyViewHolder(view)
    }

    public inline fun String.toDouble(): Double = java.lang.Double.parseDouble(this)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val tran = transactionList[position]
        var zr: Double = (tran.amount!!).toDouble()
        holder.transaction.text = tran.description + " :" + tran.amount
       if (  zr  <0.0 )
               {
                   holder.transaction.setTextColor(Color.RED)

               } else {
            holder.transaction.setTextColor(Color.BLUE)
        }

    }



    override fun getItemCount(): Int {
        return transactionList.size

    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var transaction: TextView
        internal var image: ImageView? = null

        init {
            transaction = view.findViewById(R.id.content) as TextView
            //image = (ImageView) view.findViewById(R.id.thumbnail);

            view.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {

                    val alert = AlertDialog.Builder(mContext)
                    alert.setTitle("Transaction Detail")
                    val tr = transactionList[pos]
                    alert.setMessage("Full Transaction: \n" + "Description:" + tr.description + "\n"
                            + "Effective Date:" + tr.effectiveDate + "\n" +
                            "Amount:" + tr.amount

                    )

                    alert.show()
                }
            }
        }
    }

    companion object {

        fun sortTransactionList(transactionList: List<Transaction>): List<Transaction> {
            val tm = TreeMap<String, Transaction>()

            for (tr in transactionList) tm[tr.effectiveDate as String] = tr
            val tk = tm.keys
            val mk = tk.iterator()
            val tranList = ArrayList<Transaction>()
            while (mk.hasNext()) {
                tranList.add(tm[mk.next()] as Transaction)

            }
            return tranList
        }
    }
}

