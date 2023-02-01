package com.syakirarif.wings.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.syakirarif.wings.R
import com.syakirarif.wings.adapter.ProductAdapter
import com.syakirarif.wings.core.model.Product
import com.syakirarif.wings.database.AppDatabase
import com.syakirarif.wings.databinding.ActivityMainBinding
import com.syakirarif.wings.utils.SpacingItemDecoration
import com.syakirarif.wings.utils.Tools
import dagger.hilt.android.AndroidEntryPoint
import java.io.InputStream
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var appDatabase: AppDatabase

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()

        binding.apply {
            productAdapter = ProductAdapter(arrayListOf(), this@MainActivity)
            rvProducts.adapter = productAdapter
            rvProducts.layoutManager = GridLayoutManager(this@MainActivity, 2)
            rvProducts.addItemDecoration(
                SpacingItemDecoration(
                    2,
                    Tools.dpToPx(this@MainActivity, 8),
                    true
                )
            )
            rvProducts.setHasFixedSize(true)
            rvProducts.isNestedScrollingEnabled = false

        }

        parseJSON()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun readJSONFromAsset(): String? {
        var json: String? = null
        try {
            val inputStream: InputStream = assets.open("dummy_data.json")
            json = inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    private fun parseJSON() {
        val gson = Gson()
        val listPersonType = object : TypeToken<List<Product>>() {}.type
        val products: List<Product> = gson.fromJson(readJSONFromAsset(), listPersonType)
        productAdapter.replaceData(products)
    }

    private fun initToolbar() {
        binding.apply {
            toolbar.setTitleTextColor(resources.getColor(R.color.white))
            setSupportActionBar(toolbar)
            supportActionBar?.title = "Products"
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_cart_setting, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_cart) {
            val intent = Intent(this@MainActivity, CartActivity::class.java)
            startActivity(intent)
        } else {
            appDatabase.clear()
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}