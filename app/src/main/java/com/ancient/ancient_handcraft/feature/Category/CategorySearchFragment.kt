package com.ancient.ancient_handcraft.feature.Category

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.Utils.AppUtils
import com.ancient.ancient_handcraft.app.PojoObj.Category.CategoryListPayload
import com.ancient.ancient_handcraft.app.PojoObj.Category.CategoryListResponse
import com.ancient.ancient_handcraft.app.type.FragType
import com.ancient.ancient_handcraft.base.Activity.Dashboard.DashboardActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_category_search.view.*

class CategorySearchFragment : Fragment(), View.OnClickListener, CategorySearchContract.View {
    private lateinit var mContext: Context
    private var rootView: View? = null
    private lateinit var mPresenter: CategorySearchContract.Presenter
    private lateinit var categoryListAdapter: CategoryListRecyclerView

    companion object {
        private lateinit var mCategoryListResponse: CategoryListResponse
        fun Instance(mObj: Any?): CategorySearchFragment {
            val mCategorySearchFragment = CategorySearchFragment()
            mCategoryListResponse = mObj as CategoryListResponse
            return mCategorySearchFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_category_search, container, false)
        setPresenter(CategorySearchPresenter(this))
        mPresenter?.start()
        initView(view)
        return view
    }

    private fun initView(view: View) {
        view?.category_rv!!.layoutManager = GridLayoutManager(mContext, 3)
        categoryListAdapter = CategoryListRecyclerView(
            mContext,
            mCategoryListResponse.payload!!,
            object : OnCategoryListItemClickInterface {
                override fun onItemClick(position: Int, item: CategoryListPayload) {
                    if (AppUtils.isOnline(mContext)) {
                        (mContext as DashboardActivity).loadFragment(
                            FragType.CategoryWiseProductListFragment,
                            true,
                            item
                        )
                    } else {
                        showMessage(resources.getString(R.string.no_internet_connection_check))
                    }
                }
            })
        view?.category_rv!!.adapter = categoryListAdapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        //appCtx = mContext.applicationContext as AncientHandcraftApplication
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPresenter(presenter: CategorySearchContract.Presenter) {
        mPresenter = presenter
    }

    override fun showLoader() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoader() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(msg: String) {
        AppUtils.showToastMsg(
            context!!,
            msg
        )
    }

    override fun addDisposable(disposable: Disposable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}