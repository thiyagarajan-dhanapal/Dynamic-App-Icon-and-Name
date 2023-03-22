package com.thiyagatrev.dynamic

import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle.Event
import androidx.lifecycle.Lifecycle.Event.ON_DESTROY
import androidx.lifecycle.Lifecycle.Event.ON_RESUME
import androidx.lifecycle.Lifecycle.Event.ON_STOP
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner

class BaseApplication : Application(),LifecycleEventObserver{

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }



    override fun onStateChanged(source: LifecycleOwner, event: Event) {

        if (event==ON_STOP){
            AppIconUtils.changeAppIcon(this.applicationContext)
            ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        }
    }

}