package com.ancient.ancient_handcraft.feature.Dashboard

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ancient.ancient_handcraft.AncientHandcraftApplication
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.Utils.AnimUtils.bannerArray
import com.ancient.ancient_handcraft.Utils.AppUtils
import com.ancient.ancient_handcraft.Utils.RecyclerViewMargin
import com.ancient.ancient_handcraft.app.PojoObj.Banner.BannerObject
import com.ancient.ancient_handcraft.app.PojoObj.Category.CategoryListResponse
import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.Featured_item_model
import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.Latest_product_model
import com.ancient.ancient_handcraft.app.carauselview.MyPagerAdapter
import com.ancient.ancient_handcraft.app.type.FragType
import com.ancient.ancient_handcraft.autoscrollviewpager.AutoScrollViewPager
import com.ancient.ancient_handcraft.base.Activity.Dashboard.DashboardActivity
import com.synnapps.carouselview.CarouselView
import com.synnapps.carouselview.ImageListener
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import kotlinx.android.synthetic.main.loader_layout.*

class DashboardFragment : Fragment(), View.OnClickListener, DashboardContract.View {


    val FIRST_PAGE = 1
    private lateinit var mContext: Context
    private var mView: View? = null
    private lateinit var adapter: MyPagerAdapter
    private lateinit var featuredAdapter: FraturedRecyclerView
    private lateinit var latestAdapter: LatestProductRecyclerView
    lateinit var appCtx: AncientHandcraftApplication
    private lateinit var pager: AutoScrollViewPager
    private lateinit var carouselView: CarouselView
    private var featuredArray: ArrayList<Featured_item_model> = ArrayList<Featured_item_model>()
    private var latestArray: ArrayList<Latest_product_model> = ArrayList<Latest_product_model>()
    private var sampleImages =
        intArrayOf(R.drawable.banner1, R.drawable.banner2, R.drawable.banner3)
    private var mPresenter: DashboardContract.Presenter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        setPresenter(DashboardPresenter(this))
        mPresenter?.start()
        initView(view)
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
        appCtx = mContext.applicationContext as AncientHandcraftApplication
    }

    private fun initView(rootView: View) {
        mView = rootView
        /*pager = rootView.findViewById<AutoScrollViewPager>(R.id.viewPager)
        mView?.carouselView!!.pageCount = sampleImages.size

        mView?.carouselView!!.setImageListener(imageListener)*/

        bannerImageList()
        featuredImageList()
        latestImageList()
        setImageAdapter(bannerArray)
        setFeaturedAdapter()
        setLatestProductAdapter()


        mPresenter?.initiateUserFetchProcess()

        mView?.category_ll!!.setOnClickListener(this)
    }

    private fun setFeaturedAdapter() {
        mView?.featured_rv!!.layoutManager =
            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        var decoration = RecyclerViewMargin(20, featuredArray.size)

        featuredAdapter = FraturedRecyclerView(
            mContext,
            featuredArray,
            object : OnFeaturedItemClickInterface {
                override fun onItemClick(position: Int, item: Featured_item_model) {

                }

            })
        mView?.featured_rv!!.adapter = featuredAdapter
        mView?.featured_rv!!.addItemDecoration(decoration)
    }

    private fun setLatestProductAdapter() {
        mView?.latest_product_rv!!.layoutManager = GridLayoutManager(mContext, 2)
        latestAdapter = LatestProductRecyclerView(
            mContext,
            latestArray,
            object : OnLatestItemClickInterface {
                override fun onItemClick(position: Int, item: Latest_product_model) {
                    (mContext as DashboardActivity).loadFragment(
                        FragType.HandcraftItemListFragment,
                        true,
                        ""
                    )
                }

            })
        mView?.latest_product_rv!!.adapter = latestAdapter
    }


    internal var imageListener: ImageListener =
        ImageListener { position, imageView -> imageView.setImageResource(sampleImages[position]) }

    private fun bannerImageList() {
        var obj: BannerObject = BannerObject("1", "drawable://" + R.drawable.image5)
        bannerArray.add(obj)
        var obj1: BannerObject = BannerObject("2", "drawable://" + R.drawable.image13)
        bannerArray.add(obj1)
        var obj2: BannerObject = BannerObject("3", "drawable://" + R.drawable.image20)
        bannerArray.add(obj2)
        var obj3: BannerObject = BannerObject("3", "drawable://" + R.drawable.image22)
        bannerArray.add(obj3)
    }

    private fun featuredImageList() {
        var obj: Featured_item_model =
            Featured_item_model("1", "drawable://" + R.drawable.image42)
        featuredArray.add(obj)
        var obj1: Featured_item_model =
            Featured_item_model("2", "drawable://" + R.drawable.image39)
        featuredArray.add(obj1)
        var obj2: Featured_item_model =
            Featured_item_model("3", "drawable://" + R.drawable.image35)
        featuredArray.add(obj2)
    }

    private fun latestImageList() {
        var obj: Latest_product_model =
            Latest_product_model("1", "drawable://" + R.drawable.image9)
        latestArray.add(obj)
        var obj1: Latest_product_model =
            Latest_product_model("2", "drawable://" + R.drawable.image12)
        latestArray.add(obj1)
        var obj2: Latest_product_model =
            Latest_product_model("3", "drawable://" + R.drawable.image23)
        latestArray.add(obj2)
        var obj3: Latest_product_model =
            Latest_product_model("3", "drawable://" + R.drawable.image37)
        latestArray.add(obj3)
    }

    fun setImageAdapter(bannerArray: ArrayList<BannerObject>) {

        val displayMetrics = DisplayMetrics()
        if (activity != null && activity?.getWindowManager() != null)
            activity?.getWindowManager()!!.getDefaultDisplay().getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        val width = displayMetrics.widthPixels

        adapter = MyPagerAdapter(
            activity as DashboardActivity,
            childFragmentManager,
            bannerArray,
            mView?.viewPager,
            mView?.pagesContainer!!,
            R.drawable.indicator_circle
        )

        adapter.setInitialPage(2)
        adapter.show()

        mView?.viewPager!!.adapter = adapter

        mView?.viewPager!!.startAutoScroll()
        mView?.viewPager!!.setInterval(4000)
        mView?.viewPager!!.setCycle(true)
        mView?.viewPager!!.setStopScrollWhenTouch(true)

        mView?.viewPager!!.setOnPageChangeListener(adapter)


        // Set current item to the middle page so we can fling to both
        // directions left and right
        mView?.viewPager!!.currentItem = FIRST_PAGE

        // Necessary or the pager will only have one extra page to show
        // make this at least however many pages you can see
        //pager.offscreenPageLimit = 6

        // UNCOMMENT THIS LINE FOR 3D Carausel effect
        //pager.setPageMargin(-2 * (displayMetrics.widthPixels / 5))//(Integer.parseInt(getString(R.string.pagermargin)))


    }

    override fun onClick(v: View?) {
        when (v?.getId()) {
            R.id.category_ll -> {
                if (AppUtils.isOnline(mContext)) {
                    showLoader()
                    mPresenter?.getCategoryList()
                } else {
                    AppUtils.showToastMsg(
                        mContext,
                        resources.getString(R.string.no_internet_connection_check)
                    )
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bannerArray.clear()
    }

    override fun setPresenter(presenter: DashboardContract.Presenter) {
        mPresenter = presenter
    }

    override fun showLoader() {
        loader_ll!!.visibility = View.VISIBLE
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
    }

    override fun getCategoryListResponse(mCategoryListObj: CategoryListResponse) {
        if (AppUtils.isOnline(mContext)) {
            (mContext as DashboardActivity).loadFragment(
                FragType.CategorySearchFragment,
                true,
                mCategoryListObj
            )
        } else {
            showMessage(resources.getString(R.string.no_internet_connection_check))
        }
    }
}