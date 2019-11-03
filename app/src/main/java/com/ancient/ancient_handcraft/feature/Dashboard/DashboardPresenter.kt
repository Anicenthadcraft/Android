package com.ancient.ancient_handcraft.feature.Dashboard

import android.util.Log
import com.ancient.ancient_handcraft.webhelper.api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.HttpException
import kotlin.coroutines.CoroutineContext

class DashboardPresenter(val view: DashboardContract.View) : DashboardContract.Presenter {

    private val currentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = currentJob + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)

    override fun start() {
    }

    override fun initiateUserFetchProcess() {
        scope.launch {
            val response = ApiClient.apiCustomService.GetUserByToken()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        var email = response.body()?.payload?.email
                        //view.showMessage(email!!)
                    } else {
                        view.showMessage("Error: ${response.code()}")
                    }

                } catch (e: Exception) {
                    view.showMessage(e.message.toString())
                }
            }
        }
    }

    override fun getCategoryList() {
        ApiClient.apiService.GetCategoryList()
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                view.hideLoader()
                when (response?.status ?: "") {
                    200 -> {
                        view?.getCategoryListResponse(response)
                    }
                    else -> {
                        view?.showMessage(response?.message!!)
                    }
                }

            }, { error ->
                try {
                    if (error is HttpException) {
                        val body: ResponseBody =
                            (error as HttpException).response()?.errorBody() as ResponseBody
                        val jsonObject = JSONObject(body.string())
                        val status = jsonObject.getInt("status")
                        when (status ?: "") {
                            400 -> {

                            }
                            else -> {
                                // view.showMessage(context.resources.getString(R.string.something_went_wrong))
                            }
                        }

                    } else {
                        //view.showMessage(context.resources.getString(R.string.something_went_wrong))
                    }
                } catch (e: java.lang.Exception) {
                    Log.e("Exception", "" + e.message)
                    //view.showMessage(context.resources.getString(R.string.something_went_wrong))
                }
                //Observable.fromIterable(error.message)
                view.hideLoader()

            })
    }

}