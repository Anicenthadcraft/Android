package com.ancient.ancient_handcraft.feature.CategoryWiseProductList

import com.ancient.ancient_handcraft.app.CategoryWiseProduct.CategoryWiseProductPayload
import com.ancient.ancient_handcraft.app.CategoryWiseProduct.InputParams.MainObj
import com.ancient.ancient_handcraft.base.BasePresenter
import com.ancient.ancient_handcraft.base.BaseView

interface CategoryWiseProductContract {
    interface View : BaseView<Presenter> {
        fun sendResponseInfo(payload: CategoryWiseProductPayload?)
    }

    interface Presenter : BasePresenter {
        fun createInputJsonObj(obj: Int?, pageNo: Int?, perPage: Int?, sortType:String?): MainObj
        fun requestProductList(payload: MainObj)
    }
}