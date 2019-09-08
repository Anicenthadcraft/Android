package com.ancient.ancient_handcraft.feature.SignUp

import com.ancient.ancient_handcraft.app.PojoObj.SignUp.UserPayloadPojo
import com.ancient.ancient_handcraft.base.BasePresenter
import com.ancient.ancient_handcraft.base.BaseView


interface SignUpContract {

    interface View : BaseView<Presenter> {
        fun openDashboard()
        fun openOtpVerification(
            otp: UserPayloadPojo?,
            otp1: Int
        )
    }

    interface Presenter : BasePresenter {
        fun InitiateSignUpProcess(userObj: UserPayloadPojo?)
    }
}