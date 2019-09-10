package com.ancient.ancient_handcraft.feature.Dashboard

import com.ancient.ancient_handcraft.base.BasePresenter
import com.ancient.ancient_handcraft.base.BaseView

interface DashboardContract {
    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter {
        fun initiateUserFetchProcess()
    }
}