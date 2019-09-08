package com.ancient.ancient_handcraft.feature.Login

import android.content.Context
import android.util.Log
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.app.AppData
import com.ancient.ancient_handcraft.app.PojoObj.SignUp.UserSession
import com.ancient.ancient_handcraft.webhelper.api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import java.lang.Exception

class LoginPresenter(val view: LoginContract.View, val context: Context) : LoginContract.Presenter {

    private var appData: AppData? = null
    override fun start() {
        appData = AppData(context)
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
                            val session = UserSession(true, response?.payload)
                            appData?.userSession= session
                            view.openDashboard()
                            view.showMessage(context.resources.getString(R.string.login_success_text))
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
                                401 -> {
                                    view.showMessage(jsonObject?.optString("message").toString())
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