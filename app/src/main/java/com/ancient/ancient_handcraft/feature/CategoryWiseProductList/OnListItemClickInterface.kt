package com.ancient.ancient_handcraft.feature.CategoryWiseProductList

import com.ancient.ancient_handcraft.app.CategoryWiseProduct.CategoryWiseProductObj

interface OnListItemClickInterface {
    fun onItemClick(
        position: Int,
        item: CategoryWiseProductObj?
    )
}