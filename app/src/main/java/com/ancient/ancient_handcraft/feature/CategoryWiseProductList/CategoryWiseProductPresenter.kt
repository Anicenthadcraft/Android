package com.ancient.ancient_handcraft.feature.CategoryWiseProductList

import android.util.Log
import com.ancient.ancient_handcraft.app.CategoryWiseProduct.InputParams.MainObj
import com.ancient.ancient_handcraft.app.CategoryWiseProduct.InputParams.PaginationObj
import com.ancient.ancient_handcraft.app.CategoryWiseProduct.InputParams.SearchParams
import com.ancient.ancient_handcraft.app.CategoryWiseProduct.InputParams.SortFieldObj
import com.ancient.ancient_handcraft.webhelper.api.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONException


class CategoryWiseProductPresenter(val view: CategoryWiseProductContract.View) :
    CategoryWiseProductContract.Presenter {

    override fun start() {
    }

    override fun createInputJsonObj(
        id: Int?,
        pageNo: Int?,
        perPage: Int?,
        sortType: String?
    ): MainObj {
        var mainobj = MainObj()
        try {
            val SearchParamsobj = SearchParams()
            SearchParamsobj.categoryId = id

            val paginationObj = PaginationObj()
            paginationObj.page = pageNo.toString()
            paginationObj.perPage = perPage.toString()

            val sortObj = SortFieldObj()
            sortObj.fieldName = "name"
            sortObj.sort = sortType
            mainobj.searchParams = SearchParamsobj
            mainobj.pagination = paginationObj
            mainobj.sortField = sortObj


            return mainobj
        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return mainobj
    }

    override fun requestProductList(payload: MainObj) {
        view?.showLoader()
        ApiClient.apiService.GetProductListByCategory(payload)
            .subscribeOn(Schedulers.single())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                view.hideLoader()
                when (response?.status ?: "") {
                    200 -> {
                        view?.hideLoader()
                        view.sendResponseInfo(response?.payload)
                    }
                    else -> {
                        view?.hideLoader()
                        //view.showMessage(context.resources.getString(R.string.something_went_wrong))
                    }
                }
            },
                {
                    Log.e("error", it.message)
                    view?.showMessage(it.message!!)
                    view?.hideLoader()
                }
            )
    }

}