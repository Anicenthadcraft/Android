package com.ancient.ancient_handcraft.feature.Login

import com.ancient.ancient_handcraft.feature.SignUp.SignUpContract

class LoginPresenter(val view: LoginContract.View) : LoginContract.Presenter {

    override fun start() {

    }

    override fun validateLoginInfo(email: String, password: String) {
        view.openDashboard()
    }
}