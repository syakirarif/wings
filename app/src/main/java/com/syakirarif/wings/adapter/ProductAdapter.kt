package com.syakirarif.wings.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.syakirarif.wings.R
import com.syakirarif.wings.core.model.Product
import com.syakirarif.wings.databinding.ItemProductBinding
import com.syakirarif.wings.utils.stringToRupiah
import com.syakirarif.wings.views.DetailActivity

class ProductAdapter(
    private val items: ArrayList<Product>,
    private val context: Context
) : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    fun replaceData(list: List<Product>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mBinding = ItemProductBinding.bind(itemView)

        fun bind(item: Product) {
            mBinding.apply {
                imgProduct.load(item.product_img)
                tvProductName.text = item.product_name
                tvProductPrice.text = item.price.toString().stringToRupiah()

                lytRipple.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra("EXTRA_PRODUCT", item)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}