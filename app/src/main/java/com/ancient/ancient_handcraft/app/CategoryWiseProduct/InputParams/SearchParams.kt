package com.ancient.ancient_handcraft.app.CategoryWiseProduct.InputParams

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class SearchParams(
    @SerializedName("categoryId") var categoryId: Int? = 0
) : Serializable