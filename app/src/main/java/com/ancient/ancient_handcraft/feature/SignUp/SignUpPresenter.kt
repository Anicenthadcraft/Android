package com.ancient.ancient_handcraft.feature.SignUp

import android.os.SystemClock
import com.ancient.ancient_handcraft.Utils.AppUtils
import com.ancient.ancient_handcraft.app.PojoObj.SignUp.RegisterPayloadPojo
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SignUpPresenter(val view: SignUpContract.View) : SignUpContract.Presenter {

    override fun start() {
        countdownTimer()
    }

    fun countdownTimer() {
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    moveToLogin()
                }
    }

    private fun moveToLogin() {
        view.openDashboard()
    }

    override fun InitiateSignUpProcess(registerObj: RegisterPayloadPojo?) {
    }

}