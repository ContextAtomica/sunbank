

import com.pronab.sunbank.adapters.TransactionsAdapter
import com.pronab.sunbank.model.Transaction
import org.junit.Assert.assertTrue
import java.util.*

/**
 * Created by pronabpal on 2/2/18.
 */


class SununitTest {

    @org.junit.Test
    fun testSortedList() {
        val ja1 = ArrayList<Transaction>()//('[{"name":"kay"},{"character":"madman" }]');
        val l1 = Transaction()
        val l2 = Transaction()
        l1.description = "bigmMarch"
        l1.effectiveDate = "20170312"
        l2.description = "smoothride"
        l2.effectiveDate = "20180312"
        ja1.add(l1)
        ja1.add(l2)

        val ja2 = TransactionsAdapter.sortTransactionList(ja1)
        val jk1 = ja2[1] as Transaction
        var ct: String? = null
        try {
            ct = jk1.description
        } catch (e: Exception) {
            e.printStackTrace()
        }

        assertTrue(ct === "smoothride")


    }


}
