package com.syakirarif.wings.utils

import android.app.Activity
import android.content.Context
import android.util.Base64
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.asDateTime(): String {
    return try {
        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("id", "ID"))
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val convertedDate = sdf.parse(this)
        convertedDate?.let { formatter.format(convertedDate) } ?: this
    } catch (e: Exception) {
        this
    }
}

fun String.asDateTimeLong(): String {
    return try {
        val formatter = SimpleDateFormat("d MMMM yyyy HH:mm", Locale("id", "ID"))
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val convertedDate = sdf.parse(this)
        convertedDate?.let { formatter.format(convertedDate) } ?: this
    } catch (e: Exception) {
        this
    }
}

fun getCurrentDateTime(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return sdf.format(Date())
}

fun String.asOnlyDate(): String {
    return try {
        val formatter = SimpleDateFormat("d MMMM yyyy", Locale("id", "ID"))
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val convertedDate = sdf.parse(this)
        convertedDate?.let { formatter.format(convertedDate) } ?: this
    } catch (e: Exception) {
        this
    }
}

fun String.asOnlyTime(): String {
    return try {
        val formatter = SimpleDateFormat("HH:mm:ss", Locale("id", "ID"))
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val convertedDate = sdf.parse(this)
        convertedDate?.let { formatter.format(convertedDate) } ?: this
    } catch (e: Exception) {
        this
    }
}

fun String.decodeFromBase64(): String {
    return Base64.decode(this, Base64.NO_WRAP).toString(charset("UTF-8"))
}

fun String.encodeToBase64(): String {
    return Base64.encodeToString(this.toByteArray(charset("UTF-8")), Base64.NO_WRAP)
}

fun String.stringToRupiah(): String {
    val localeID = Locale("in", "ID")
    val doubleValue = this.toDoubleOrNull() ?: return this
    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
    numberFormat.minimumFractionDigits = 0
    return numberFormat.format(doubleValue)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}