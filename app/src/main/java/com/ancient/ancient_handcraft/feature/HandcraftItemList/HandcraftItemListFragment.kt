package com.ancient.ancient_handcraft.feature.HandcraftItemList

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.Latest_product_model
import com.ancient.ancient_handcraft.app.PojoObj.HandcraftItemList.HandcraftItemModel
import com.ancient.ancient_handcraft.app.type.FragType
import com.ancient.ancient_handcraft.base.Activity.Dashboard.DashboardActivity
import com.ancient.ancient_handcraft.feature.Dashboard.LatestProductRecyclerView
import com.ancient.ancient_handcraft.feature.Dashboard.OnLatestItemClickInterface
import com.fieldtrackingsystem.base.presentation.BaseFragment
import kotlinx.android.synthetic.main.fragment_hadcraft_list_layout.view.*

class HandcraftItemListFragment : BaseFragment(){

    private lateinit var mContext: Context
    private lateinit var latestAdapter : ProductListRecyclerView
    private var itemArray:ArrayList<HandcraftItemModel> = ArrayList<HandcraftItemModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_hadcraft_list_layout, container, false)
        initView(view)
        return view
    }

    private fun initView(view: View?) {
        ProductList()
        setProductItemAdapter(view!!)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
    }

    private fun ProductList() {

        var obj1: HandcraftItemModel =
            HandcraftItemModel("1", "product1","drawable://"+R.drawable.image14,true)
        itemArray.add(obj1)
        var obj2: HandcraftItemModel =
            HandcraftItemModel("2", "product2","drawable://"+R.drawable.image16,true)
        itemArray.add(obj2)
        var obj3: HandcraftItemModel =
            HandcraftItemModel("3", "product3","drawable://"+R.drawable.image24,true)
        itemArray.add(obj3)
        var obj4: HandcraftItemModel =
            HandcraftItemModel("4", "product4","drawable://"+R.drawable.image32,true)
        itemArray.add(obj4)
        var obj5: HandcraftItemModel =
            HandcraftItemModel("5", "product5","drawable://"+R.drawable.image35,true)
        itemArray.add(obj5)
        var obj6: HandcraftItemModel =
            HandcraftItemModel("6", "product6","drawable://"+R.drawable.image39,true)
        itemArray.add(obj6)
        var obj7: HandcraftItemModel =
            HandcraftItemModel("7", "product7","drawable://"+R.drawable.image49,true)
        itemArray.add(obj7)
        var obj8: HandcraftItemModel =
            HandcraftItemModel("8", "product8","drawable://"+R.drawable.image50,true)
        itemArray.add(obj8)
    }

    private fun setProductItemAdapter(rootView: View){
        rootView!!.handcraft_item_rv!!.layoutManager = GridLayoutManager(mContext,2)


            latestAdapter = ProductListRecyclerView(
            mContext,
            itemArray,
            object : OnProductListItemClickInterface {
                override fun onItemClick(position: Int, item: HandcraftItemModel) {
                    (mContext as DashboardActivity).loadFragment(FragType.ProductDetailsFragment, true, "")
                }

            })
        rootView?.handcraft_item_rv!!.adapter =latestAdapter
    }
}