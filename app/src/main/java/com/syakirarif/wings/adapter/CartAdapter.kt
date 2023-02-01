package com.syakirarif.wings.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.syakirarif.wings.R
import com.syakirarif.wings.core.model.TransactionHeader
import com.syakirarif.wings.databinding.ItemCartBinding
import com.syakirarif.wings.utils.stringToRupiah
import com.syakirarif.wings.views.CartActivity

class CartAdapter(
    private val items: ArrayList<TransactionHeader>,
    private val cartActivity: CartActivity
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    fun replaceData(list: List<TransactionHeader>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mBinding = ItemCartBinding.bind(itemView)

        fun bind(item: TransactionHeader) {

            val qtyItem: Int = item.transaction_detail.quantity

            mBinding.apply {
                imgProduct.load(item.product.product_img)
                tvProductName.text = item.product.product_name
                tvProductPrice.text = item.product.price.toString().stringToRupiah()
                tvProductQty.text = qtyItem.toString()

                imgbtnDecrease.setOnClickListener {
                    if (qtyItem > 0) {
                        cartActivity.changeQty(false, item)
                    }
                }

                imgbtnIncrease.setOnClickListener {
                    cartActivity.changeQty(true, item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }
}