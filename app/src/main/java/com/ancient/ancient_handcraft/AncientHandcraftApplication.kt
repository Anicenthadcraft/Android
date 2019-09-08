package com.ancient.ancient_handcraft

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.StrictMode

class AncientHandcraftApplication : Application() {

    internal lateinit var mContext: Context
    private var pckname:String = ""

    companion object {
        private var mCurrentActivity: String? = null
        val getCurrentActivity
            get() = mCurrentActivity

        var setCurrentActivity: String? = null
            set(value) {
                mCurrentActivity = value
            }

    }
    override fun onCreate() {
        super.onCreate()

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        mContext = applicationContext

        pckname = mContext.packageName
    }

    private fun getpackageName():String{
        return pckname;
    }



}