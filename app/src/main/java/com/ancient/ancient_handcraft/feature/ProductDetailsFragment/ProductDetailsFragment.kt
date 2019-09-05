package com.ancient.ancient_handcraft.feature.ProductDetailsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ancient.ancient_handcraft.R
import com.fieldtrackingsystem.base.presentation.BaseFragment
import android.content.Context
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_product_details.view.*
import androidx.viewpager.widget.ViewPager.OnPageChangeListener

class ProductDetailsFragment : BaseFragment() {

    internal lateinit var pagerAdapter: MyCustomPagerAdapter
    private lateinit var mContext: Context
    private var images: IntArray = intArrayOf(
        R.drawable.image7,
        R.drawable.image27, R.drawable.image42
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_product_details, container, false)
        initView(view)
        return view
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
    }

    private fun initView(view: View?) {
        pagerAdapter = MyCustomPagerAdapter(mContext, images)
        view!!.viewpager.adapter = pagerAdapter

        var isAtZeroIndex: Boolean = false
        var isAtLastIndex: Boolean = false

        val tab = view!!.viewpager.getCurrentItem()
        if (tab == 0) {
            isAtZeroIndex = true
        } else if (tab == images.size - 1) {
            isAtLastIndex = true
        }
        toggleArrowVisibility(view, isAtZeroIndex, isAtLastIndex)

        view!!.left_nav.setOnClickListener() {

            view!!.viewpager.arrowScroll(ViewPager.FOCUS_LEFT)

            val tab = view!!.viewpager.getCurrentItem()
            if (tab == 0) {
                isAtZeroIndex = true
            } else {
                isAtZeroIndex = false
            }
            if (tab == images.size - 1) {
                isAtLastIndex = true
            } else {
                isAtLastIndex = false
            }
            toggleArrowVisibility(view, isAtZeroIndex, isAtLastIndex)
        }

        view!!.right_nav.setOnClickListener() {

            view!!.viewpager.arrowScroll(ViewPager.FOCUS_RIGHT)

            val tab = view!!.viewpager.getCurrentItem()
            if (tab == 0) {
                isAtZeroIndex = true
            } else {
                isAtZeroIndex = false
            }
            if (tab == images.size - 1) {
                isAtLastIndex = true
            } else {
                isAtLastIndex = false
            }
            toggleArrowVisibility(view, isAtZeroIndex, isAtLastIndex)
        }

        view!!.viewpager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                val tab = view!!.viewpager.getCurrentItem()
                if (tab == 0) {
                    isAtZeroIndex = true
                } else {
                    isAtZeroIndex = false
                }
                if (tab == images.size - 1) {
                    isAtLastIndex = true
                } else {
                    isAtLastIndex = false
                }
                toggleArrowVisibility(view, isAtZeroIndex, isAtLastIndex)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                // Check if this is the page you want.
            }
        })

        view!!.rootview_rl.setOnClickListener() {

        }
    }

    fun toggleArrowVisibility(view: View?, isAtZeroIndex: Boolean, isAtLastIndex: Boolean) {
        if (isAtZeroIndex)
            view!!.left_nav.setVisibility(View.INVISIBLE)
        else
            view!!.left_nav.setVisibility(View.VISIBLE)
        if (isAtLastIndex)
            view!!.right_nav.setVisibility(View.INVISIBLE)
        else
            view!!.right_nav.setVisibility(View.VISIBLE)

    }


}