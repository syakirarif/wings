package com.syakirarif.wings.views

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.syakirarif.wings.R
import com.syakirarif.wings.adapter.CartAdapter
import com.syakirarif.wings.core.model.TransactionDetail
import com.syakirarif.wings.core.model.TransactionHeader
import com.syakirarif.wings.database.AppDatabase
import com.syakirarif.wings.databinding.ActivityCartBinding
import com.syakirarif.wings.utils.Tools
import com.syakirarif.wings.utils.getCurrentDateTime
import com.syakirarif.wings.utils.stringToRupiah
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CartActivity : AppCompatActivity() {

    @Inject
    lateinit var appDatabase: AppDatabase

    private var _binding: ActivityCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var cartAdapter: CartAdapter

    private var totalAmount = 0

    private var listCart: ArrayList<TransactionHeader> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()

        binding.apply {
            initRecyclerView()

            tvTotal.text = totalAmount.toString().stringToRupiah()

            btnCheckout.setOnClickListener {
                Toast.makeText(this@CartActivity, "Checkout", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            cartAdapter = CartAdapter(arrayListOf(), this@CartActivity)
            rvProducts.adapter = cartAdapter
            rvProducts.layoutManager = LinearLayoutManager(this@CartActivity)
            rvProducts.setHasFixedSize(true)
            rvProducts.isNestedScrollingEnabled = false
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        getCartData()
    }

    fun changeQty(isIncrease: Boolean, trxHeader: TransactionHeader) {
        if (appDatabase.containsTransactionHeader()) {
            listCart = ArrayList(appDatabase.getTransactionHeader())

            if (listCart.isNotEmpty()) {

                var samePos: Int? = null
                var qtyBefore = 0
                var qtyAfter = 0

                for (i in 0 until listCart.size) {
                    if (listCart[i].product.product_code == trxHeader.product.product_code) {
                        samePos = i
                        qtyBefore = listCart[i].transaction_detail.quantity
                    }
                }

                if (samePos != null && qtyBefore > 0) {
                    qtyAfter = if (isIncrease) ++qtyBefore else --qtyBefore
                    val trxDetailNew = TransactionDetail(
                        price = trxHeader.transaction_detail.price,
                        quantity = qtyAfter,
                        subtotal = trxHeader.transaction_detail.subtotal,
                        currency = trxHeader.transaction_detail.currency
                    )
                    val trxHeaderNew = TransactionHeader(
                        document_code = trxHeader.document_code,
                        document_number = trxHeader.document_number,
                        user = trxHeader.user,
                        total = totalAmount,
                        date = getCurrentDateTime(),
                        product = trxHeader.product,
                        transaction_detail = trxDetailNew
                    )

                    if (qtyAfter == 0) {
                        listCart.removeAt(samePos)
                    } else {
                        listCart.removeAt(samePos)
                        listCart.add(trxHeaderNew)
                    }

                    appDatabase.setTransactionHeader(listCart)
                }

            }
        }

        initRecyclerView()
        getCartData()
    }

    private fun getCartData() {

        if (appDatabase.containsTransactionHeader()) {
            listCart = ArrayList(appDatabase.getTransactionHeader())

            if (listCart.isNotEmpty()) {
                cartAdapter.replaceData(listCart)
            }

        }

        checkDataRV()
        updateTotalAmount()
    }

    private fun checkDataRV() {
        binding.lytNoData.visibility = if (listCart.isEmpty()) View.VISIBLE else View.GONE
        binding.rvProducts.visibility = if (listCart.isEmpty()) View.GONE else View.VISIBLE
    }

    private fun updateTotalAmount() {
        totalAmount = 0

        if (appDatabase.containsTransactionHeader()) {
            listCart = ArrayList(appDatabase.getTransactionHeader())

            if (listCart.isNotEmpty()) {

                for (i in 0 until listCart.size) {
                    val price1 = listCart[i].transaction_detail.quantity * listCart[i].product.price
                    totalAmount += price1
                }
            }
        }

        binding.tvTotal.text = totalAmount.toString().stringToRupiah()

    }

    private fun initToolbar() {
        binding.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.title = null
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            Tools.setSystemBarColor(this@CartActivity, R.color.grey_10)
            Tools.setSystemBarLight(this@CartActivity)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_setting, menu)
        Tools.changeMenuIconColor(menu, resources.getColor(R.color.grey_60))
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}