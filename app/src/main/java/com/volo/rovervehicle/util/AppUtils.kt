package com.volo.rovervehicle.util

 import android.content.Context
import android.net.ConnectivityManager
 import com.volo.rovervehicle.App


object AppUtils {
    /** Check if Internet is available **/
    fun isNetworkAvailable(): Boolean {
        val context = App.appContext
        if (null != context) {
            val connectivityManager =
                (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
        return false
    }
}