package com.ancient.ancient_handcraft.feature.Dashboard

import com.ancient.ancient_handcraft.webhelper.api.ApiClient
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import okhttp3.Dispatcher
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
            callFetchUser()
        }
    }

    private suspend fun callFetchUser() {
        val response = ApiClient.apiService.GetUserByToken()
        withContext(Dispatchers.Main){

        }
    }
}