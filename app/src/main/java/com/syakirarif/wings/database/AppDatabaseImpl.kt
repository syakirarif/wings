package com.syakirarif.wings.database

import android.content.Context
import com.orhanobut.hawk.Hawk
import com.syakirarif.wings.core.model.TransactionHeader
import com.syakirarif.wings.core.model.User

class AppDatabaseImpl(context: Context) : AppDatabase {

    companion object {
        private const val PREF_PREFIX = "com.syakirarif"
        private const val PREF_USER_DATA = "$PREF_PREFIX.USER_DATA"
        private const val PREF_TRX_HEADER = "$PREF_PREFIX.TRX_HEADER"
    }

    private val deleteKey = ArrayList<String>()

    init {
        Hawk.init(context).build()
        deleteKey.add(PREF_USER_DATA)
        deleteKey.add(PREF_TRX_HEADER)
    }

    override fun clear() {
        deleteKey.forEach { Hawk.delete(it) }
    }

    override fun setUserData(value: User) {
        Hawk.put(PREF_USER_DATA, value)
    }

    override fun getUserData(): User {
        return Hawk.get(PREF_USER_DATA)
    }

    override fun containsUserData(): Boolean {
        return Hawk.contains(PREF_USER_DATA)
    }

    override fun deleteUserData() {
        Hawk.delete(PREF_USER_DATA)
    }

    override fun setTransactionHeader(value: List<TransactionHeader>) {
        Hawk.put(PREF_TRX_HEADER, value)
    }

    override fun getTransactionHeader(): List<TransactionHeader> {
        return Hawk.get(PREF_TRX_HEADER)
    }

    override fun deleteTransactionHeader() {
        Hawk.delete(PREF_TRX_HEADER)
    }

    override fun containsTransactionHeader(): Boolean {
        return Hawk.contains(PREF_TRX_HEADER)
    }
}