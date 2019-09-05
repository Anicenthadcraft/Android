package com.ancient.ancient_handcraft.feature.splash

import android.location.Location
import com.ancient.ancient_handcraft.base.BasePresenter
import com.ancient.ancient_handcraft.base.BaseView


interface SplashContract {

    interface View: BaseView<Presenter> {
        fun openLoginActivity()
        fun openDashboard()

    }

    interface Presenter: BasePresenter {

        //fun fetchLocation()
        //fun countdownTimer()

    }
}