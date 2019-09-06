package com.ancient.ancient_handcraft.feature.SignUp

import android.content.Context
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.app.PojoObj.SignUp.RegisterPayloadPojo
import com.ancient.ancient_handcraft.webhelper.api.ApiClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SignUpPresenter(
    val view: SignUpContract.View,
    val context: Context
) : SignUpContract.Presenter {
    override fun start() {

    }

    override fun InitiateSignUpProcess(registerObj: RegisterPayloadPojo?) {
        ApiClient.apiService.registerUser(
            firstName = registerObj!!.firstName,
            lastName = registerObj!!.lastName,
            email = registerObj!!.email,
            isAdmin = registerObj!!.isAdmin,
            mobileNo = registerObj!!.mobileNo,
            password = registerObj!!.password
        ).subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    view.hideLoader()
                    when (response?.status ?: "") {
                        200 -> {
                            view.openOtpVerification()
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