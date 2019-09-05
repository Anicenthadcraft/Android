package com.ancient.ancient_handcraft.base.Activity.Dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ancient.ancient_handcraft.R
import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.NavDrawer_item_model
import kotlinx.android.synthetic.main.nav_drawer_menu_item.view.*

class NavDrawerRecyclerView(var mContext: Context, var itemList:ArrayList<NavDrawer_item_model>, var onClickInf: OnItemClickInterface) : RecyclerView.Adapter<NavDrawerRecyclerView.MyViewHolder>() {


    private var context: Context
    private var mItemList:ArrayList<NavDrawer_item_model>
    private var mOnClickInf: OnItemClickInterface
    init {
        context = mContext
        mItemList = itemList
        mOnClickInf = onClickInf
    }
    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.nav_drawer_menu_item, parent, false)
        return MyViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(mItemList[position])

        if(position == mItemList.size-1){
            holder.underline_view.visibility = View.GONE
        }
        holder.main_ll.setOnClickListener{
            mOnClickInf.onItemClick(position,mItemList[position])
        }
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        if(mItemList!=null && mItemList.size>0) return mItemList.size else return 0
    }

    //the class is hodling the list view
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val main_ll = itemView.main_ll
        val nav_drawer_item_iv = itemView.nav_drawer_item_iv
        val nav_drawer_item_name_tv = itemView.nav_drawer_item_name_tv
        val underline_view = itemView.underline_view
        fun bindItems(item: NavDrawer_item_model) {
            nav_drawer_item_name_tv.text = item.itemName
        }
    }
}