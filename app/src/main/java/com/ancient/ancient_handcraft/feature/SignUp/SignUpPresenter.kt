package com.ancient.ancient_handcraft.feature.SignUp

import android.content.Context
import android.util.Log
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.app.PojoObj.SignUp.UserPayloadPojo
import com.ancient.ancient_handcraft.webhelper.api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import retrofit2.HttpException
import org.json.JSONObject
import java.lang.Exception


class SignUpPresenter(
    val view: SignUpContract.View,
    val context: Context
) : SignUpContract.Presenter {
    override fun start() {

    }

    override fun InitiateSignUpProcess(userObj: UserPayloadPojo?) {
        ApiClient.apiService.registerUser(
            firstName = userObj!!.firstName,
            lastName = userObj!!.lastName,
            email = userObj!!.email,
            isAdmin = userObj!!.isAdmin,
            mobileNo = userObj!!.mobileNo,
            password = userObj!!.password
        ).subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    view.hideLoader()
                    when (response?.status ?: "") {
                        200 -> {
                            view.openOtpVerification(response?.payload,response?.payload!!.otp)
                            //view.showMessage(context.resources.getString(R.string.registration_success_text))
                        }
                        else -> {
                            view.showMessage(context.resources.getString(R.string.something_went_wrong))
                        }
                    }
                },
                { error ->
                    try {
                        if (error is HttpException) {
                            val body: ResponseBody =
                                (error as HttpException).response().errorBody() as ResponseBody
                            val jsonObject = JSONObject(body.string())
                            val status = jsonObject.getInt("status")
                            when (status ?: "") {
                                400 -> {
                                    view.showMessage(context.resources.getString(R.string.invalid_email_mobile_error))
                                }
                                else -> {
                                    view.showMessage(context.resources.getString(R.string.something_went_wrong))
                                }
                            }

                        } else {
                            view.showMessage(context.resources.getString(R.string.something_went_wrong))
                        }
                    } catch (e: Exception) {
                        Log.e("Exception", "" + e.message)
                    }
                    //Observable.fromIterable(error.message)
                    view.hideLoader()
                }
            )
    }

}