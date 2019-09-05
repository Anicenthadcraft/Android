package com.ancient.ancient_handcraft.base.Activity.Dashboard

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.Utils.AnimUtils
import com.ancient.ancient_handcraft.Utils.AppUtils
import com.ancient.ancient_handcraft.Utils.AppUtils.Companion.showToastMsg
import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.NavDrawer_item_model
import com.ancient.ancient_handcraft.app.type.FragType
import com.ancient.ancient_handcraft.app.type.TopBarConfig
import com.ancient.ancient_handcraft.feature.Dashboard.DashboardFragment
import com.ancient.ancient_handcraft.feature.HandcraftItemList.HandcraftItemListFragment
import com.ancient.ancient_handcraft.feature.ProductDetailsFragment.ProductDetailsFragment
import com.nostra13.universalimageloader.core.DisplayImageOptions
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.drawer_menu_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import kotlin.collections.ArrayList

class DashboardActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var context: Context
    private lateinit var mDrawerToggle: ActionBarDrawerToggle

    private val imageLoader = ImageLoader.getInstance()
    private var options: DisplayImageOptions? = null

    private val drawerMenuItems: ArrayList<NavDrawer_item_model> = ArrayList()
    private var backpressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        context = this@DashboardActivity

        initializeImageLoader()
        initView()

        initBackStackActionSet()
        loadFragment(FragType.DashboardFragment, true, "")
    }


    private fun initView() {

        toolbar.setPadding(0, toolbar.paddingTop, 0, toolbar.paddingBottom)
        toolbar.setTitle(R.string.blank)
        toolbar.setSubtitle(R.string.blank)

        mDrawerToggle = object : ActionBarDrawerToggle(
            this, drawerlayout, toolbar, R.string.blank, R.string.blank) {

            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)

            }

            /* Called when a drawer has settled in a completely open state. */
            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)

            }


            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                //AppUtils.hideSoftKeyboard(context as Activity)
            }


        }

        drawerlayout.addDrawerListener(mDrawerToggle)
        mDrawerToggle.isDrawerIndicatorEnabled = true
        mDrawerToggle.toolbarNavigationClickListener = View.OnClickListener {
            if (drawerlayout.isDrawerOpen(GravityCompat.START)) {
                drawerlayout.closeDrawer(GravityCompat.START)
            }
        }

        mDrawerToggle.syncState()

        toolbar.setNavigationIcon(R.drawable.ic_header_menu_icon)
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_header_back_arrow)
        setSupportActionBar(toolbar)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        menu_rv.layoutManager = LinearLayoutManager(this)
        menu_rv.adapter = NavDrawerRecyclerView(
            this,
            getMenuItems(),
            object : OnItemClickInterface {
                override fun onItemClick(position: Int, item: NavDrawer_item_model) {
                    Toast.makeText(context, item.itemName, Toast.LENGTH_LONG).show()
                }

            })
        SearchPanelVisibility()
    }

    private fun getMenuItems(): ArrayList<NavDrawer_item_model> {
        drawerMenuItems.add(NavDrawer_item_model("Home"))
        drawerMenuItems.add(NavDrawer_item_model("Craft"))
        drawerMenuItems.add(NavDrawer_item_model("Offer Zones"))
        drawerMenuItems.add(NavDrawer_item_model("Notifications"))
        drawerMenuItems.add(NavDrawer_item_model("My Orders"))
        drawerMenuItems.add(NavDrawer_item_model("My Rewards"))
        drawerMenuItems.add(NavDrawer_item_model("My Cart"))
        drawerMenuItems.add(NavDrawer_item_model("My Wishlist"))
        drawerMenuItems.add(NavDrawer_item_model("My Account"))
        drawerMenuItems.add(NavDrawer_item_model("Gift Card"))
        drawerMenuItems.add(NavDrawer_item_model("Help Centers"))
        drawerMenuItems.add(NavDrawer_item_model("Logout"))

        return drawerMenuItems
    }

    private fun initializeImageLoader() {
        options = DisplayImageOptions.Builder().showImageOnLoading(resources.getDrawable(R.drawable.ic_side_panel_profile_placeholder)
        ).showImageForEmptyUri(resources.getDrawable(R.drawable.ic_side_panel_profile_placeholder)).showImageOnFail(resources.getDrawable(R.drawable.ic_side_panel_profile_placeholder))
            .cacheInMemory(true).cacheOnDisk(true).considerExifParams(true).bitmapConfig(Bitmap.Config.RGB_565).postProcessor { bmp ->
                // return Bitmap.createScaledBitmap(bmp, 480, 370,
                // false);
                bmp
            }.build()
        imageLoader.init(ImageLoaderConfiguration.createDefault(baseContext))
    }
    fun getFragment(): Fragment {
        return supportFragmentManager.findFragmentById(R.id.frame_layout_container)!!
    }

    /**
     * onBackPressed process handle
     */
    override fun onBackPressed() {

        val fm = supportFragmentManager
        fm.executePendingTransactions()
//        AppUtils.hideSoftKeyboard(DashboardActivity())

        if (fm.backStackEntryCount == 0 && getFragment() != null && getFragment() !is DashboardFragment) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            } else {

            }
            loadFragment(FragType.DashboardFragment, false, Any())
        } else if (getFragment() != null && getFragment() is DashboardFragment) {
            if (backpressed + 2000 > System.currentTimeMillis()) {
                finish()
                super.onBackPressed()
            } else {
                showToastMsg(this,getString(R.string.alert_exit))
                //Toast.makeText(this,getString(R.string.alert_exit),Toast.LENGTH_SHORT).show()
            }

            backpressed = System.currentTimeMillis()
        } /*else if (getCurrentFragType() == FragType.FragmentMyWallet)
            loadFragment(FragType.DashboardFragment, false, "")*/
         else {
            super.onBackPressed()
        }
    }

    /**
     * Initiate BackStack Operation
     */
    private fun initBackStackActionSet() {
        //supportFragmentManager.addOnBackStackChangedListener { getFragInstance(getCurrentFragType(), "", false) }
        supportFragmentManager.addOnBackStackChangedListener {
            getFragInstance(getCurrentFragType(), "", false)

            //val f = supportFragmentManager.findFragmentById(R.id.frame_layout_container)

        }
    }

    private fun getFragInstance(mFragType: FragType, initializeObject: Any, enableFragGeneration: Boolean): Fragment? {
        var mFragment: Fragment? = null
        AppUtils.hideSoftKeyboard(context as Activity)
        when (mFragType) {
            FragType.DashboardFragment -> {
                if (enableFragGeneration) {
                    mFragment = DashboardFragment()
                }
                HeaderSubIconVisibility()
                NotificationVisibility()
//                tv_notification_count.visibility = View.VISIBLE   // Added temporarily
                //LocationVisibility()
                ShareInVisibility()
                SearchInvisibility()
                SearchBarInVisibility()
                setTopBarTitle(getString(R.string.dashboard_text))
                setTopBarVisibility(TopBarConfig.HOME)
                setDrawerOpenMode()
            }

            FragType.HandcraftItemListFragment->{
                if (enableFragGeneration) {
                    mFragment = HandcraftItemListFragment()
                }
                HeaderSubIconVisibility()
                NotificationInvisible()
//                tv_notification_count.visibility = View.VISIBLE   // Added temporarily
                //LocationVisibility()
                ShareInVisibility()
                SearchInvisibility()
                SearchBarInVisibility()
                setTopBarTitle(getString(R.string.productlist_text))
                setTopBarVisibility(TopBarConfig.BACK)
                setDrawerOpenMode()
            }

            FragType.ProductDetailsFragment->{
                if (enableFragGeneration) {
                    mFragment = ProductDetailsFragment()
                }
                HeaderSubIconVisibility()
                NotificationInvisible()
//                tv_notification_count.visibility = View.VISIBLE   // Added temporarily
                //LocationVisibility()
                ShareInVisibility()
                SearchInvisibility()
                SearchBarInVisibility()
                setTopBarTitle(getString(R.string.productdetails_text))
                setTopBarVisibility(TopBarConfig.BACK)
                setDrawerLockMode()
            }

            else -> {
                if (enableFragGeneration) {
                    mFragment = DashboardFragment()
                }
                setTopBarTitle(getString(R.string.blank))
                setTopBarVisibility(TopBarConfig.HOME)
                setDrawerLockMode()
            }
        }

        return mFragment
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (supportFragmentManager.backStackEntryCount == 0 || getCurrentFragType() == FragType.DashboardFragment) {
//                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
//                        drawerLayout.closeDrawer(GravityCompat.START)
//                    } else {
//                        drawerLayout.openDrawer(GravityCompat.START)
//                    }
                    drawerlayout.openDrawer(GravityCompat.START)
                } else {
                    onBackPressed()
                }
                return true
            }


        }
        return super.onOptionsItemSelected(item)
    }


    /**
     * LoadFragment
     */
    fun loadFragment(mFragType: FragType, addToStack: Boolean, initializeObject: Any) {

        drawerlayout.closeDrawers()

        if (isFinishing || getCurrentFragType().equals(mFragType)) {
            /*if (mFragType == FragType.NotificationFragment) {
                (getFragment() as NotificationFragment).getNewInstance(initializeObject)
                (getFragment() as NotificationFragment).NotificationDataGeneration()
                return
            } else if (mFragType == FragType.FragmentTransactionDetails && navigateOnNotificationClick) {
                navigateOnNotificationClick = false
                (getFragment() as FragmentTransactionDetails).RefreshOnSamePageLoad(initializeObject as PushNotifObj)
                return
            } else if (mFragType == FragType.FragmentSplitbillTransactionDetails && navigateOnNotificationClick) {
                navigateOnNotificationClick = false
                (getFragment() as FragmentSplitbillTransactionDetails).RefreshOnSamePageLoad(initializeObject as PushNotifObj)
                return
            } else if (mFragType == FragType.PaymentDetailsFragment && (initializeObject is getAccountListObj)) {
                (getFragment() as PaymentDetailsFragment).RefreshOnSamePageLoad(initializeObject as getAccountListObj)
                return
            } else if (mFragType == FragType.PayBillFragment && (initializeObject is SendPaymentObj) && initializeObject != null) {
                (getFragment() as PayBillFragment).RecallPayGoApiOnInputPin(initializeObject)
                return
            } else if (mFragType == FragType.ReedemLoyaltyFragment && (initializeObject is SendPaymentObj) && initializeObject != null && AppConstant.navigateFrom.equals("payGoLoyalty")) {
                (getFragment() as ReedemLoyaltyFragment).RecallPayGoLoyaltyApiOnInputPin(initializeObject)
                return
            } *//*else if (mFragType == FragType.NotificationFragment && (initializeObject is RequestMoneyRequest) && initializeObject != null && AppConstant.navigateFrom.equals("pendingMoneyAccept")) {
                (getFragment() as NotificationFragment).generateRequestMoneyOnApprovedTransactionPin(initializeObject)
                return
            }*//* else
                return*/
        }

        val mTransaction = supportFragmentManager.beginTransaction()
        mTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out)

        /* if (getCurrentFragType() == FragType.FragmentInputPin) {
             mTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_down, R.anim.slide_out_down, R.anim.slide_out_up);
         } else {

         }*/
        if (addToStack) {
            mTransaction.add(R.id.frame_layout_container, getFragInstance(mFragType, initializeObject, true)!!, mFragType.toString())
            mTransaction.addToBackStack(mFragType.toString()).commitAllowingStateLoss()
        } else {
            mTransaction.replace(R.id.frame_layout_container, getFragInstance(mFragType, initializeObject, true)!!, mFragType.toString())
            mTransaction.commitAllowingStateLoss()
        }


    }


    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onClick(v: View?) {
    }

    /**
     * Getting Current Fragment By its FragType
     */
    private fun getCurrentFragType(): FragType {
        val f = supportFragmentManager.findFragmentById(R.id.frame_layout_container)
            ?: return FragType.DEFAULT
        val name = f::class.java.simpleName
        return FragType.valueOf(name)
    }

    /**
     * Drawer Panel UnLocking
     */
    fun setDrawerOpenMode() {
        drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    /**
     * Drawer Panel Locking
     */
    fun setDrawerLockMode() {
        drawerlayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }
    /**
     * Making Toolbar visible/Invisible
     */
    fun setTopBarVisibility(mTopBarConfig: TopBarConfig) {
        if (mTopBarConfig == TopBarConfig.HOME) {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            // Show hamburger
            mDrawerToggle.isDrawerIndicatorEnabled = true
            toolbar.setNavigationIcon(R.drawable.ic_header_menu_icon)
            //iv_tick_icon.visibility = View.GONE
        } else {
            mDrawerToggle.isDrawerIndicatorEnabled = false
            // Show back button
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_header_back_arrow)
            //iv_tick_icon.visibility = View.GONE
        }
    }

    /**
     * Set Header Title
     */
    fun setTopBarTitle(title: String) {
        tv_header.text = title
    }

    fun NotificationVisibility() {
        notification_frame.visibility = View.VISIBLE
        iv_notification.visibility = View.VISIBLE
//        tv_notification_count.visibility = View.VISIBLE
        //refreshNotificationCount()
    }

    fun NotificationInvisible() {
        notification_frame.visibility = View.GONE
        iv_notification.visibility = View.GONE
//        tv_notification_count.visibility = View.GONE
    }

    fun SearchVisibility() {
        iv_search_icon.visibility = View.VISIBLE
    }

    fun SearchInvisibility() {
        iv_search_icon.visibility = View.GONE
    }

    fun SearchBarVisibility() {
        toolbar_search_rl.visibility = View.VISIBLE
        toolbar_search_rl.startAnimation(AnimUtils.inFromRightAnimation())
        toolbar_search.requestFocus()
        AppUtils.visibleSoftKeyboard(context as Activity)
    }

    fun SearchBarInVisibility() {
        if (toolbar_search_rl.visibility == View.VISIBLE) {
            toolbar_search.setText("")
            toolbar_search_rl.visibility = View.GONE
            toolbar_search_rl.startAnimation(AnimUtils.outToRightAnimation())

        }
    }

    fun HeaderTextVisibility() {
        header_text_rl.visibility = View.VISIBLE
    }

    fun HeaderTextInvesibility() {
        header_text_rl.visibility = View.GONE
    }

    fun LocationVisibility() {
        location_iv.visibility = View.VISIBLE
    }

    fun LocationInVisibility() {
        location_iv.visibility = View.GONE
    }

    fun HeaderSubIconVisibility() {
        header_sub_icon_ll.visibility = View.VISIBLE
    }

    fun HeaderSubIconInVisibility() {
        header_sub_icon_ll.visibility = View.GONE
    }

    fun ShareVisibility() {
        share_iv.visibility = View.VISIBLE
    }

    fun ShareInVisibility() {
        share_iv.visibility = View.GONE
    }

    fun SearchPanelVisibility(){
        searchbar_rl.visibility = View.VISIBLE
    }

    fun SearchPanelInVisibility(){
        searchbar_rl.visibility = View.GONE
    }


}