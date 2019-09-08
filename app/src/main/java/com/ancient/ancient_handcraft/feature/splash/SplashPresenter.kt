package com.ancient.ancient_handcraft.feature.splash

import android.os.SystemClock
import android.util.Log
import android.widget.Toast
import com.ancient.ancient_handcraft.Utils.Constants
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SplashPresenter(val view: SplashContract.View) : SplashContract.Presenter {

    override fun start() {
        countdownTimer()
    }

    fun countdownTimer() {
        Observable.timer(3, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                moveToNextActivity()
            },
                {
                    Log.e("error", "" + it)
                }, {
                    Log.e("onComplete", "Completed")
                })
    }

    private fun moveToNextActivity() {
        view.moveToNextActivity()
    }
}