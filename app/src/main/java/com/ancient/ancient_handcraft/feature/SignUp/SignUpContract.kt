package com.ancient.ancient_handcraft.feature.SignUp

import android.location.Location
import com.ancient.ancient_handcraft.app.PojoObj.SignUp.RegisterPayloadPojo
import com.ancient.ancient_handcraft.base.BasePresenter
import com.ancient.ancient_handcraft.base.BaseView


interface SignUpContract {

    interface View: BaseView<Presenter> {
        fun openDashboard()

    }

    interface Presenter: BasePresenter {
        fun InitiateSignUpProcess(registerObj:RegisterPayloadPojo?)
        //fun fetchLocation()
        //fun countdownTimer()

    }
}