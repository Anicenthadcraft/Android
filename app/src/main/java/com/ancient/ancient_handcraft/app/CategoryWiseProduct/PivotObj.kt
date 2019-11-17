package com.ancient.ancient_handcraft.app.CategoryWiseProduct

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PivotObj(
    @SerializedName("categoryId") var categoryId: Int? = 0,
    @SerializedName("productId") var productId: Int? = 0
) :
    Serializable