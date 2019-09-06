package com.ancient.ancient_handcraft.feature.Login

import android.content.Context
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.app.PojoObj.Login.LoginPayloadpojo
import com.ancient.ancient_handcraft.feature.SignUp.SignUpContract
import com.ancient.ancient_handcraft.webhelper.api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginPresenter(val view: LoginContract.View, val context: Context) : LoginContract.Presenter {

    override fun start() {

    }

    override fun InitiateLoginProcess(mobileNo: String, password: String) {
        ApiClient.apiService.LoginUser(
            mobileNo = mobileNo,
            password = password
        ).subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    view.hideLoader()
                    when (response?.status ?: "") {
                        200 -> {
                            view.openDashboard()
                        }
                        else -> {
                            view.showErrorMessage(context.resources.getString(R.string.something_went_wrong))
                        }
                    }
                },
                { error ->
                    view.hideLoader()
                    view.showErrorMessage(context.resources.getString(R.string.something_went_wrong))
                }
            )
    }
}