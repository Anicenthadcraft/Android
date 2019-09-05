package com.ancient.ancient_handcraft.feature.HandcraftItemList

import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.Featured_item_model
import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.Latest_product_model
import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.NavDrawer_item_model
import com.ancient.ancient_handcraft.app.PojoObj.HandcraftItemList.HandcraftItemModel

interface OnProductListItemClickInterface {
    fun onItemClick(position:Int,item : HandcraftItemModel)
}