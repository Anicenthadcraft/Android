package com.ancient.ancient_handcraft.feature.Dashboard

import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.Featured_item_model
import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.Latest_product_model
import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.NavDrawer_item_model

interface OnLatestItemClickInterface {
    fun onItemClick(position:Int,item : Latest_product_model)
}