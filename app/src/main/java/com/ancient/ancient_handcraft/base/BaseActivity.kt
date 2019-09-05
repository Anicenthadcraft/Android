package com.ancient.ancient_handcraft.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.Disposable

abstract class BaseActivity : AppCompatActivity() {

    val disposables: MutableList<Disposable> = mutableListOf()

    override final fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(this is BaseView<*>) {
            onCreated(savedInstanceState)
        } else {
            throw MVPNoViewException("Couldn't find the view for this Activity")
        }
    }

    abstract fun onCreated(savedInstanceState: Bundle?)

    class MVPNoViewException(message:String):Exception(message)

    override fun onDestroy() {
        try {
            //Loop through all
            disposables.forEach {
                it.dispose()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        super.onDestroy()
    }
}