package com.syakirarif.wings.core.model

data class Product(
    val product_code: String,
    val product_name: String,
    val product_img: String,
    val product_description: String,
    val price: Int,
    val currency: String,
    val discount: Int,
    val dimension: String,
    val unit: String
) : java.io.Serializable