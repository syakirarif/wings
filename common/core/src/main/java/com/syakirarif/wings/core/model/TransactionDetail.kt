package com.syakirarif.wings.core.model

data class TransactionDetail(
    val price: Int,
    val quantity: Int,
    val subtotal: Int,
    val currency: String
)