package com.ancient.ancient_handcraft.feature.Login

import com.ancient.ancient_handcraft.base.BasePresenter
import com.ancient.ancient_handcraft.base.BaseView

interface LoginContract {
    interface View: BaseView<Presenter> {
        fun openDashboard()
        fun openSignUp()
        fun openForgotPassword()
    }

    interface Presenter: BasePresenter {
        fun validateLoginInfo(email:String, password:String)
    }
}