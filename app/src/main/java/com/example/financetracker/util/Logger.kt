package com.example.financetracker.util

import android.util.Log

class Logger(c: Class<Any>) {
    val namespace: String = c.toGenericString()

    fun info(m: String) = Log.i(namespace, m)

    fun warn(m: String) = Log.w(namespace, m)

    fun debug(m: String) = Log.d(namespace, m)

    fun error(m: String) = Log.e(namespace, m)
    fun error(m: String, e: Exception) = Log.e(namespace, m, e)

}