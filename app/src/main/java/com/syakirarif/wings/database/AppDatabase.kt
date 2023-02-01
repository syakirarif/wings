package com.syakirarif.wings.database

import com.syakirarif.wings.core.model.TransactionHeader
import com.syakirarif.wings.core.model.User

interface AppDatabase {
    fun clear()

    fun setUserData(value: User)
    fun getUserData(): User
    fun containsUserData(): Boolean
    fun deleteUserData()

    fun containsTransactionHeader(): Boolean
    fun setTransactionHeader(value: List<TransactionHeader>)
    fun getTransactionHeader(): List<TransactionHeader>
    fun deleteTransactionHeader()
}