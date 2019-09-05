package com.ancient.ancient_handcraft.base.Activity.Dashboard

import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.NavDrawer_item_model

interface OnItemClickInterface {
    fun onItemClick(position:Int,item : NavDrawer_item_model)
}