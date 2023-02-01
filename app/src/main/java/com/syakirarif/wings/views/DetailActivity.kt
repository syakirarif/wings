package com.syakirarif.wings.views

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.syakirarif.wings.core.model.Product
import com.syakirarif.wings.core.model.TransactionDetail
import com.syakirarif.wings.core.model.TransactionHeader
import com.syakirarif.wings.database.AppDatabase
import com.syakirarif.wings.databinding.ActivityDetailBinding
import com.syakirarif.wings.utils.getCurrentDateTime
import com.syakirarif.wings.utils.stringToRupiah
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var appDatabase: AppDatabase

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private var listCart: ArrayList<TransactionHeader> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val product = intent.getSerializableExtra("EXTRA_PRODUCT") as? Product

        if (product != null)
            binding.apply {
                imgProduct.load(product.product_img)
                tvProductName.text = product.product_name
                tvProductDimension.text = String.format("Dimension: ${product.dimension}")
                tvPrice.text = product.price.toString().stringToRupiah()
                tvDescription.text = product.product_description

                fabAdd.setOnClickListener {
                    val trxDetail = TransactionDetail(
                        price = product.price,
                        quantity = 1,
                        subtotal = 0,
                        currency = product.currency
                    )
                    val trxHeader = TransactionHeader(
                        document_code = "",
                        document_number = "",
                        user = appDatabase.getUserData().username,
                        total = 0,
                        date = getCurrentDateTime(),
                        product = product,
                        transaction_detail = trxDetail
                    )
//                    appDatabase.setTransactionHeader(trxHeader)

                    processToCart(trxHeader)

                }

                extendedFab.setOnClickListener {
                    val intent = Intent(this@DetailActivity, CartActivity::class.java)
                    startActivity(intent)
                }
            }
    }

    private fun processToCart(trxHeader: TransactionHeader) {

        if (appDatabase.containsTransactionHeader()) {
            listCart = ArrayList(appDatabase.getTransactionHeader())

            if (listCart.isNotEmpty()) {

                var samePos: Int? = null

                for (i in 0 until listCart.size) {
                    if (listCart[i].product.product_code == trxHeader.product.product_code) {
                        samePos = i
                    }
                }

                if (samePos != null) {
                    Toast.makeText(
                        this@DetailActivity,
                        "Barang sudah ada di Cart",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    insertData(trxHeader)
                }

            } else {
                insertData(trxHeader)
            }

        } else {
            insertData(trxHeader)
        }
    }

    private fun insertData(trxHeader: TransactionHeader) {
        listCart.add(trxHeader)
        appDatabase.setTransactionHeader(listCart)
        Toast.makeText(
            this@DetailActivity,
            "Barang telah dimasukkan ke Cart",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}