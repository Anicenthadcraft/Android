package com.ancient.ancient_handcraft

import android.app.Application
import android.content.Context
import android.os.StrictMode

class AncientHandcraftApplication : Application() {

    internal lateinit var mContext: Context
    private var pckname:String = ""

    init {
        instance = this
    }

    companion object {
        private var instance: AncientHandcraftApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
    override fun onCreate() {
        super.onCreate()

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
        mContext = AncientHandcraftApplication.applicationContext()

        pckname = mContext.packageName

    }

    private fun getpackageName():String{
        return pckname;
    }



}