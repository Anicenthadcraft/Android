package com.ancient.ancient_handcraft.app.CategoryWiseProduct

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class ProductUnderCategoryObj(
    @SerializedName("id") var id: Int? = 0,
    @SerializedName("name") var name: String? = "",
    @SerializedName("slug") var slug: String? = "",
    @SerializedName("parentId") var parentId: Int? = 0,
    @SerializedName("pivot") var pivot: PivotObj? = null
) : Serializable