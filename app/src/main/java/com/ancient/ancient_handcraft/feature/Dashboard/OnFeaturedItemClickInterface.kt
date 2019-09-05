package com.ancient.ancient_handcraft.feature.Dashboard

import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.Featured_item_model
import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.NavDrawer_item_model

interface OnFeaturedItemClickInterface {
    fun onItemClick(position:Int,item : Featured_item_model)
}