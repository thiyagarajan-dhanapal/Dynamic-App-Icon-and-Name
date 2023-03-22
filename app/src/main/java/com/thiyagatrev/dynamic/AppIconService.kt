package com.thiyagatrev.dynamic

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

class ChangeAppIconService : Service(), LifecycleEventObserver {
    private val TAG = "AppIconService"

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        Log.e(TAG, "onTaskRemoved: ")

    }

    override fun onStateChanged(source: LifecycleOwner, event: Event) {
        if (event == ON_STOP) {
            Log.e(TAG, "onStateChanged: ON_STOP")
            AppIconUtils.changeAppIcon(applicationContext)
            ProcessLifecycleOwner.get().lifecycle.removeObserver(this)
            stopSelf()
        }
    }

}

object AppIconUtils {
    private const val TAG = "AppIconUtils"

    fun changeAppIcon(context: Context) {
        val sp = context.getSharedPreferences("appSettings", Context.MODE_PRIVATE)

        val aliasNameState = sp.getBoolean("appIcon", false)

        Log.e(TAG, "changeAppIcon: $aliasNameState")
        AppIconUtils.changeAppIconDynamically(context = context, isNewIcon = aliasNameState)
    }

    private fun changeAppIconDynamically(context: Context, isNewIcon: Boolean) {
        val pm = context.applicationContext.packageManager

        if (isNewIcon) {
            pm.setComponentEnabledSetting(
                ComponentName(
                    context,
                    "com.thiyagatrev.dynamic.green"
                ),  //com.example.dummy will be your package
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )

            pm.setComponentEnabledSetting(
                ComponentName(
                    context,
                    "com.thiyagatrev.dynamic.red"
                ),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
        } else {
            pm.setComponentEnabledSetting(
                ComponentName(
                    context,
                    "com.thiyagatrev.dynamic.green"
                ),
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP
            )
            pm.setComponentEnabledSetting(
                ComponentName(
                    context,
                    "com.thiyagatrev.dynamic.red"
                ),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )

        }
    }
}

