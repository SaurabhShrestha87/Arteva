package com.arteva.user.manager.net

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import java.io.IOException

interface CustomCallback<T> : Callback<T> {

    override fun onFailure(call: Call<T>, t: Throwable) {
        if (t is IOException && !call.isCanceled) {
            if (showNoNetworkPage()) {

            }
        }

        Log.e(CustomCallback::class.java.name, t.toString())
    }

    fun onStateChanged(): RetryListener?

    fun showNoNetworkPage(): Boolean {
        return true
    }

}