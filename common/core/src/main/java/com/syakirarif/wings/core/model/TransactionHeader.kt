package com.syakirarif.wings.core.model

data class TransactionHeader(
    val document_code: String,
    val document_number: String,
    val user: String,
    val total: Int,
    val date: String,
    val product: Product,
    val transaction_detail: TransactionDetail
)