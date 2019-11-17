package com.ancient.ancient_handcraft.feature.CategoryWiseProductList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.Utils.AppUtils
import com.ancient.ancient_handcraft.Utils.PaginationScrollListener
import com.ancient.ancient_handcraft.app.CategoryWiseProduct.CategoryWiseProductObj
import com.ancient.ancient_handcraft.app.CategoryWiseProduct.CategoryWiseProductPayload
import com.ancient.ancient_handcraft.app.PojoObj.Category.CategoryListPayload
import com.ancient.ancient_handcraft.app.type.SortType
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_category_wise_product_list.*
import kotlinx.android.synthetic.main.fragment_category_wise_product_list.view.*


class CategoryWiseProductListFragment() : Fragment(),
    View.OnClickListener,
    CategoryWiseProductContract.View {
    private lateinit var mContext: Context
    private var rootView: View? = null
    private lateinit var mPresenter: CategoryWiseProductContract.Presenter
    private lateinit var productListAdapter: CategoryWiseProductListRecyclerView
    private lateinit var mCategoryListPayload: CategoryListPayload
    private lateinit var layout: GridLayoutManager

    private var isLoading = false // check if is loading
    private var isLastPage = false // check if it is the last page which is displayed

    private var pageNo = 1;
    private var perPage = 10;
    private var totalPage = 0;
    private var currentPage = 0;

    private var mProductList: ArrayList<CategoryWiseProductObj?> = ArrayList()
    private lateinit var loader_ll: LinearLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        rootView = inflater.inflate(R.layout.fragment_category_wise_product_list, container, false)
        setPresenter(CategoryWiseProductPresenter(this))
        mPresenter?.start()
        mCategoryListPayload =
            getArguments()?.getSerializable("CategoryObj") as CategoryListPayload;
        initView(rootView!!)
        return rootView
    }

    private fun initView(view: View?) {
        loader_ll = rootView!!.findViewById(R.id.loader_ll)

        pageNo = 1
        currentPage = pageNo
        if (AppUtils.isOnline(mContext)) {
            mPresenter?.requestProductList(
                mPresenter?.createInputJsonObj(
                    mCategoryListPayload.id,
                    pageNo,
                    perPage,
                    SortType.ASC.name
                )
            )
        } else
            showMessage(resources.getString(R.string.no_internet_connection_check))

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        //appCtx = mContext.applicationContext as AncientHandcraftApplication
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setPresenter(presenter: CategoryWiseProductContract.Presenter) {
        mPresenter = presenter
    }

    override fun showLoader() {
        loader_ll.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        loader_ll.visibility = View.GONE
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

    override fun sendResponseInfo(payload: CategoryWiseProductPayload?) {
        totalPage = payload?.lastPage!!
        isLoading = false
        if (currentPage != totalPage) {
            //mTransactionDetailsAdapter.addLoadingFooter()
            isLastPage = false
        } else
            isLastPage = true

        if (payload?.data != null && payload?.data.size > 0) {
            no_content_tv.visibility = View.GONE
            mProductList.addAll(payload?.data!!)
            initiateAdapter()
        } else
            no_content_tv.visibility = View.VISIBLE

    }

    fun initiateAdapter() {
        if (currentPage == 1) {
            layout = GridLayoutManager(mContext, 2)
            rootView?.category_wise_product_rv!!.layoutManager = layout
            productListAdapter = CategoryWiseProductListRecyclerView(
                mContext,
                mProductList,
                object : OnListItemClickInterface {
                    override fun onItemClick(position: Int, item: CategoryWiseProductObj?) {
                        //TODO navigation to some Page
                        //productListAdapter.addToList(payload?.data!!)
                    }
                })

            rootView?.category_wise_product_rv!!.adapter = productListAdapter
        } else
            productListAdapter.addToList(mProductList)


        rootView?.category_wise_product_rv!!.setOnScrollListener(object :
            PaginationScrollListener(layout) {
            override fun loadMoreItems() {
                this@CategoryWiseProductListFragment.isLoading = true
                currentPage += 1
                if (currentPage <= totalPage)
                    if (AppUtils.isOnline(mContext)) {
                        mPresenter.requestProductList(
                            mPresenter?.createInputJsonObj(
                                mCategoryListPayload.id,
                                currentPage,
                                perPage,
                                SortType.ASC.name
                            )
                        )
                    } else
                        showMessage(resources.getString(R.string.no_internet_connection_check))

            }

            override fun getTotalPageCount(): Int {
                return totalPage
            }

            override fun isLastPage(): Boolean {
                return this@CategoryWiseProductListFragment.isLastPage
            }

            override fun isLoading(): Boolean {
                return this@CategoryWiseProductListFragment.isLoading
            }

        })


    }
}