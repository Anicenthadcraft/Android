package com.ancient.ancient_handcraft.feature.Category

import com.ancient.ancient_handcraft.app.PojoObj.Category.CategoryListPayload
import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.Featured_item_model
import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.Latest_product_model
import com.ancient.ancient_handcraft.app.PojoObj.DashboardActivity.NavDrawer_item_model

interface OnCategoryListItemClickInterface {
    fun onItemClick(position:Int,item : CategoryListPayload)
}