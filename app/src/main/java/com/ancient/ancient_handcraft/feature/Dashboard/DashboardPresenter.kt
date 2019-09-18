package com.ancient.ancient_handcraft.feature.Dashboard

import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.webhelper.api.ApiClient
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import okhttp3.Dispatcher
import java.lang.Exception
import java.util.*
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
                        view.showMessage(email!!)
                    } else {
                        view.showMessage("Error: ${response.code()}")
                    }

                } catch (e: Exception) {
                    view.showMessage(e.message.toString())
                }
            }
        }
    }
}